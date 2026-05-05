package backend.team3.pro.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                // BCrypt hashes passwords before they are saved to the database.
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // The H2 console needs CSRF disabled and same-origin frames to work in the browser.
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                                .authorizeHttpRequests(auth -> auth
                                                // Login, registration, and the H2 console are available without login.
                                                .requestMatchers("/login", "/register", "/h2-console/**").permitAll()

                                                // Public API GET requests can be used without authentication.
                                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                                                // Product management pages are restricted to ADMIN users.
                                                .requestMatchers("/addvaate", "/addvalmistaja", "/tallenna",
                                                                "/tallennavalmistaja",
                                                                "/edit/**", "/valmistaja/edit/**", "/delete/**",
                                                                "/deletevalmistaja/**")
                                                .hasRole("ADMIN")

                                                // Normal application pages require the user to be logged in.
                                                .requestMatchers("/", "/homepage", "/valmistajat", "/valmistaja/**")
                                                .authenticated()

                                                // Any route not listed above also requires authentication.
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                // Use the custom login page from templates/login.html.
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/homepage", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                // The application uses a GET logout link instead of a POST form.
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll());

                return http.build();
        }
}
