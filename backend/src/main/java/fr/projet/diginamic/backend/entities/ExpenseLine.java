package fr.projet.diginamic.backend.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** The entity expenseLine */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class ExpenseLine {
	/** The Id of the expenseLine */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The date of the expenseLine */
    @Column(name = "date")
	private Date date;
    
    /** The tva of the expenseLine */
    @Column(name = "tva")
	private double tva;
    
    /** The amount of the expenseLine */
    @Column(name = "amount")
	private double amount;
	
    /** The expense of this expenseLine */
	 @ManyToOne
	 @JoinColumn(name = "expense_id", referencedColumnName = "id", nullable = true)
	 public Expense expense;
	 
	 /** The type of this expenseLine */
	 @ManyToOne
	 @JoinColumn(name = "expense_type_id", referencedColumnName = "id", nullable = true)
	 public ExpenseType expenseType;

}
