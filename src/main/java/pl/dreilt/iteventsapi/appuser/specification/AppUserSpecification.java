package pl.dreilt.iteventsapi.appuser.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.dreilt.iteventsapi.appuser.model.AppUser;

import javax.persistence.criteria.Predicate;

public class AppUserSpecification {

    private AppUserSpecification() {
    }

    public static Specification<AppUser> bySearch(String searchWord) {
        return (root, query, criteriaBuilder) -> {
            Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + searchWord + "%");
            Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + searchWord + "%");
            Predicate email = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchWord + "%");
            return criteriaBuilder.or(firstName, lastName, email);
        };
    }

    public static Specification<AppUser> bySearch(String searchWord, String searchWord2) {
        return (root, query, criteriaBuilder) -> {
            Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + searchWord + "%");
            Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + searchWord2 + "%");
            return criteriaBuilder.and(firstName, lastName);
        };
    }
}
