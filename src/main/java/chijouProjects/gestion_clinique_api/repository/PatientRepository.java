package chijouProjects.gestion_clinique_api.repository;

import chijouProjects.gestion_clinique_api.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
