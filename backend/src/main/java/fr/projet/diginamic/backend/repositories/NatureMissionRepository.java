package fr.projet.diginamic.backend.repositories;

import fr.projet.diginamic.backend.entities.NatureMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NatureMissionRepository extends JpaRepository<NatureMission, Integer> {

    /**
     * Find NatureMissions by their label
     * @param label the label of the NatureMission
     * @return a list of NatureMissions with the given label
     */
    List<NatureMission> findByLabel(String label);

    Optional<NatureMission> findById(Long id);
}
