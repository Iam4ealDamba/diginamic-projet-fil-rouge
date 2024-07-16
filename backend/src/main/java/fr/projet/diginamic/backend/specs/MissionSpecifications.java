package fr.projet.diginamic.backend.specs;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import fr.projet.diginamic.backend.entities.Mission;

public class MissionSpecifications {

	public static Specification<Mission> hasType(String type) {
		return (root, query, criteriaBuilder) -> type == null ? criteriaBuilder.conjunction()
				: criteriaBuilder.equal(root.get("missionNature").get("label"), type);
	}

	public static Specification<Mission> hasUserName(String userName) {
		return (root, query, criteriaBuilder) -> {
			if (userName == null) {
				return criteriaBuilder.conjunction();
			} else {
				Predicate lastNamePredicate = criteriaBuilder.like(
						criteriaBuilder.lower(root.get("user").get("lastName")), "%" + userName.toLowerCase() + "%");
				Predicate firstNamePredicate = criteriaBuilder.like(
						criteriaBuilder.lower(root.get("user").get("firstName")), "%" + userName.toLowerCase() + "%");
				return criteriaBuilder.or(lastNamePredicate, firstNamePredicate);
			}
		};
	}

	public static Specification<Mission> hasStatus(String status) {
		return (root, query, criteriaBuilder) -> status == null ? criteriaBuilder.conjunction()
				: criteriaBuilder.equal(root.get("status"), status);
	}

	public static Specification<Mission> filterByStatusAndUserName(String status, String userName) {
		return Specification.where(hasStatus(status)).and(hasUserName(userName));
	}
}
