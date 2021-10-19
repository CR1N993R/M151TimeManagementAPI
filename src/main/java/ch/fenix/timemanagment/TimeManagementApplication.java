package ch.fenix.timemanagment;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.services.ApplicationUserService;
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
    CommandLineRunner run (ApplicationUserService userService) {
        return args -> {
          userService.saveUser(new ApplicationUser("cedric", "1234"));
        };
    }
}
