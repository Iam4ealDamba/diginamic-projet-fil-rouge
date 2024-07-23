package fr.projet.diginamic.backend.controllers;

import java.io.IOException;

import fr.projet.diginamic.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itextpdf.text.DocumentException;

// import com.itextpdf.text.DocumentException;

// import fr.diginamic.hello.exception.RestException;
import fr.projet.diginamic.backend.dtos.ExpenseDto;
import fr.projet.diginamic.backend.dtos.ExpenseWithLinesDto;
import fr.projet.diginamic.backend.entities.Expense;
import fr.projet.diginamic.backend.services.ExpenseService;
import jakarta.servlet.http.HttpServletResponse;

/** Controller for all expense */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
	@Autowired
	ExpenseService expenseService;

	
//	/** Endpoint to obtain the list of all Expenses
//     * @return A list of ExpenseDto
//     */
//	 @GetMapping
//	    public Page<ExpenseDto> getExpenses(@RequestParam int page, @RequestParam int size) {
//		 Page<ExpenseDto> expenses= expenseService.getExpenses(page, size);
//	    	return expenses;
//	    }

	/** Endpoint to obtain the list of all my Expenses
	 * @param page            The page number to retrieve, with a default value of 0 if not specified.
	 * @param size            The size of the page to retrieve, with a default value of 10 if not specified.
	 * @param token           The JWTtoken of the connected user
	 * @return A list of ExpenseDto
	 * @throws Exception if there is no result
	 */
	@GetMapping("/user")
	public Page<ExpenseDto> getMyExpenses(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
										  @RequestParam(value = "page", defaultValue = "0") int page,
										  @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
		Page<ExpenseDto> expenses= expenseService.getMyExpenses(page, size, token);
		return expenses;
	}

	/** Endpoint to obtain the list of all Expenses of a manager and they associates
	 * @param page            The page number to retrieve, with a default value of 0 if not specified.
	 * @param size            The size of the page to retrieve, with a default value of 10 if not specified.
	 * @param token           The JWTtoken of the connected user
	 * @return A list of ExpenseDto
	 * @throws Exception if there is no result
	 */
	@GetMapping("/manager")
	public Page<ExpenseDto> getExpensesForManager(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
												  @RequestParam(value = "page", defaultValue = "0") int page,
												  @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
		Page<ExpenseDto> expenses= expenseService.getExpensesForManager(page, size, token);
		return expenses;
	}
	 
	 /** Endpoint to save an Expense
	     * @param expense, the expense to save
	     * @return a responseEntity with a success message
	     * @throws Exception if there is no result
	     */
	 @PostMapping
	    public  ResponseEntity<String> saveExpense(@RequestBody ExpenseDto expense) throws Exception {
		 Expense expenseSave= expenseService.saveExpense(expense);
		 if(expenseSave== null) {
	    		throw new Exception("The expense was not saved");
	    	}
	        return new ResponseEntity<>("Success", HttpStatus.CREATED);
	    }
	 

	 /** Endpoint to obtain one Expense by the id
	     * @param id, the expense id
	     * @return the expense found
	     * @throws Exception if there is no result
	     */
	 @GetMapping("/{id}")
	    public ExpenseWithLinesDto getExpense(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable Long id) throws Exception {
		 ExpenseWithLinesDto expense= expenseService.getExpense(id, token);
	        if(expense== null) {
	    		throw new Exception("No expense found");
	    	}
	    	return expense;
	    }
	 
	 @GetMapping("/pdf/{id}")
	    public void exportExpenseById(@PathVariable Long id, HttpServletResponse response, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws Exception, IOException,
	    DocumentException {
	       expenseService.exportExpense(id, response, token);
	    }

}
