package fr.projet.diginamic.backend.repositories.interfaces;

import fr.projet.diginamic.backend.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.projet.diginamic.backend.entities.Expense;

import java.util.Date;
import java.util.List;

/** Repository of all Expense method to search from database*/
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	/**Method to get all expenses on the database
	 * @param pageable the pagination and sorting information.
     * @return An arrayList of all expenses
     */
	Page<Expense> findAll(Pageable pageable);

	/**Method to get all expenses from one user by his id
	 * @param id, the user id.
	 * @param pageable the pagination and sorting information.
	 * @return An arrayList of all expenses
	 */
	Page<Expense> findByMission_User_Id(Long id, Pageable pageable);

	/**Method to get all expenses from users who got this Manager.
	 * @param manager, the user Entity who is a manager.
	 * @param pageable the pagination and sorting information.
	 * @return An arrayList of all expenses
	 */
	Page<Expense> findByMission_User_Manager(UserEntity manager, Pageable pageable);

	/**Method check if the expense with this id is from a mission with this user.
	 * @param user, the user Entity.
	 * @param id, the id of the expense.
	 * @return a boolean
	 */
	boolean existsByIdAndMission_User(Long id, UserEntity user);

	/**Method check if the expense with this id is from a mission with this manager.
	 * @param manager, the user Entity.
	 * @param id, the id of the expense.
	 * @return a boolean
	 */
	boolean existsByIdAndMission_User_Manager(Long id, UserEntity manager);

}
