package fr.projet.diginamic.backend.specs;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.entities.UserEntity;

public class MissionSpecifications {

    /**
     * Specification to filter missions by status using the 'type' field of the
     * StatusEnum.
     * 
     * @param statusStr The status description to filter missions by.
     * @return A Specification object that filters missions based on their status
     *         description.
     */
    public static Specification<Mission> hasStatus(String statusStr) {
        return (root, query, criteriaBuilder) -> {
            if (statusStr == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), statusStr);
        };
    }

    /**
     * Specification to filter missions by the label of their nature.
     * 
     * @param nature The label of the nature to filter missions by.
     * @return A Specification<Mission> object that filters missions based on their
     *         nature's label.
     */
    public static Specification<Mission> hasNature(String nature) {
        return (root, query, criteriaBuilder) -> {
            if (nature == null) {
                return criteriaBuilder.conjunction(); // Returns all if no nature is specified
            }
            return criteriaBuilder.equal(root.join("NatureMission").get("label"), nature);
        };
    }

    /**
     * Specification to filter missions by the user's name (either first or last
     * name).
     * 
     * @param userName The user name to filter by. It searches both 'firstName' and
     *                 'lastName'.
     * @return A Specification object that filters missions where the user's first
     *         or last name contains the provided username.
     */
    public static Specification<Mission> hasUserName(String userName) {
        return (root, query, criteriaBuilder) -> {
            if (userName == null) {
                return criteriaBuilder.disjunction();
            } else {
                Join<Mission, UserEntity> userJoin = root.join("user", JoinType.LEFT);
                Predicate firstNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(userJoin.get("firstName")), "%" + userName.toLowerCase() + "%");

                Predicate lastNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(userJoin.get("lastName")), "%" + userName.toLowerCase() + "%");

                return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
            }
        };
    }

    public static Specification<Mission> hasLabel(String label) {
        return (root, query, criteriaBuilder) -> {
            if (label == null) {
                return criteriaBuilder.disjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("label")), "%" + label.toLowerCase() + "%");
            }
        };
    }

    /**
     * Combines multiple search and filter specifications into one based on various
     * mission attributes.
     * This specification allows filtering by status, nature, user name, and mission
     * label.
     *
     * @param status          The status of the mission to filter by.
     * @param nature          The nature of the mission to filter by.
     * @param labelOrUsername Either a username or a mission's label to filter by.
     * 
     * @return A combined specification based on the provided filters.
     */

    public static Specification<Mission> filterMissionsByCriteriaForManager(String status, String nature,
            String labelOrUsername) {
        Specification<Mission> spec = Specification.where(null);

        if (status != null && !status.isEmpty()) {
            spec = spec.and(hasStatus(status));
        }

        if (nature != null && !nature.isEmpty()) {
            spec = spec.and(hasNature(nature));
        }

        if (labelOrUsername != null && !labelOrUsername.isEmpty()) {
            spec = spec.and(Specification.where(hasUserName(labelOrUsername)).or(hasLabel(labelOrUsername)));
        }
        return spec;
    }

    public static Specification<Mission> filterMissionsByCriteriaForEmployee(String status, String nature,
            String label) {
        Specification<Mission> spec = Specification.where(null);
        if (status != null && !status.isEmpty()) {
            spec = spec.and(hasStatus(status));
        }
        if (nature != null && !nature.isEmpty()) {
            spec = spec.and(hasNature(nature));
        }
        if (label != null && !label.isEmpty()) {
            spec = spec.and(hasLabel(label));
        }
        return spec;
    }

}
