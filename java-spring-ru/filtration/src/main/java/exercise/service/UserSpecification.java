package exercise.service;

import exercise.model.User;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public final class UserSpecification implements Specification<User> {

    private SearchCriteria searchCriteria;

    public UserSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // BEGIN
        return  criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get(searchCriteria.getKey())),
                        "%" + searchCriteria.getValue().toString().toLowerCase() + "%");
        // END
    }
}
