package chijouProjects.gestion_clinique_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montantTotal;
    private boolean payee;
    private LocalDateTime dateEmission;
    @OneToOne
    private Appointment appointment;
}
