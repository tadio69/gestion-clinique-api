package chijouProjects.gestion_clinique_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private String status; // SCHEDULED, CANCELLED, COMPLETED
    @ManyToOne
    private User doctor;
    @ManyToOne private Patient patient;
}
