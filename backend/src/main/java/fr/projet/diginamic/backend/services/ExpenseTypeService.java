package fr.projet.diginamic.backend.services;

import fr.projet.diginamic.backend.dtos.ExpenseTypeDto;
import fr.projet.diginamic.backend.utils.ExpenseTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import fr.projet.diginamic.backend.dtos.ExpenseLineDto;
import fr.projet.diginamic.backend.entities.ExpenseLine;
import fr.projet.diginamic.backend.entities.ExpenseType;
import fr.projet.diginamic.backend.repositories.interfaces.ExpenseLineRepository;
import fr.projet.diginamic.backend.repositories.interfaces.ExpenseTypeRepository;
import fr.projet.diginamic.backend.utils.ExpenseLineMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeService {



	@Autowired
	private ExpenseTypeRepository expenseTypeRepo;

	@Autowired
	private ExpenseTypeMapper expenseTypeMapper;
	
	/** method to get all ExpenseTypes and transform them into ExpenseTypeDto
     * @return expenseTypeDto, a Dto with all ExpenseType
     */
	public List<ExpenseTypeDto> getExpenseTypes(){
    	List<ExpenseType> expenseTypes=expenseTypeRepo.findAll();
    	List<ExpenseTypeDto> expenseTypesDto = expenseTypes.stream()
				.map(expenseTypeMapper::BeanToDto)
				.toList();
		return expenseTypesDto;
	}

	/**
	 * Method to get an expenseType his id transform it into ExpenseTypeDto
	 * 
	 * @param id, the id of the expenseType
	 * @return An expenseTypeDto
	 */
	public ExpenseTypeDto getExpenseType(Long id) throws Exception {
		ExpenseType expenseType = expenseTypeRepo.findById(id).orElseThrow(Exception::new);;
        return expenseTypeMapper.BeanToDto(expenseType);
	}

	/**
	 * Method to save an expenseType
	 * 
	 * @param expenseType, the expenseType to save
	 * @return the expenseType who was saved
	 */
	public ExpenseType saveExpenseType(ExpenseTypeDto expenseType) {
		ExpenseType expenseTypeSave = expenseTypeMapper.dtoToBean(expenseType);
		expenseTypeSave = expenseTypeRepo.save(expenseTypeSave);
		return expenseTypeSave;
	}

	/**
	 * Method to delete an expenseType by its id
	 * 
	 * @param id, the id of the expenseType to delete
	 * @return the expenseType deleted
	 */
	public ExpenseType deleteExpenseType(Long id) throws Exception {
		ExpenseType expenseType = expenseTypeRepo.findById(id).orElseThrow(Exception::new);;
		expenseTypeRepo.delete(expenseType);
		return expenseType;
	}

	/**
	 * Method to modify an expenseType
	 * 
	 * @param expenseType, the new expenseType
	 * @param id,          the id of the expenseType to modify
	 * @return the expenseType after modification
	 */
	public ExpenseType modifyExpenseType(ExpenseTypeDto expenseType, Long id) throws Exception {
		ExpenseType expenseTypeBdd = expenseTypeRepo.findById(id).orElseThrow(Exception::new);;
		expenseTypeBdd.setType(expenseType.getType());

		return expenseTypeBdd;
	}

}
