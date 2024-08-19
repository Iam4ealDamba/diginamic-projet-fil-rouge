package fr.projet.diginamic.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import fr.projet.diginamic.backend.dtos.ExpenseLineDto;
import fr.projet.diginamic.backend.entities.ExpenseLine;
import fr.projet.diginamic.backend.services.ExpenseLineService;

/** Controller for all ExpenseLine*/
@RestController
@RequestMapping("/api/expenseLines")
public class ExpenseLineController {
	
	@Autowired
	ExpenseLineService expenseLineService;

//	/** Endpoint to obtain a list of all ExpenseLines
//     * @return the expense found
//     * @throws Exception if there is no result
//     */
//	 @GetMapping
//	    public Page<ExpenseLineDto> getExpenseLines(@RequestParam int page, @RequestParam int size) {
//		 Page<ExpenseLineDto> expenseLines= expenseLineService.getExpenseLines(page, size);
//	    	return expenseLines;
//	    }
	 
	 /** Endpoint to save one ExpenseLine 
	     * @param expenseLine, the ExpenseLine to save
	     * @return a responseEntity with a succes message
	     */
	 @Operation(summary = "Save a new ExpenseLine", description = "Saves a new ExpenseLine to the database.")
	 @ApiResponses(value = {
			 @ApiResponse(responseCode = "201", description = "ExpenseLine created successfully",
					 content = @Content(mediaType = "application/json")),
			 @ApiResponse(responseCode = "500", description = "Internal Server Error",
					 content = @Content(mediaType = "application/json"))
	 })
	 @PostMapping
	    public  ResponseEntity<String> saveExpenseLine(@RequestBody ExpenseLineDto expenseLine)  {
		 ExpenseLine expenseLineSave= expenseLineService.saveExpenseLine(expenseLine);
		 if(expenseLineSave== null) {
			 new ResponseEntity<>("fail", HttpStatus.NOT_IMPLEMENTED);
	    	}
	        return new ResponseEntity<>("Success", HttpStatus.CREATED);
	    }
	 /** Endpoint to obtain one ExpenseLine by the id
	     * @param id, the expenseLine id
	     * @return the expenseLine found

	     */
	 @Operation(summary = "Get an ExpenseLine by ID", description = "Fetches a single ExpenseLine by its unique identifier.")
	 @ApiResponses(value = {
			 @ApiResponse(responseCode = "200", description = "ExpenseLine found and returned successfully",
					 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseLineDto.class))),
			 @ApiResponse(responseCode = "404", description = "ExpenseLine not found",
					 content = @Content(mediaType = "application/json"))
	 })
	 @GetMapping("/{id}")
	    public ExpenseLineDto getExpenseLine(@PathVariable Long id) {
		 ExpenseLineDto expenseLine= expenseLineService.getExpenseLine(id);
	        if(expenseLine== null) {
				new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
	    	}
	    	return expenseLine;
	    }
	 
	 /** Endpoint to delete one Expense by the id
	     * @param id, the expense id
	     * @return ResponseEntity with a success message

	     */
	 @Operation(summary = "Delete an ExpenseLine by ID", description = "Deletes a single ExpenseLine by its unique identifier.")
	 @ApiResponses(value = {
			 @ApiResponse(responseCode = "200", description = "ExpenseLine deleted successfully",
					 content = @Content(mediaType = "application/json")),
			 @ApiResponse(responseCode = "404", description = "ExpenseLine not found",
					 content = @Content(mediaType = "application/json"))
	 })
	 @DeleteMapping
	    public ResponseEntity<String> DeleteExpenseLine(@PathVariable Long id)  {
		 ExpenseLine expenseLine= expenseLineService.deleteExpenseLine(id);
	        if(expenseLine== null) {
				new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
	    	}
	        return new ResponseEntity<>("Success", HttpStatus.CREATED);
	    }
	 
	 /** Endpoint to modify one Expense by the id
	     * @param expenseLine, the new expenseLine
	     * @param id, the expense id
	     * @return ResponseEntity with a success message
	     * @throws Exception if there is no result
	     */
	 @Operation(summary = "Modify an ExpenseLine by ID", description = "Modifies a single ExpenseLine by its unique identifier.")
	 @ApiResponses(value = {
			 @ApiResponse(responseCode = "200", description = "ExpenseLine updated successfully",
					 content = @Content(mediaType = "application/json")),
			 @ApiResponse(responseCode = "404", description = "ExpenseLine not found",
					 content = @Content(mediaType = "application/json"))
	 })
	 @PutMapping("/{id}")
	    public ResponseEntity<String> modifyExpenseLine(@RequestBody ExpenseLineDto expenseLine, @PathVariable Long id) {
	        ExpenseLine expenseLineModify = expenseLineService.modifyExpenseLine(expenseLine,id);
		 if(expenseLineModify== null) {
			 new ResponseEntity<>("fail", HttpStatus.NOT_MODIFIED);
		 }
	        return new ResponseEntity<>("Success", HttpStatus.OK);
	    }

}
