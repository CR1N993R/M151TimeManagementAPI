package ch.fenix.timemanagment;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.models.Role;
import ch.fenix.timemanagment.services.ApplicationUserService;
import ch.fenix.timemanagment.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TimeManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimeManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run (ApplicationUserService userService, RoleService roleService) {
        return args -> {
            ApplicationUser user = new ApplicationUser("root", "1234");
            ApplicationUser user1 = new ApplicationUser("cedric", "1234");
            ApplicationUser user2 = new ApplicationUser("miki", "1234");
            userService.saveUser(user);
            userService.saveUser(user1);
            userService.saveUser(user2);
            roleService.saveRole(new Role("ROLE_ADMIN"));
            userService.setRole(user, roleService.getRoleById(1));
        };
    }
}
