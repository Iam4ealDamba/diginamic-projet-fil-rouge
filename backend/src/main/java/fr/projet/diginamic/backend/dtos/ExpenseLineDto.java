package fr.projet.diginamic.backend.dtos;

import java.util.Date;
import java.util.Set;

import fr.projet.diginamic.backend.entities.Expense;
import fr.projet.diginamic.backend.entities.ExpenseType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** The Dto of the expenseLine */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExpenseLineDto {
	
	/** The Id of the expense */
	private Long id;
	
	/** The date of the expenseLineDto */
	private Date date;

	/** The tva of the expenseLineDto */
	private double tva;

	/** The amount of the expenseLineDto */
	private double amount;
 
	 /** The type of this expenseLineDto */
	 public String expenseType;

}

