package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import backend.team3.pro.Model.Vaate;
import backend.team3.pro.Model.Valmistaja;
import backend.team3.pro.Repository.VaateRepository;
import backend.team3.pro.Repository.ValmistajaRepository;
import jakarta.validation.Valid;

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

    @GetMapping("/delete/{id}")
    public String deleteVaate(@PathVariable("id") Long id) {
        if (!vaateRepository.existsById(id)) {
            throw new IllegalArgumentException("Vaate ei loydy annetulla id:lla");
        }

        vaateRepository.deleteById(id);
        return "redirect:/homepage";
    }

    @PostMapping("/tallenna")
    public String tallenna(@Valid Vaate vaate, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("valmistajat", valmistajaRepository.findAll());
            return "addvaate";
        }

        if (vaate.getValmistaja() == null || vaate.getValmistaja().getId() == null) {
            model.addAttribute("valmistajat", valmistajaRepository.findAll());
            model.addAttribute("valmistajaVirhe", "Valmistaja on pakollinen");
            return "addvaate";
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