package backend.team3.pro.Controller.API;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.team3.pro.Model.AppUser;
import backend.team3.pro.Model.Asiakas;
import backend.team3.pro.Model.Role;
import backend.team3.pro.Repository.AppUserRepository;
import backend.team3.pro.Repository.AsiakasRepository;
import backend.team3.pro.Repository.RoleRepository;
import backend.team3.pro.Security.JwtUtil;

@CrossOrigin(origins = "https://frontendtiimi3-opt3frontend.2.rahtiapp.fi/")
@RequestMapping("/api/auth")
@RestController
public class AuthRestController {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AsiakasRepository asiakasRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final backend.team3.pro.Security.JwtUtil jwtUtil;

    public AuthRestController(AppUserRepository userRepository, RoleRepository roleRepository,
            AsiakasRepository asiakasRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.asiakasRepository = asiakasRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (req.username == null || req.username.isBlank() || req.password == null || req.password.isBlank()) {
            return ResponseEntity.badRequest().body(new ApiError("Username and password are required"));
        }

        if (userRepository.existsByUsername(req.username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError("Username already in use"));
        }

        if (req.firstName == null || req.firstName.isBlank()) {
            return ResponseEntity.badRequest().body(new ApiError("First name is required"));
        }
        if (req.email == null || req.email.isBlank()) {
            return ResponseEntity.badRequest().body(new ApiError("Email is required"));
        }

        Role userRole = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new Role("USER")));

        AppUser user = new AppUser(req.username, passwordEncoder.encode(req.password));
        user.addRole(userRole);
        userRepository.save(user);

        Asiakas asiakas = new Asiakas(req.firstName, req.lastName != null ? req.lastName : "", req.email, req.username);
        asiakasRepository.save(asiakas);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiMessage("User and customer profile created"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.username == null || loginRequest.password == null) {
            return ResponseEntity.badRequest().body(new ApiError("Missing credentials"));
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            Asiakas asiakas = asiakasRepository.findByUsername(loginRequest.username)
                    .orElseThrow(() -> new RuntimeException("Customer profile not found for username: " + loginRequest.username));

            var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
            String tokenStr = jwtUtil.generateToken(loginRequest.username, authorities);

            return ResponseEntity.ok(new AuthResponse(loginRequest.username, authorities, tokenStr, asiakas.getId()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("Invalid username or password"));
        }
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

    public static class RegisterRequest {
        public String username;
        public String password;
        public String firstName;
        public String lastName;
        public String email;
    }

    public record AuthResponse(String username, List<String> roles, String token, Long id) {
    }

    public record ApiError(String error) {
    }

    public record ApiMessage(String message) {
    }
}