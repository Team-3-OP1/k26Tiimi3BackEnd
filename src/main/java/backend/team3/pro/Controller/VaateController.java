package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import backend.team3.pro.Model.Vaate;
import backend.team3.pro.Model.Valmistaja;
import backend.team3.pro.Repository.VaateRepository;
import backend.team3.pro.Repository.ValmistajaRepository;

@Controller
public class VaateController {

    private final VaateRepository vaateRepository;
    private final ValmistajaRepository valmistajaRepository;

    public VaateController(VaateRepository vaateRepository, ValmistajaRepository valmistajaRepository) {
        this.vaateRepository = vaateRepository;
        this.valmistajaRepository = valmistajaRepository;
    }

    @GetMapping("/addvaate")
    public String naytaLomake(Model model) {
        model.addAttribute("vaate", new Vaate());
        model.addAttribute("valmistajat", valmistajaRepository.findAll());
        return "addvaate";
    }

    @PostMapping("/tallenna")
    public String tallenna(Vaate vaate) {
        if (vaate.getValmistaja() != null && vaate.getValmistaja().getId() != null) {
            Valmistaja valmistaja = valmistajaRepository.findById(vaate.getValmistaja().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Valmistajaa ei loydy annetulla id:lla"));
            vaate.setValmistaja(valmistaja);
        }

        vaateRepository.save(vaate);
        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public String showHomepage(Model model) {
        model.addAttribute("vaatteet", vaateRepository.findAll());
        return "Homepage";
    }

}
