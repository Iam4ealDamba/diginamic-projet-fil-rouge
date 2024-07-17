package fr.projet.diginamic.backend.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.projet.diginamic.backend.entities.ExpenseType;

/** Repository of all ExpenseType method to search from database*/
@Repository
public interface ExpenseTypeRepository  extends JpaRepository<ExpenseType, Long> {

}
