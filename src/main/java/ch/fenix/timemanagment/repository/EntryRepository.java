package ch.fenix.timemanagment.repository;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.models.Category;
import ch.fenix.timemanagment.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> getAllByUserUsername(String username);
    int deleteByIdAndUserUsername(Long id, String username);
    Entry getByIdAndUserUsername(Long id, String user_username);
    List<Entry> getAllByUserAndCategory(ApplicationUser user, Category category);
}
