package ch.fenix.timemanagment.services;

import ch.fenix.timemanagment.models.Role;
import ch.fenix.timemanagment.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleById(long id) {
        return roleRepository.getById(id);
    }

    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }
}
