package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // Näytä käyttäjälle uuden vaatteen lisäys lomake
    @GetMapping("/addvaate")
    public String naytaLomake(Model model) {
        model.addAttribute("vaate", new Vaate());
        model.addAttribute("valmistajat", valmistajaRepository.findAll());
        return "addvaate";
    }

    // Poista vaate tietokannasta
    @GetMapping("/delete/{id}")
    public String deleteVaate(@PathVariable("id") Long id) {
        if (!vaateRepository.existsById(id)) {
            throw new IllegalArgumentException("Vaate ei loydy annetulla id:lla");
        }

        vaateRepository.deleteById(id);
        return "redirect:/homepage";
    }

    // Tallenna uusi Vaate tietokantaan
    @PostMapping("/tallenna")
    public String tallenna(Vaate vaate) {
        if (vaate.getValmistaja() == null || vaate.getValmistaja().getId() == null) {
            throw new IllegalArgumentException("Valmistaja on pakollinen");
        }

        Valmistaja valmistaja = valmistajaRepository.findById(vaate.getValmistaja().getId())
                .orElseThrow(() -> new IllegalArgumentException("Valmistajaa ei loydy annetulla id:lla"));
        vaate.setValmistaja(valmistaja);

        vaateRepository.save(vaate);
        return "redirect:/homepage";
    }

    @GetMapping("/edit/{id}")
    public String editVaate(@PathVariable("id") Long id, Model model) {
        Vaate vaate = vaateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaate ei loydy annetulla id:lla"));
        model.addAttribute("vaate", vaate);
        model.addAttribute("valmistajat", valmistajaRepository.findAll());
        return "editvaate";
    }

}
