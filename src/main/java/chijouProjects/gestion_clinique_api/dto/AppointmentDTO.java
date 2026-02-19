package chijouProjects.gestion_clinique_api.dto;

import java.time.LocalDateTime;

public record AppointmentDTO(
        LocalDateTime dateTime,
        Long patientId,
        Long doctorId
) {}
