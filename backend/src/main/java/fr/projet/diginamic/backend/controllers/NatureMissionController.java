package fr.projet.diginamic.backend.controllers;

import fr.projet.diginamic.backend.dtos.NatureMissionDTO;
import fr.projet.diginamic.backend.services.NatureMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing NatureMission entities.
 */
@RestController
@RequestMapping("/api/missions/natures")
public class NatureMissionController {

    @Autowired
    private NatureMissionService natureMissionService;

    /**
     * Get a list of all NatureMissions.
     *
     * @return a list of NatureMissionDTO.
     */
    @GetMapping
    public ResponseEntity<List<NatureMissionDTO>> getNaturesMissions() {
        List<NatureMissionDTO> naturesMissions = natureMissionService.getAllNatureMissions();
        return ResponseEntity.ok(naturesMissions);
    }

    /**
     * Get a NatureMission by ID.
     *
     * @param natureId the ID of the NatureMission.
     * @return a NatureMissionDTO.
     */
    @GetMapping("/{natureId}")
    public ResponseEntity<NatureMissionDTO> getNatureMission(@PathVariable Long natureId) {
        return natureMissionService.getNatureMissionById(natureId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new NatureMission.
     *
     * @param natureMissionDTO the NatureMissionDTO to create.
     * @return the created NatureMissionDTO.
     */
    @PostMapping
    public ResponseEntity<NatureMissionDTO> postNatureMission(@RequestBody NatureMissionDTO natureMissionDTO) {
        NatureMissionDTO createdNatureMission = natureMissionService.createNatureMission(natureMissionDTO);
        return ResponseEntity.ok(createdNatureMission);
    }

    /**
     * Update an existing NatureMission.
     *
     * @param natureId         the ID of the NatureMission to update.
     * @param natureMissionDTO the updated NatureMissionDTO.
     * @return the updated NatureMissionDTO.
     */
    @PutMapping("/{natureId}")
    public ResponseEntity<NatureMissionDTO> putNatureMission(@PathVariable Long natureId, @RequestBody NatureMissionDTO natureMissionDTO) {
        NatureMissionDTO updatedNatureMission = natureMissionService.updateNatureMission(natureId, natureMissionDTO);
        return ResponseEntity.ok(updatedNatureMission);
    }

    /**
     * Delete a NatureMission by ID.
     *
     * @param id the ID of the NatureMission to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNatureMission(@PathVariable Long id) {
        boolean isRemoved = natureMissionService.deleteNatureMission(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

