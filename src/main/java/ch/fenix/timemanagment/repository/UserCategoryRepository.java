package ch.fenix.timemanagment.repository;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.models.Category;
import ch.fenix.timemanagment.models.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    UserCategory getUserCategoriesByCategoryAndApplicationUser(Category category, ApplicationUser applicationUser);
}
