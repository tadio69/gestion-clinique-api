package chijouProjects.gestion_clinique_api.service;

import chijouProjects.gestion_clinique_api.dto.AppointmentDTO;
import chijouProjects.gestion_clinique_api.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment createRdv(AppointmentDTO dto);
    void cancelRdv(Long id);
    List<Appointment> getAllRdv();
}
