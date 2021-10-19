package ch.fenix.timemanagment.services;

import ch.fenix.timemanagment.models.ApplicationUser;
import ch.fenix.timemanagment.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not found!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public ApplicationUser getUserByUsername(String username) {
        return applicationUserRepository.findByUsername(username);
    }

    public int deleteUserById(long id) {
        return applicationUserRepository.deleteApplicationUserById(id);
    }

    public boolean saveUser(ApplicationUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (applicationUserRepository.findByUsername(user.getUsername()) == null) {
            applicationUserRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }

    public ApplicationUser updateUser(String username, String password) {
        ApplicationUser user = getUserByUsername(username);
        if (user != null && password != null) {
            user.setPassword(passwordEncoder.encode(password));
            return user;
        }
        return null;
    }

    public ApplicationUser updateUser(String username, String password, long id) {
        ApplicationUser user = applicationUserRepository.findApplicationUserById(id);
        if (user != null) {
            if (username != null) {
                user.setUsername(username);
            }
            if (password != null) {
                user.setPassword(passwordEncoder.encode(password));
            }
            return user;
        }
        return null;
    }

    public List<ApplicationUser> findAll() {
        return applicationUserRepository.findAll();
    }
}