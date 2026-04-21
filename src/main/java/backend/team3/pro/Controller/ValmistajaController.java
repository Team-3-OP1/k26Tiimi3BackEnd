package backend.team3.pro.Controller;

import java.util.Optional;

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
public class ValmistajaController {

    private final ValmistajaRepository valmistajaRepository;
    private final VaateRepository vaateRepository;

    public ValmistajaController(ValmistajaRepository valmistajaRepository, VaateRepository vaateRepository) {
        this.valmistajaRepository = valmistajaRepository;
        this.vaateRepository = vaateRepository;
    }

    @GetMapping("/addvalmistaja")
    public String naytaValmistajaLomake(Model model) {
        model.addAttribute("valmistaja", new Valmistaja());
        return "addvalmistaja";
    }

    @GetMapping("/valmistajat")
    public String naytaValmistajat(Model model) {
        model.addAttribute("valmistajat", valmistajaRepository.findAll());
        return "valmistajat";
    }

    @GetMapping("/deletevalmistaja/{id}")
    public String poistaValmistaja(@PathVariable("id") Long id, Model model) {
        boolean onKaytossa = false;
        Iterable<Vaate> vaatteet = vaateRepository.findAll();

        for (Vaate v : vaatteet) {
            if (v.getValmistaja() != null && v.getValmistaja().getId().equals(id)) {
                onKaytossa = true;
                break;
            }
        }

        if (onKaytossa) {
            model.addAttribute("error", "Valmistajaa ei voi poistaa, koska se on käytössä!");
            model.addAttribute("valmistajat", valmistajaRepository.findAll());
            return "valmistajat";
        }

        valmistajaRepository.deleteById(id);
        return "redirect:/valmistajat";
    }

    @PostMapping("/tallennavalmistaja")
    public String tallennaValmistaja(@Valid Valmistaja valmistaja,
                                     BindingResult bindingResult,
                                     Model model) {

        if (bindingResult.hasErrors()) {
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

    @GetMapping("/valmistaja/edit/{id}")
    public String editValmistaja(@PathVariable("id") Long id, Model model) {
        Optional<Valmistaja> valmistajaOpt = valmistajaRepository.findById(id);

        if (valmistajaOpt.isEmpty()) {
            return "redirect:/homepage";
        }

        model.addAttribute("valmistaja", valmistajaOpt.get());
        return "editvalmistaja";
    }
}