package fr.projet.diginamic.backend.dtos;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** The Dto of the expenseType */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExpenseTypeDto {
	
	/** The id of the expenseTypeDto */
	private Long id;
	
	/** The type of the expenseTypeDto */
	private String type;

}
