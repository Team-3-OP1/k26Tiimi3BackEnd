package backend.team3.pro.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.team3.pro.Model.Vaate;
import backend.team3.pro.Repository.VaateRepository;

@Controller
public class IndexController {

    @Autowired
    private VaateRepository repository;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/index")
    public String saveVaatte(@RequestParam("name") String name,
            @RequestParam("size") String size,
            @RequestParam("price") double price) {

        Vaate newVaatte = new Vaate(name, size, price);
        repository.save(newVaatte);

        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public String showHomepage(Model model) {
        model.addAttribute("vaatteet", repository.findAll());
        return "Homepage";
    }

}
