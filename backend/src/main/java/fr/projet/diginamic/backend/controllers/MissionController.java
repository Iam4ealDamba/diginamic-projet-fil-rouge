package fr.projet.diginamic.backend.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

import fr.projet.diginamic.backend.dtos.CreateMissionDTO;
import fr.projet.diginamic.backend.dtos.DisplayedMissionDTO;
import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.services.MissionService;
import fr.projet.diginamic.backend.utils.CSVGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


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

	  @Autowired
    private CSVGenerationService csvGenerationService;

	/**
	 * Create a new mission with detailed validation error response.
	 *
	 * @param mission The mission details to create.
	 * @param result  The binding result that holds the validation errors.
	 * @return The created mission or validation errors.
	 */
	@Operation(summary = "Create a new mission")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Mission created successfully",
			content = { @Content(mediaType = "application/json",
			schema = @Schema(implementation = CreateMissionDTO.class)) }),
		@ApiResponse(responseCode = "400", description = "Invalid input",
			content = @Content)
	})
	@PostMapping
	public ResponseEntity<?> createMission(@Valid @RequestBody CreateMissionDTO mission, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			result.getAllErrors().forEach(error -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
			return ResponseEntity.badRequest().body(errors);
		}
		DisplayedMissionDTO savedMission = missionService.createMission(mission);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMission);
	}

	
	/**
	 * Retrieves a paginated list of missions based on various filtering and sorting
	 * criteria.
	 * This endpoint supports pagination, sorting, and dynamic filtering to provide
	 * a flexible retrieval
	 * of mission data. The method allows filtering by mission status, nature, and a
	 * search term that can
	 * match either the username of the assigned user or the mission label.
	 *
	 * @param page            The page number to retrieve, with a default value of 0
	 *                        if not specified.
	 * @param size            The size of the page to retrieve, with a default value
	 *                        of 10 if not specified.
	 * @param order           The direction of sorting (asc or desc), with 'asc' as
	 *                        the default value.
	 * @param sortField       The field on which to sort the results, with
	 *                        'startDate' as the default field.
	 * @param status          Optional filter to limit results to missions with a
	 *                        specific status.
	 * @param natureMission   Optional filter to limit results to missions of a
	 *                        specific nature.
	 * @param userNameOrLabel Optional search term that matches either the username
	 *                        of the mission assignee or the mission label.
	 * @return A {@link ResponseEntity} object containing a {@link Page} of
	 *         {@link Mission} objects
	 *         that match the specified criteria. The response is always OK (200),
	 *         even if no missions match the filters.
	 */

	 @Operation(summary = "Get a paginated list of all missions")
	 @ApiResponses(value = {
		 @ApiResponse(responseCode = "200", description = "List of missions",
			 content = { @Content(mediaType = "application/json",
			 schema = @Schema(implementation = Page.class)) })
	 })
	@GetMapping
	public ResponseEntity<Page<DisplayedMissionDTO>> getAllMissionsWithSpecs(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			@RequestParam(value = "sort", defaultValue = "startDate") String sortField,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "nature", required = false) String natureMission,
			@RequestParam(value = "searchbar", required = false) String userNameOrLabel) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortField));
		Page<DisplayedMissionDTO> missions = missionService.findAllMissionsWithSpecs(status, natureMission,
				userNameOrLabel,
				pageable);

		return ResponseEntity.ok(missions);
	}

	/**
	 * Retrieve a single mission by its ID.
	 * 
	 * @param id The ID of the mission to retrieve.
	 * @return The requested mission.
	 */
	@Operation(summary = "Retrieve a single mission by its ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Mission retrieved successfully",
			content = { @Content(mediaType = "application/json",
			schema = @Schema(implementation = DisplayedMissionDTO.class)) }),
		@ApiResponse(responseCode = "404", description = "Mission not found",
			content = @Content)
	})
	@GetMapping("/{id}")
	public ResponseEntity<DisplayedMissionDTO> getMissionById(@PathVariable Long id) {
		return ResponseEntity.ok(missionService.findOneMissionDto(id));
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
	@Operation(summary = "Update an existing mission")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Mission updated successfully",
			content = { @Content(mediaType = "application/json",
			schema = @Schema(implementation = DisplayedMissionDTO.class)) }),
		@ApiResponse(responseCode = "400", description = "Invalid input",
			content = @Content),
		@ApiResponse(responseCode = "404", description = "Mission not found",
			content = @Content)
	})
	@PutMapping("/{id}")

	public ResponseEntity<?> updateMission(@PathVariable Long id, @Valid @RequestBody DisplayedMissionDTO mission,
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
	@Operation(summary = "Update the status of a mission")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Mission status updated successfully",
			content = { @Content(mediaType = "application/json",
			schema = @Schema(implementation = DisplayedMissionDTO.class)) }),
		@ApiResponse(responseCode = "400", description = "Invalid status value",
			content = @Content),
		@ApiResponse(responseCode = "404", description = "Mission not found",
			content = @Content),
		@ApiResponse(responseCode = "403", description = "Access denied",
			content = @Content)
	})
	@PutMapping("/{id}/status")
	// TODO: double check if can receive enum in body or if need to be converted in
	// backend
	public ResponseEntity<?> updateMissionStatus(@PathVariable Long id, @RequestBody String status) {
		return ResponseEntity.ok(missionService.updateMissionStatus(id, status));
	}

	/**
	 * Delete a mission by its ID.
	 * 
	 * @param id The ID of the mission to delete.
	 * @return A status of 204 No Content on successful deletion.
	 */
	@Operation(summary = "Delete a mission by its ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Mission deleted successfully",
			content = @Content),
		@ApiResponse(responseCode = "404", description = "Mission not found",
			content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
		missionService.deleteMission(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/csv-export-bounties")
    public void exportMissionBountiesToCSV(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"mission_bounties.csv\"");

        List<DisplayedMissionDTO> missions = missionService.findAllMissions();
        String titleCSV = "Récapitulatif des primes de l'année: " + new SimpleDateFormat("YYYY", Locale.FRENCH).format(new Date());
        csvGenerationService.generateBountiesCsvReport(titleCSV, missions, response.getOutputStream());
        response.flushBuffer();
    }
}
