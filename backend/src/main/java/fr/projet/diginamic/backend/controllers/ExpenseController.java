package fr.projet.diginamic.backend.controllers;

import java.io.IOException;
import java.util.Map;

import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

/**
 * Controller for all expense
 */
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


    /**
     * Endpoint to obtain the list of all my Expenses
     *
     * @param page  The page number to retrieve, with a default value of 0 if not specified.
     * @param size  The size of the page to retrieve, with a default value of 10 if not specified.
     * @param token The JWTtoken of the connected user
     * @return A list of ExpenseDto
     * @throws Exception if there is no result
     */
    @Operation(
            summary = "Get a paginated list of all expenses of a user",
            description = "Retrieve a paginated list of expenses from user's mission"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of expenses",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/user")
    public Page<ExpenseDto> getMyExpenses(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        return expenseService.getMyExpenses(page, size, token);
    }


    /**
     * Endpoint to obtain the list of all Expenses of a manager and they associates
     *
     * @param page  The page number to retrieve, with a default value of 0 if not specified.
     * @param size  The size of the page to retrieve, with a default value of 10 if not specified.
     * @param token The JWTtoken of the connected user
     * @return A list of ExpenseDto
     */
    @Operation(
            summary = "Get a paginated list of all expenses for a manager",
            description = "Retrieve a paginated list of expenses with manager's expenses and also associates' expenses"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of expenses",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/manager")
    public Page<ExpenseDto> getExpensesForManager(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return expenseService.getExpensesForManager(page, size, token);
    }


    /**
     * Endpoint to save an Expense
     *
     * @param expense, the expense to save
     * @return a responseEntity with a success message
     */
    @Operation(summary = "Create a new mission", description = "Create a new expense . Returns a success response or errors.")
    @ApiResponse(responseCode = "201", description = "Expense created successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Invalid Json", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    @PostMapping
    public ResponseEntity<String> saveExpense(@RequestBody ExpenseDto expense) {
        Expense expenseSave = expenseService.saveExpense(expense);
        if (expenseSave == null) {
            new ResponseEntity<>("fail", HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    /**
     * Endpoint to obtain one Expense by the id
     *
     * @param id, the expense id
     * @return the expense found
     * @throws Exception if there is no result
     */
    @Operation(summary = "Retrieve an expense by ID", description = "Fetches a detailed view of a single expense by its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Expense found and returned successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseWithLinesDto.class)))
    @ApiResponse(responseCode = "404", description = "Expense not found", content = @Content(mediaType = "application/json"))
    @GetMapping("/{id}")
    public ExpenseWithLinesDto getExpense(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        ExpenseWithLinesDto expense = expenseService.getExpense(id, token);
        if (expense == null) {
            new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
        return expense;
    }

    @Operation(summary = "Retrieve an expense by ID and download a pdf with all the information", description = "Fetches a detailed view of a single expense by its unique identifier and transforms it into a PDF.")
    @ApiResponse(responseCode = "200", description = "PDF generated successfully", content = @Content(mediaType = "application/pdf"))
    @ApiResponse(responseCode = "404", description = "Expense not found", content = @Content(mediaType = "application/json"))
    @GetMapping("/pdf/{id}")
    public void exportExpenseById(@PathVariable Long id, HttpServletResponse response, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws Exception, IOException,
            DocumentException {
        expenseService.exportExpense(id, response, token);
    }

}