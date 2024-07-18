package fr.projet.diginamic.backend.controllers;

import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.services.MissionService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	 * Create a new mission with detailed validation error response.
	 *
	 * @param mission The mission details to create.
	 * @param result  The binding result that holds the validation errors.
	 * @return The created mission or validation errors.
	 */
	@PostMapping
	public ResponseEntity<?> createMission(@Valid @RequestBody Mission mission, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			result.getAllErrors().forEach(error -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
			return ResponseEntity.badRequest().body(errors);
		}
		Mission savedMission = missionService.createMission(mission);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMission);
	}

	/**
	 * Retrieve all missions.
	 * 
	 * @return A list of missions.
	 */
	@GetMapping
	public ResponseEntity<Page<Mission>> getAllMissions(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Mission> pageResult = missionService.findAllMissions(pageable);
		return ResponseEntity.ok(pageResult);
	}

	@GetMapping
	public ResponseEntity<Page<Mission>> getAllMissionsWithSpecs(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			@RequestParam(value = "sort", defaultValue = "startDate") String sortField,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "nature", required = false) String natureMission,
			@RequestParam(value = "searchbar", required = false) String userNameOrLabel) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortField));
		Page<Mission> missions = missionService.findAllMissionsWithSpecs(status, natureMission, userNameOrLabel,
				pageable);

		return ResponseEntity.ok(missions);
	}

	/**
	 * Retrieve a single mission by its ID.
	 * 
	 * @param id The ID of the mission to retrieve.
	 * @return The requested mission.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
		return ResponseEntity.ok(missionService.findOneMission(id));
	}

	/**
	 * Update an existing mission.
	 * 
	 * @param id      The ID of the mission to update.
	 * @param mission Updated details for the mission.
	 *                * @param result The binding result that holds the validation
	 *                errors.
	 * @return The updated mission or errors.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMission(@PathVariable Long id, @Valid @RequestBody Mission mission,
			BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			result.getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
			return ResponseEntity.badRequest().body(errors);
		}
		return ResponseEntity.ok(missionService.updateMission(id, mission));
	}

	/**
	 * Updates the status of a mission. This endpoint is restricted to managers and
	 * administrators.
	 * Allows setting the mission status to either "Validé" or "Rejeté".
	 *
	 * @param missionId the ID of the mission to update
	 * @param status    the new status to be set for the mission
	 *                  * @param result The binding result that holds the validation
	 *                  errors.
	 * @return a ResponseEntity with status OK if the update is successful
	 * @throws AccessDeniedException if the user is not authorized to update the
	 *                               mission status
	 */
	@PutMapping("/{id}/status")
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public ResponseEntity<?> updateMissionStatus(@PathVariable Long id, @Valid @RequestParam String status,
			BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			result.getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
			return ResponseEntity.badRequest().body(errors);
		}
		return ResponseEntity.ok(missionService.updateMissionStatus(id, status));
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
