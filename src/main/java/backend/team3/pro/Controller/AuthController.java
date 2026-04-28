package backend.team3.pro.Controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.team3.pro.Model.AppUser;
import backend.team3.pro.Repository.AppUserRepository;

@Controller
public class AuthController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String homepage() {
        return "redirect:/homepage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Kayttajatunnus ja salasana ovat pakollisia");
            return "register";
        }

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Kayttajatunnus on jo kaytossa");
            return "register";
        }

        userRepository.save(new AppUser(username, passwordEncoder.encode(password), "USER"));
        return "redirect:/login?registered";
    }
}