package fr.projet.diginamic.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.ExpenseLineDto;
import fr.projet.diginamic.backend.entities.ExpenseLine;
import fr.projet.diginamic.backend.entities.ExpenseType;
import fr.projet.diginamic.backend.repositories.interfaces.ExpenseTypeRepository;


/**Service to transform ExpenseLine in Dtos and vice-versa*/
@Service
public class ExpenseLineMapper {
	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;

	/**Method to transform a ExpenseLineDto to an ExpenseLine
	 * @param ExpenseLineDto, the dto to transform
     * @return the expenseLine object after the mapping
     */
	public ExpenseLine dtoToBean( ExpenseLineDto exp){
		 ExpenseLine newExpLine= new  ExpenseLine();
		  newExpLine.setId(exp.getId());
			newExpLine.setTva(exp.getTva());
			newExpLine.setAmount(exp.getAmount());
			ExpenseType ExpType= expenseTypeRepository.findByType(exp.getExpenseType());
			if(ExpType==null) {
				newExpLine.setExpenseType(new ExpenseType().setType(exp.getExpenseType()));
			}
			else {
				newExpLine.setExpenseType(ExpType);
			}
			newExpLine.setDate(exp.getDate());
		return newExpLine;
		
	}
	
	/**Method to transform a ExpenseLine to an ExpenseLineDto
	 * @param ExpenseLine, the bean to transform
     * @return the expenseLineDto object after the mapping
     */
	public ExpenseLineDto BeanToDto( ExpenseLine exp){
		  ExpenseLineDto newExpLine= new ExpenseLineDto();
			newExpLine.setId(exp.getId());
			newExpLine.setTva(exp.getTva());
			newExpLine.setAmount(exp.getAmount());
			newExpLine.setExpenseType(exp.getExpenseType().getType());
			newExpLine.setDate(exp.getDate());
		return newExpLine;
		
	}

}
