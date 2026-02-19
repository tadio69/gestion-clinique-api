package chijouProjects.gestion_clinique_api.repository;

import chijouProjects.gestion_clinique_api.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
