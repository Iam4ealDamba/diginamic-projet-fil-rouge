package fr.projet.diginamic.backend.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import fr.projet.diginamic.backend.entities.UserEntity;

/** User repository interface */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /** Get user by email 
     * 
     * @param email - the email of the user
    */
    public Optional<UserEntity> findByEmail(String email);
}
