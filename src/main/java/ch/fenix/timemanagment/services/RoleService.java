package ch.fenix.timemanagment.services;

import ch.fenix.timemanagment.models.Role;
import ch.fenix.timemanagment.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleById(long id) {
        return roleRepository.getRoleById(id);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public void saveRole(Role role) {
        roleRepository.saveAndFlush(role);
    }
}
