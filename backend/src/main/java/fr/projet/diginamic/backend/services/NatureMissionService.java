package fr.projet.diginamic.backend.services;

import fr.projet.diginamic.backend.dtos.NatureMissionDTO;
import fr.projet.diginamic.backend.entities.NatureMission;
import fr.projet.diginamic.backend.repositories.NatureMissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing NatureMission entities.
 */
@Service
public class NatureMissionService {

    @Autowired
    private NatureMissionRepository natureMissionRepository;

    /**
     * Get a list of all NatureMissions.
     *
     * @return a list of NatureMissionDTO.
     */
    public List<NatureMissionDTO> getAllNatureMissions() {
        List<NatureMission> natureMissions = natureMissionRepository.findAll();
        return natureMissions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a NatureMission by ID.
     *
     * @param id the ID of the NatureMission.
     * @return an Optional of NatureMissionDTO.
     */
    public Optional<NatureMissionDTO> getNatureMissionById(Long id) {
        Optional<NatureMission> natureMission = natureMissionRepository.findById(id);
        return natureMission.map(this::convertToDTO);
    }

    /**
     * Create a new NatureMission.
     *
     * @param natureMissionDTO the NatureMissionDTO to create.
     * @return the created NatureMissionDTO.
     */
    public NatureMissionDTO createNatureMission(NatureMissionDTO natureMissionDTO) {
        NatureMission natureMission = convertToEntity(natureMissionDTO);
        NatureMission savedNatureMission = natureMissionRepository.save(natureMission);
        return convertToDTO(savedNatureMission);
    }

    /**
     * Update an existing NatureMission.
     *
     * @param id the ID of the NatureMission to update.
     * @param natureMissionDTO the updated NatureMissionDTO.
     * @return the updated NatureMissionDTO.
     */
    public NatureMissionDTO updateNatureMission(Long id, NatureMissionDTO natureMissionDTO) {
        NatureMission natureMission = natureMissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NatureMission not found"));

        natureMission.setLabel(natureMissionDTO.getLabel());
        natureMission.setAdr(natureMissionDTO.getAdr());
        natureMission.setIsBilled(natureMissionDTO.getIsBilled());
        natureMission.setStartDate(natureMissionDTO.getStartDate());
        natureMission.setEndDate(natureMissionDTO.getEndDate());
        natureMission.setBonusPercentage(natureMissionDTO.getBonusPercentage());
        natureMission.setIsEligibleToBounty(natureMissionDTO.getIsEligibleToBounty());

        NatureMission updatedNatureMission = natureMissionRepository.save(natureMission);
        return convertToDTO(updatedNatureMission);
    }

    /**
     * Delete a NatureMission by ID.
     *
     * @param id the ID of the NatureMission to delete.
     */
    public void deleteNatureMission(Long id) {
        if (!natureMissionRepository.existsById(id)) {
            throw new RuntimeException("NatureMission not found");
        }
        natureMissionRepository.deleteById(id);
    }

    // Helper methods to convert between entity and DTO
    private NatureMissionDTO convertToDTO(NatureMission entity) {
        return new NatureMissionDTO(
                entity.getId(),
                entity.getLabel(),
                entity.getAdr(),
                entity.getIsBilled(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getBonusPercentage(),
                entity.getIsEligibleToBounty()
        );
    }

    private NatureMission convertToEntity(NatureMissionDTO dto) {
        return new NatureMission(
                dto.getId(),
                dto.getLabel(),
                dto.getAdr(),
                dto.getIsBilled(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getBonusPercentage(),
                dto.getIsEligibleToBounty(),
                new HashSet<>() // Assuming no missions are set in the DTO
        );
    }
}
