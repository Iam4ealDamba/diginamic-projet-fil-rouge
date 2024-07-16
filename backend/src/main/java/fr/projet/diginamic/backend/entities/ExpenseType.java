package fr.projet.diginamic.backend.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** The entity of the expenseType */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class ExpenseType {
	
	/** The id of the expenseType */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The type of the expenseType */
	private String type;
	
	/** The expenseLines who are of this expenseType */
	@OneToMany(mappedBy = "expenseType")
	public Set<ExpenseLine> expenseLines;

}
