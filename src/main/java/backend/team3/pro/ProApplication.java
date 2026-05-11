package backend.team3.pro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import backend.team3.pro.Model.AppUser;
import backend.team3.pro.Model.Role;
import backend.team3.pro.Model.Tyyppi;
import backend.team3.pro.Model.Valmistaja;
import backend.team3.pro.Repository.AppUserRepository;
import backend.team3.pro.Repository.RoleRepository;
import backend.team3.pro.Repository.TyyppiRepository;
import backend.team3.pro.Repository.ValmistajaRepository;

@SpringBootApplication
public class ProApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProApplication.class, args);
    }

    @Bean
    CommandLineRunner initValmistajat(ValmistajaRepository valmistajaRepository,
                                      TyyppiRepository tyyppiRepository) {
        return args -> {
            // Add basic manufacturers if they do not already exist.
            if (!valmistajaRepository.existsByName("Rukka"))
                valmistajaRepository.save(new Valmistaja("Rukka"));
            if (!valmistajaRepository.existsByName("Luhta"))
                valmistajaRepository.save(new Valmistaja("Luhta"));

            // Add basic product types if they do not already exist.
            if (!tyyppiRepository.existsByNimi("vaate"))
                tyyppiRepository.save(new Tyyppi("vaate"));
            if (!tyyppiRepository.existsByNimi("ruoka"))
                tyyppiRepository.save(new Tyyppi("ruoka"));
            if (!tyyppiRepository.existsByNimi("lelu"))
                tyyppiRepository.save(new Tyyppi("lelu"));
        };
    }

    @Bean
    CommandLineRunner initSecurity(AppUserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Create default roles used by Spring Security.
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

            roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(new Role("USER")));

            // Create the first admin user only if it does not already exist.
            if (!userRepository.existsByUsername("admin")) {
                AppUser admin = new AppUser("admin", passwordEncoder.encode("admin123"));
                admin.addRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}
