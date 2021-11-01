package ch.fenix.timemanagment.repository;

import ch.fenix.timemanagment.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);
    Role getRoleById(long id);
}
