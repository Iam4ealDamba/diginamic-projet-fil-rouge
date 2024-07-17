package fr.projet.diginamic.backend.utils;

import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.ExpenseDto;
import fr.projet.diginamic.backend.dtos.ExpenseWithLinesDto;
import fr.projet.diginamic.backend.entities.Expense;

/**Method to transform a ExpenseLine to an ExpenseLineDto
 * @param ExpenseLine, the bean to transform
 * @return the expenseLineDto object after the mapping
 */
@Service
public class ExpenseMapper {

	/**Method to transform a Expense to an ExpenseDto
	 * @param exp, the dto to transform
     * @return the expense object after the mapping
     */
	public Expense dtoToBean(ExpenseDto exp){
		Expense newExp= new Expense();
		newExp.setDate(exp.getDate());
		newExp.setStatus(exp.getStatus());
		return newExp;
		
	}
	/**Method to transform a Expense to an ExpenseDto
	 * @param exp, the bean to transform
     * @return the expenseDto object after the mapping
     */
	public ExpenseDto BeanToDto(Expense exp){
		ExpenseDto newExp= new ExpenseDto();
		newExp.setDate(exp.getDate());
		newExp.setStatus(exp.getStatus());
		return newExp;
		
	}
	/**Method to transform a ExpenseWithLinesDto to an Expense
	 * @param exp, the dto to transform
     * @return the expense object after the mapping
     */
	public Expense dtoWithLinesToBean(ExpenseWithLinesDto exp){
		Expense newExp= new Expense();
		newExp.setDate(exp.getDate());
		newExp.setStatus(exp.getStatus());
		newExp.setExpenseLines(exp.getExpenseLines());
		return newExp;
		
	}
	
	/**Method to transform a Expense to an ExpenseWithLinesDto
	 * @param exp, the bean to transform
     * @return the ExpenseWithLinesDto object after the mapping
     */
	public ExpenseWithLinesDto BeanToDtoWithLines(Expense exp){
		ExpenseWithLinesDto newExp= new ExpenseWithLinesDto();
		newExp.setDate(exp.getDate());
		newExp.setStatus(exp.getStatus());
		newExp.setExpenseLines(exp.getExpenseLines());
		return newExp;
		
	}

}