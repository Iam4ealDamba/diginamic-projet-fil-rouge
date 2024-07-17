package fr.projet.diginamic.backend.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.diginamic.backend.dtos.ExpenseDto;
import fr.projet.diginamic.backend.dtos.ExpenseWithLinesDto;
import fr.projet.diginamic.backend.entities.Expense;
import fr.projet.diginamic.backend.services.ExpenseService;


/** Controller for all expense*/
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
	@Autowired
	ExpenseService expenseService;
	
	/** Endpoint to obtain the list of all Expenses
     * @return A list of ExpenseDto
     * @throws Exception if there is no result
     */
	 @GetMapping
	    public ArrayList<ExpenseDto> getExpenses() throws Exception {
		 ArrayList<ExpenseDto> expenses= expenseService.getExpenses();
	        if(expenses.isEmpty()) {
	    		throw new Exception("No expense found");
	    	}
	    	return expenses;
	    }
	 
	 /** Endpoint to obtain the list of all Expenses
	     * @param expense, the expense to save
	     * @return a responseEntity with a success message
	     * @throws Exception if there is no result
	     */
	 @PostMapping
	    public  ResponseEntity<String> saveExpense(@RequestBody ExpenseDto expense) throws Exception {
		 Expense expenseSave= expenseService.saveExpense(expense);
		 if(expenseSave== null) {
	    		throw new Exception("The expense was not save");
	    	}
	        return new ResponseEntity<>("Success", HttpStatus.CREATED);
	    }
	 

	 /** Endpoint to obtain one Expense by the id
	     * @param id, the expense id
	     * @return the expense found
	     * @throws Exception if there is no result
	     */
	 @GetMapping("/{id}")
	    public ExpenseWithLinesDto getExpense(@PathVariable Long id) throws Exception {
		 ExpenseWithLinesDto expense= expenseService.getExpense(id);
	        if(expense== null) {
	    		throw new Exception("No expense found");
	    	}
	    	return expense;
	    }

}
