package ch.fenix.timemanagment.services;

import ch.fenix.timemanagment.models.Category;
import ch.fenix.timemanagment.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserCategoryService userService;

    public Category saveCategory(Category category) {
        categoryRepository.saveAndFlush(category);
        if (category.getInterval() != null) {
            userService.createUserCategories(category);
        }
        return category;
    }

    public int deleteCategoryById(long id) {
        return categoryRepository.deleteCategoryById(id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(long id) {
        return categoryRepository.getCategoryById(id);
    }

}
