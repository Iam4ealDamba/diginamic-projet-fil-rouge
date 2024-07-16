package fr.projet.diginamic.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.repositories.MissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;

/**
 * Service class for managing mission entities.
 * Provides methods to perform CRUD operations on mission entities.
 */
@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    /**
     * Save a mission entity.
     * @param mission the mission to save.
     * @return the saved mission entity.
     */
    public Mission createMission(Mission mission) {
        return missionRepository.save(mission);
    }
    
    /**
     * Retrieve a single mission by its ID.
     * @param id the ID of the mission to retrieve.
     * @return the found mission entity.
     * @throws EntityNotFoundException if the mission is not found.
     */
    @Transactional(readOnly = true)
    public Mission findOneMission(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with ID: " + id));
    }

    /**
     * Retrieve all missions.
     * @return a list of missions.
     */
    @Transactional(readOnly = true)
    public Page<Mission> findAllMissions(Pageable pageable) {
        return missionRepository.findAll(pageable);
    }
    
    /**
     * Retrieve a single mission by its ID and update it.
     * @param id the ID of the mission to retrieve and update.
     * @param updatedMission the updated mission data.
     * @return the updated mission entity.
     * @throws EntityNotFoundException if the mission is not found.
     */
    @Transactional
    public Mission updateMission(Long id, Mission updatedMission) {
    	
        return missionRepository.findById(id).map(mission -> {
            mission.setLabel(updatedMission.getLabel());
            mission.setDailyRate(updatedMission.getDailyRate());
            mission.setStatus(updatedMission.getStatus());
            mission.setStartDate(updatedMission.getStartDate());
            mission.setEndDate(updatedMission.getEndDate());
            mission.setTransport(updatedMission.getTransport());
            mission.setDepartureCity(updatedMission.getDepartureCity());
            mission.setArrivalCity(updatedMission.getArrivalCity());
            mission.setBonusDate(updatedMission.getBonusDate());
            mission.setBonusAmount(updatedMission.getBonusAmount());
            mission.setUser(updatedMission.getUser());
            mission.setMissionNature(updatedMission.getMissionNature());
            mission.setExpense(updatedMission.getExpense());
            return missionRepository.save(mission);
        }).orElseThrow(() -> new EntityNotFoundException("Mission not found with ID: " + id));
    }

    /**
     * Delete a mission by its ID.
     * @param id the ID of the mission to delete.
     * @throws EntityNotFoundException if the mission is not found.
     */
    @Transactional
    public void deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new EntityNotFoundException("Mission not found with ID: " + id);
        }
        missionRepository.deleteById(id);
    }
}