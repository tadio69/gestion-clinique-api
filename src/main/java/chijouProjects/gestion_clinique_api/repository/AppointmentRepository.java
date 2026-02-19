package chijouProjects.gestion_clinique_api.repository;

import chijouProjects.gestion_clinique_api.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Vérifier collision Docteur
    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);

    // Vérifier si le patient a déjà un RDV ce jour
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.patient.id = :pId " +
            "AND a.dateTime BETWEEN :start AND :end")
    boolean hasPatientRdvOnDay(@Param("pId") Long patientId,
                               @Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end);
}
