package ch.fenix.timemanagment.repository;

import ch.fenix.timemanagment.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    ApplicationUser getApplicationUserById(long id);
    int deleteApplicationUserById(long id);
    ApplicationUser findApplicationUserById(long id);
}