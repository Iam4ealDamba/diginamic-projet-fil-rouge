package fr.projet.diginamic.backend.controllers;

import fr.projet.diginamic.backend.dtos.ExpenseTypeDto;
import fr.projet.diginamic.backend.entities.ExpenseType;
import fr.projet.diginamic.backend.services.ExpenseTypeService;
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
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyExpenseType(@RequestBody ExpenseTypeDto expenseType, @PathVariable Long id) throws Exception{
        ExpenseType expenseTypeModify = expenseTypeService.modifyExpenseType(expenseType,id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
