package chijouProjects.gestion_clinique_api.repository;

import chijouProjects.gestion_clinique_api.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}
