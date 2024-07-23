package fr.projet.diginamic.backend.utils;

import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.ExpenseTypeDto;
import fr.projet.diginamic.backend.entities.ExpenseType;

@Service
public class ExpenseTypeMapper {
	
	/**
	 * Method to transform a ExpenseTypeDto to an ExpenseType
	 * 
	 * @param exp, the dto to transform
	 * @return the expenseType object after the mapping
	 */
	public ExpenseType dtoToBean(ExpenseTypeDto exp) {
		ExpenseType newExpType = new ExpenseType();
		newExpType.setType(exp.getType());
		return newExpType;
	}

	/**
	 * Method to transform a ExpenseLine to an ExpenseLineDto
	 * 
	 * @param exp, the bean to transform
	 * @return the expenseLineDto object after the mapping
	 */
	public ExpenseTypeDto BeanToDto(ExpenseType exp) {
		ExpenseTypeDto newExpType = new ExpenseTypeDto();
		newExpType.setId(exp.getId());
		newExpType.setType(exp.getType());
		return newExpType;

	}

}
