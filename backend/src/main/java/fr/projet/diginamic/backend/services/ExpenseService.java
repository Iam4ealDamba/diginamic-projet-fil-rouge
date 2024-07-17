package fr.projet.diginamic.backend.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.ExpenseDto;
import fr.projet.diginamic.backend.dtos.ExpenseWithLinesDto;
import fr.projet.diginamic.backend.entities.Expense;
import fr.projet.diginamic.backend.repositories.interfaces.ExpenseRepository;
import fr.projet.diginamic.backend.utils.ExpenseMapper;

/**Service for all expense's related method*/
@Service
public class ExpenseService {
	@Autowired
	private ExpenseRepository expenseRepo;
	
	@Autowired
	private ExpenseMapper expenseMapper;
	
	/**Method get all expenses and transform them into ExpenseDto
     * @return the List of all expenses
     */
	public ArrayList<ExpenseDto> getExpenses(){
		ArrayList<Expense> expenses= expenseRepo.findAll();
		ArrayList<ExpenseDto> expensesDto= new ArrayList();
		for(Expense expense: expenses) {
			expensesDto.add(expenseMapper.BeanToDto(expense));
		}
		return expensesDto;
	}
	
	/**Method to get an expense by its id and transform it into an ExpenseDto
	 * @param id, the id of the expense to get
     * @return the expenseLine found
     */
	public ExpenseWithLinesDto getExpense(Long id){
		Expense expense= expenseRepo.findById(id).orElse(null);
		ExpenseWithLinesDto expenseDto= expenseMapper.BeanToDtoWithLines(expense);
		return expenseDto;
	}
	
	/**Method to save an expense
	 * @param expense, the id of the expense to save
     * @return the expense saved
     */
	public Expense saveExpense(ExpenseDto expense){
		Expense expenseSave= expenseMapper.dtoToBean(expense);
		expenseSave= expenseRepo.save(expenseSave);
		return expenseSave;
	}
}
