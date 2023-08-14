package exercise.controller;
import exercise.model.QUser;
import exercise.model.User;
import exercise.repository.UserRepository;
import exercise.service.SearchCriteria;
import exercise.service.UserSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

// Зависимости для самостоятельной работы
 import org.springframework.data.querydsl.binding.QuerydslPredicate;
 import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserRepository userRepository;

    // BEGIN
    /*@GetMapping(path = "")
    public Iterable<User> getUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        /*List<Specification> specificationList = new ArrayList<>();

        if (firstName != null) {
            specificationList.add(new UserSpecification(new SearchCriteria("firstName" , firstName)));
        }

        if (lastName != null) {
            specificationList.add(new UserSpecification(new SearchCriteria("lastName" , lastName)));
        }

        Specification<User> resultSpecification =
                specificationList.stream()
                        .reduce(null, ((specification, specification2) -> {
                            if (specification == null) {
                                return specification2;
                            }
                        return specification.and(specification2);
                        }));

        if (!specificationList.isEmpty()) {
            return userRepository.findAll(resultSpecification);
        }

        return userRepository.findAll();
*/

        //Дополнительная задача
        /*if (firstName == null && lastName == null) {
            return userRepository.findAll();
        }

        if (firstName == null) {
            return userRepository.findAll(QUser.user.lastName.containsIgnoreCase(lastName));
        }

        if (lastName == null) {
            return userRepository.findAll(QUser.user.firstName.containsIgnoreCase(firstName));
        }

        return userRepository.findAll(
                QUser.user.lastName.containsIgnoreCase(lastName)
                        .and(
                                QUser.user.firstName.containsIgnoreCase(firstName))
        );*/
    @GetMapping(path = "")
    public Iterable<User> getUsers(@QuerydslPredicate(root = User.class) Predicate predicate) {
        return userRepository.findAll(predicate);

    }
    // END
}

