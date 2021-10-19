package ch.fenix.timemanagment.services;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.models.Category;
import ch.fenix.timemanagment.models.Entry;
import ch.fenix.timemanagment.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EntryService {
    private final EntryRepository entryRepository;
    private final CategoryService categoryService;
    private final ApplicationUserService userService;
    private final UserCategoryService userCategoryService;

    public Entry createEntry(LocalDateTime checkIn, LocalDateTime checkOut, String username, long categoryId) {
        ApplicationUser user = userService.getUserByUsername(username);
        Category category = categoryService.getCategoryById(categoryId);
        Entry entry = new Entry(checkIn, checkOut, user, category);
        user.getEntries().add(entry);
        userCategoryService.calculateBalance(category, user);
        return entryRepository.saveAndFlush(entry);
    }

    public int deleteEntryByUsername(long id, String username) {
        return entryRepository.deleteByIdAndUserUsername(id, username);
    }

    public List<Entry> getEntriesByUsername(String username) {
        return entryRepository.getAllByUserUsername(username);
    }

    public Entry editEntry(long id, long categoryId, String username, LocalDateTime checkIn, LocalDateTime checkOut) {
        Entry entry = entryRepository.getByIdAndUserUsername(id, username);
        Category category = categoryService.getCategoryById(categoryId);
        if (entry != null) {
            if (category != null) {
                entry.setCategory(category);
            }
            if (checkIn != null) {
                entry.setCheckIn(checkIn);
            }
            if (checkOut != null) {
                entry.setCheckOut(checkOut);
            }
            userCategoryService.calculateBalance(category, userService.getUserByUsername(username));
            return entry;
        }
        return null;
    }
}
