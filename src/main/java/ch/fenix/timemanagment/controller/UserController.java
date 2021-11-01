package ch.fenix.timemanagment.controller;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.services.ApplicationUserService;
import com.auth0.jwt.JWT;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ch.fenix.timemanagment.configuration.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final ApplicationUserService applicationUserService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody ApplicationUser user) {
        if (applicationUserService.saveUser(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping
    public ApplicationUser getUser(@RequestHeader String authorization) {
        String username = JWT.decode(authorization.replace(TOKEN_PREFIX, "")).getSubject();
        return applicationUserService.getUserByUsername(username);
    }

    @PutMapping
    public ResponseEntity<ApplicationUser> updateUser(@RequestBody UpdateUser updateUser, @RequestHeader String authorization) {
        String username = JWT.decode(authorization.replace(TOKEN_PREFIX, "")).getSubject();
        ApplicationUser user = applicationUserService.updateUser(username, updateUser.password);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationUser> updateUser(@RequestBody UpdateUser updateUser, @PathVariable long id) {
        ApplicationUser user = applicationUserService.updateUser(updateUser.username, updateUser.password, id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        if (applicationUserService.deleteUserById(id) == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationUser>> getAllUsers() {
        return new ResponseEntity<>(applicationUserService.findAll(), HttpStatus.OK);
    }

    @Data
    static class UpdateUser {
        String username;
        String password;
    }
}
