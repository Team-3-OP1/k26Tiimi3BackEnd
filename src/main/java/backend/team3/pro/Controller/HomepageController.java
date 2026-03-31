package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {
    @GetMapping("/homepage")
    public String homepage() {
        return "homepage";
    }

    
}
