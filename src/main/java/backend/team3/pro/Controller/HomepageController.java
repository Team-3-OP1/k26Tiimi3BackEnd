package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import backend.team3.pro.Repository.VaateRepository;

@Controller
public class HomepageController {

    private final VaateRepository repository;

    public HomepageController(VaateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/homepage")
    public String showHomepage(Model model) {
        model.addAttribute("tuotteet", repository.findAll());
        return "Homepage";
    }
}
