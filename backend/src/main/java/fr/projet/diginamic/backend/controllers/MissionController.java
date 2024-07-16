package fr.projet.diginamic.backend.controllers;

import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.services.MissionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Controller class for handling requests for the /missions endpoint. This class
 * handles CRUD operations for missions within the system, permitting the
 * creation, retrieval, update, and deletion of mission entries.
 */
@RestController
@RequestMapping("/api/missions")
public class MissionController {

	@Autowired
	private MissionService missionService;

	/**
	 * Create a new mission.
	 * 
	 * @param mission The mission details to create.
	 * @return The created mission.
	 */
	@PostMapping
	public ResponseEntity<Mission> createMission(@RequestBody Mission mission) {
		return ResponseEntity.ok(missionService.createMission(mission));
	}

	/**
	 * Retrieve all missions.
	 * 
	 * @return A list of missions.
	 */
	@GetMapping
	public ResponseEntity<Page<Mission>> getAllMissions(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "100") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Mission> pageResult = missionService.findAllMissions(pageable);
		return ResponseEntity.ok(pageResult);
	}

	/**
	 * Retrieve a single mission by its ID.
	 * 
	 * @param id The ID of the mission to retrieve.
	 * @return The requested mission.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Mission> getMissionById(@PathVariable Long id){
		return ResponseEntity.ok(missionService.findOneMission(id));
	}

	/**
	 * Update an existing mission.
	 * 
	 * @param id      The ID of the mission to update.
	 * @param mission Updated details for the mission.
	 * @return The updated mission.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Mission> updateMission(@PathVariable Long id, @RequestBody Mission mission) {
		return ResponseEntity.ok(missionService.updateMission(id, mission));
	}

	/**
	 * Delete a mission by its ID.
	 * 
	 * @param id The ID of the mission to delete.
	 * @return A status of 204 No Content on successful deletion.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
		missionService.deleteMission(id);
		return ResponseEntity.noContent().build();
	}
}
