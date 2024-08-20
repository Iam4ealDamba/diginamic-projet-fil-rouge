package fr.projet.diginamic.backend.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** The Dto of the expense */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExpenseDto {
	
	/** The Id of the expense */
	private Long id;

	/** The date of the expense */
	private Date date;
	
    /** The date of the status */
	private String status;
	

}