package fr.projet.diginamic.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.projet.diginamic.backend.entities.Mission;

/**
 * Repository interface for {@link Mission} entities, providing methods to perform
 * operations on the database.
 */
@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer>{

}
