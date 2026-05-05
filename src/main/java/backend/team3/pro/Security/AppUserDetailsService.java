package backend.team3.pro.Security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.team3.pro.Repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    // Repository used to load users from the database during login.
    private final AppUserRepository userRepository;

    public AppUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Spring Security calls this method when a user tries to log in.
        var appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User ei loydy"));

        // Convert database roles into Spring Security authorities.
        List<GrantedAuthority> authorities = appUser.getRoles().stream()
                .<GrantedAuthority>map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();

        // Return the user data in the format expected by Spring Security.
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
