package ch.fenix.timemanagment.services;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.models.Category;
import ch.fenix.timemanagment.models.Entry;
import ch.fenix.timemanagment.models.UserCategory;
import ch.fenix.timemanagment.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;
    private final ApplicationUserService applicationUserService;

    public UserCategory getUserCategoryByCategoryAndUser(Category category, ApplicationUser applicationUser) {
        return userCategoryRepository.getUserCategoriesByCategoryAndApplicationUser(category, applicationUser);
    }

    public void saveUserCategory(UserCategory userCategory) {
        userCategoryRepository.saveAndFlush(userCategory);
    }

    public void createUserCategories(Category category) {
        for (ApplicationUser applicationUser : applicationUserService.findAll()) {
            UserCategory userCategory = new UserCategory(applicationUser, category);
            saveUserCategory(userCategory);
            applicationUser.getUserCategories().add(userCategory);
        }
    }

    public void calculateBalance(Category category, ApplicationUser applicationUser) {
        UserCategory userCategory = getUserCategoryByCategoryAndUser(category, applicationUser);
        if (userCategory != null) {
            List<Entry> entries = userCategory.getApplicationUser().getEntries();
            float sum = 0;
            float amount = category.getInterval().getChronoUnit().between(entries.get(0).getCheckIn(), entries.get(entries.size() - 1).getCheckIn()) * userCategory.getCategory().getMin();
            for (Entry entry : entries) {
                sum += MINUTES.between(entry.getCheckOut(), entry.getCheckIn()) / 60F;
            }
            userCategory.setBalance(Math.round((amount - sum) * 100.0F)/100.0F);
        }
    }
}
