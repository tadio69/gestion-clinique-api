package chijouProjects.gestion_clinique_api.service.impl;

import chijouProjects.gestion_clinique_api.dto.AppointmentDTO;
import chijouProjects.gestion_clinique_api.entity.Appointment;
import chijouProjects.gestion_clinique_api.entity.Patient;
import chijouProjects.gestion_clinique_api.entity.User;
import chijouProjects.gestion_clinique_api.exception.BusinessException;
import chijouProjects.gestion_clinique_api.repository.AppointmentRepository;
import chijouProjects.gestion_clinique_api.repository.PatientRepository;
import chijouProjects.gestion_clinique_api.repository.UserRepository;
import chijouProjects.gestion_clinique_api.service.AppointmentService;
import chijouProjects.gestion_clinique_api.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private NotificationService notificationService;

    @Override
    @Transactional
    public Appointment createRdv(AppointmentDTO dto) {
        // 1. Récupération du médecin et du patient
        User doctor = userRepository.findById(dto.doctorId())
                .orElseThrow(() -> new BusinessException("Médecin introuvable."));

        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new BusinessException("Patient introuvable."));

        // 2. Logique de vérification de collision (Médecin)
        // On vérifie si le médecin a déjà un RDV à cette heure précise
        if (appointmentRepository.existsByDoctorIdAndDateTime(doctor.getId(), dto.dateTime())) {
            throw new BusinessException("Le médecin est déjà occupé pour ce créneau.");
        }

        // 3. Logique de vérification (Patient)
        // Un patient ne peut pas réserver deux RDV le même jour
        LocalDateTime startOfDay = dto.dateTime().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = dto.dateTime().toLocalDate().atTime(23, 59, 59);
        if (appointmentRepository.hasPatientRdvOnDay(patient.getId(), startOfDay, endOfDay)) {
            throw new BusinessException("Ce patient possède déjà un rendez-vous pour cette journée.");
        }

        // 4. Création et sauvegarde de l'objet Appointment
        Appointment newRdv = new Appointment();
        newRdv.setDateTime(dto.dateTime());
        newRdv.setDoctor(doctor);
        newRdv.setPatient(patient);
        newRdv.setStatus("SCHEDULED");

        Appointment saved = appointmentRepository.save(newRdv);

        // 5. Notification en temps réel au médecin via WebSocket
        String message = String.format("Nouveau RDV avec le patient %s %s prévu le %s",
                patient.getNom(), patient.getPrenom(), saved.getDateTime());

        notificationService.sendNotification(doctor.getUsername(), message);

        // Optionnel : Notifier aussi la secrétaire pour confirmer la création dans son UI
        // notificationService.sendNotification("secretaire_admin", "RDV créé avec succès");

        return saved;
    }

    @Override
    public List<Appointment> getAllRdv() {
        return appointmentRepository.findAll();
    }

    @Override
    @Transactional
    public void cancelRdv(Long id) {
        Appointment rdv = appointmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Rendez-vous introuvable."));

        // Vérification du délai de 24h avant l'annulation
        if (LocalDateTime.now().isAfter(rdv.getDateTime().minusHours(24))) {
            throw new BusinessException("Annulation impossible : le délai de prévenance de 24h est dépassé.");
        }

        rdv.setStatus("CANCELLED");
        appointmentRepository.save(rdv);

        // Notifier le médecin de l'annulation
        notificationService.sendNotification(rdv.getDoctor().getUsername(),
                "Le rendez-vous du " + rdv.getDateTime() + " a été annulé.");
    }
}
