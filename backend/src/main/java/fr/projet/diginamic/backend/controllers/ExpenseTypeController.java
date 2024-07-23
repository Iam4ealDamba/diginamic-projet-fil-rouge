package fr.projet.diginamic.backend.controllers;

import fr.projet.diginamic.backend.dtos.ExpenseTypeDto;
import fr.projet.diginamic.backend.entities.ExpenseType;
import fr.projet.diginamic.backend.services.ExpenseTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller for all expense */
@RestController
@RequestMapping("/api/expenseTypes")
public class ExpenseTypeController {
    @Autowired
    ExpenseTypeService expenseTypeService;


    /** Endpoint to obtain a list of all ExpenseTypes
     * @return the expenseTypes found
     */
    @Operation(summary = "Get all ExpenseTypes", description = "Fetches a list of all ExpenseTypes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ExpenseTypes retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseTypeDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))
    })
	 @GetMapping
	    public List<ExpenseTypeDto> getExpenseTypes() {
         List<ExpenseTypeDto> expenseTypeDtos= expenseTypeService.getExpenseTypes();
	    	return expenseTypeDtos;
	    }

    /** Endpoint to save one ExpenseLine
     * @param expenseType, the ExpenseTypeDto to save
     * @return a responseEntity with a succes message
     * @throws Exception if the save doesn't work
     */
    @Operation(summary = "Save a new ExpenseType", description = "Saves a new ExpenseType to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ExpenseType created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<String> saveExpenseLine(@RequestBody ExpenseTypeDto expenseType) throws Exception {
        ExpenseType expenseTypeSave= expenseTypeService.saveExpenseType(expenseType);
        if(expenseTypeSave== null) {
            throw new Exception("The expenseType was not save");
        }
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    /** Endpoint to obtain one ExpenseType by the id
     * @param id, the expenseType id
     * @return the expenseType found
     * @throws Exception if there is no result
     */
    @Operation(summary = "Get an ExpenseType by ID", description = "Fetches a single ExpenseType by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ExpenseType found and returned successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseTypeDto.class))),
            @ApiResponse(responseCode = "404", description = "ExpenseType not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ExpenseTypeDto getExpenseType(@PathVariable Long id) throws Exception {
        ExpenseTypeDto expenseType= expenseTypeService.getExpenseType(id);
        if(expenseType== null) {
            throw new Exception("No expenseLine found");
        }
        return expenseType;
    }

    /** Endpoint to delete one ExpenseType by the id
     * @param id, the expenseType id
     * @return ResponseEntity with a success message
     * @throws Exception if there is nothing to delete
     */
    @Operation(summary = "Delete an ExpenseType by ID", description = "Deletes a single ExpenseType by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ExpenseType deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "ExpenseType not found",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping
    public ResponseEntity<String> DeleteExpenseType(@PathVariable Long id) throws Exception {
        ExpenseType expenseType= expenseTypeService.deleteExpenseType(id);
        if(expenseType== null) {
            throw new Exception("The expenseType was not deleted");
        }
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    /** Endpoint to modify one ExpenseType by the id
     * @param expenseType, the new expenseType
     * @param id, the expenseType id
     * @return ResponseEntity with a success message
     * @throws Exception if there is no result
     */
    @Operation(summary = "Modify an ExpenseType by ID", description = "Modifies a single ExpenseType by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ExpenseType updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "ExpenseType not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyExpenseType(@RequestBody ExpenseTypeDto expenseType, @PathVariable Long id) throws Exception{
        ExpenseType expenseTypeModify = expenseTypeService.modifyExpenseType(expenseType,id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
