package fr.projet.diginamic.backend.repositories.interfaces;

import fr.projet.diginamic.backend.entities.NatureMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NatureMissionRepository  extends JpaRepository <NatureMission, Integer> {

    /**
     * Find NatureMissions by their label
     * @param libelle the label of the NatureMission
     * @return a list of NatureMissions with the given label
     */
    List<NatureMission> findByLibelle(String libelle);


}
