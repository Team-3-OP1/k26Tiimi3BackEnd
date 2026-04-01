package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import backend.team3.pro.Model.Valmistaja;
import backend.team3.pro.Repository.ValmistajaRepository;

@Controller
public class ValmistajaController {

    private final ValmistajaRepository valmistajaRepository;

    public ValmistajaController(ValmistajaRepository valmistajaRepository) {
        this.valmistajaRepository = valmistajaRepository;
    }

    @GetMapping("/addvalmistaja")
    public String naytaValmistajaLomake(Model model) {
        model.addAttribute("valmistaja", new Valmistaja());
        return "addvalmistaja";
    }

    @PostMapping("/tallennavalmistaja")
    public String tallennaValmistaja(Valmistaja valmistaja, Model model) {
        if (valmistaja.getName() == null || valmistaja.getName().trim().isEmpty()) {
            model.addAttribute("error", "Valmistajan nimi on pakollinen");
            model.addAttribute("valmistaja", valmistaja);
            return "addvalmistaja";
        }

        String nimi = valmistaja.getName().trim();
        if (valmistajaRepository.existsByName(nimi)) {
            model.addAttribute("error", "Valmistaja on jo olemassa");
            model.addAttribute("valmistaja", valmistaja);
            return "addvalmistaja";
        }

        valmistaja.setName(nimi);
        valmistajaRepository.save(valmistaja);
        return "redirect:/homepage";
    }
}