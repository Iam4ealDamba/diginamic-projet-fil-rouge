package fr.projet.diginamic.backend.specs;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import fr.projet.diginamic.backend.entities.Mission;

public class MissionSpecifications {

    /**
     * Specification to filter missions by type.
     * 
     * @param type The type of mission to filter by.
     * @return A Specification object that filters missions based on their type.
     */
    public static Specification<Mission> hasType(String type) {
        return (root, query, criteriaBuilder) -> 
            type == null ? criteriaBuilder.conjunction() :
            criteriaBuilder.equal(root.get("MissionNature").get("label"), type);
    }

    /**
     * Specification to filter missions by the user's name (either first or last name).
     * 
     * @param userName The user name to filter by. It searches both 'firstName' and 'lastName'.
     * @return A Specification object that filters missions where the user's first or last name contains the provided username.
     */
    public static Specification<Mission> hasUserName(String userName) {
        return (root, query, criteriaBuilder) -> {
            if (userName == null) {
                return criteriaBuilder.conjunction();
            } else {
                Predicate lastNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("UserEntity").get("lastName")), "%" + userName.toLowerCase() + "%");
                Predicate firstNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("UserEntity").get("firstName")), "%" + userName.toLowerCase() + "%");
                return criteriaBuilder.or(lastNamePredicate, firstNamePredicate);
            }
        };
    }

    /**
     * Specification to filter missions by status.
     * 
     * @param status The status to filter missions by.
     * @return A Specification object that filters missions based on their status.
     */
    public static Specification<Mission> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> 
            status == null ? criteriaBuilder.conjunction() :
            criteriaBuilder.equal(root.get("Mission"), status);
    }

    /**
     * Combines specifications to filter missions by status and user name.
     * 
     * @param status The status to filter missions by.
     * @param userName The user name to filter by. It searches both 'firstName' and 'lastName'.
     * @return A Specification object that applies both filters.
     */
    public static Specification<Mission> filterByStatusAndUserName(String status, String userName) {
        return Specification.where(hasStatus(status)).and(hasUserName(userName));
    }
}

