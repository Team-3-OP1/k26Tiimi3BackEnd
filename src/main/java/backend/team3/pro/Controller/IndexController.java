package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.team3.pro.Model.Vaate;
import backend.team3.pro.Repository.VaateRepository;

@Controller
public class IndexController {
    public final VaateRepository repository;

    public IndexController(VaateRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("vaate", new Vaate());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editVaate(@PathVariable("id") Long id, Model model) {
        Vaate vaate = repository.findById(id).orElse(null);
        model.addAttribute("vaate", vaate);
        return "index";
    }

    @PostMapping("/index")
    public String saveVaatte(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("name") String name,
            @RequestParam("size") String size,
            @RequestParam("price") double price) {

        Vaate vaate;
        if (id != null) {
            vaate = repository.findById(id).orElse(new Vaate());
            vaate.setName(name);
            vaate.setSize(size);
            vaate.setPrice(price);
        } else {
            vaate = new Vaate(name, size, price);
        }
        repository.save(vaate);

        return "redirect:/homepage";
    }

}
