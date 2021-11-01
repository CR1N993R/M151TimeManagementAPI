package ch.fenix.timemanagment.controller;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.models.Role;
import ch.fenix.timemanagment.services.ApplicationUserService;
import ch.fenix.timemanagment.services.RoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final ApplicationUserService userService;
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> addRoleToUser(@RequestBody ComRole comRole) {
        ApplicationUser user = userService.getUserById(comRole.getUserId());
        Role role = roleService.getRoleById(comRole.getRoleId());
        if (user != null) {
            userService.setRole(user, role);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @Data
    static class ComRole {
        long userId;
        long roleId;
    }
}
