package chijouProjects.gestion_clinique_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content; // Liste des médicaments et posologie
    private LocalDateTime dateCreation;
    @ManyToOne private User doctor;
    @ManyToOne private Patient patient;
}
