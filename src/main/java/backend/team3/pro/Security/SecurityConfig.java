package backend.team3.pro.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                // BCrypt hashes passwords before they are saved to the database.
                return new BCryptPasswordEncoder();
        }

        @Bean
        public org.springframework.security.authentication.AuthenticationManager authenticationManager(
                        org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration authConfig)
                        throws Exception {
                return authConfig.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter)
                        throws Exception {
                http
                                // The H2 console and API endpoints need CSRF disabled for API clients to
                                // interact without a browser CSRF token.
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"))
                                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .authorizeHttpRequests(auth -> auth
                                                // Login, registration, and the H2 console are available without login.
                                                .requestMatchers("/login", "/register", "/h2-console/**").permitAll()

                                                // Public API GET requests can be used without authentication.
                                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                                // Allow unauthenticated POSTs for authentication endpoints
                                                // (login/register).
                                                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()

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

                // Add JWT authentication filter so API requests carrying a Bearer token are
                // authorized.
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of(
                                "https://frontendtiimi3-opt3frontend.2.rahtiapp.fi",
                                "http://localhost:5173"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }

        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil,
                        AppUserDetailsService userDetailsService) {
                return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        }
}
