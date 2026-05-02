package backend.team3.pro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend.team3.pro.Model.Koko;
import backend.team3.pro.Model.Tyyppi;
import backend.team3.pro.Model.Vaate;
import backend.team3.pro.Repository.TyyppiRepository;
import backend.team3.pro.Repository.VaateRepository;
import backend.team3.pro.Repository.ValmistajaRepository;
import jakarta.validation.Valid;

@Controller
public class VaateController {

    private final VaateRepository vaateRepository;
    private final ValmistajaRepository valmistajaRepository;
    private final TyyppiRepository tyyppiRepository;

    public VaateController(VaateRepository vaateRepository,
                           ValmistajaRepository valmistajaRepository,
                           TyyppiRepository tyyppiRepository) {
        this.vaateRepository = vaateRepository;
        this.valmistajaRepository = valmistajaRepository;
        this.tyyppiRepository = tyyppiRepository;
    }

    private void lisaaLomakeAtribuutit(Model model) {
        model.addAttribute("valmistajat", valmistajaRepository.findAll());
        model.addAttribute("tyypit", tyyppiRepository.findAll());
        model.addAttribute("kokovalinnat", Koko.values());
    }

    @GetMapping("/addvaate")
    public String naytaLomake(Model model) {
        model.addAttribute("vaate", new Vaate());
        lisaaLomakeAtribuutit(model);
        return "addvaate";
    }

    @PostMapping("/tallenna")
    public String tallenna(@Valid Vaate vaate,
                           BindingResult bindingResult,
                           @RequestParam(value = "tyyppiId", required = false) Long tyyppiId,
                           @RequestParam(value = "valmistaja.id", required = false) Long valmistajaid,
                           Model model) {

        boolean virhe = false;

        // Aseta tyyppi
        if (tyyppiId != null) {
            tyyppiRepository.findById(tyyppiId).ifPresent(vaate::setTyyppi);
        } else {
            model.addAttribute("tyyppiVirhe", "Tyyppi on pakollinen");
            virhe = true;
        }

        // Aseta valmistaja
        if (valmistajaid != null) {
            valmistajaRepository.findById(valmistajaid).ifPresent(vaate::setValmistaja);
        } else {
            model.addAttribute("valmistajaVirhe", "Valmistaja on pakollinen");
            virhe = true;
        }

        if (bindingResult.hasErrors() || virhe) {
            lisaaLomakeAtribuutit(model);
            return "addvaate";
        }

        // Jos tyyppi on vaate, koko on pakollinen
        Tyyppi tyyppi = vaate.getTyyppi();
        if (tyyppi != null && "vaate".equalsIgnoreCase(tyyppi.getNimi()) && vaate.getKoko() == null) {
            model.addAttribute("kokoVirhe", "Koko on pakollinen vaatteille (S, M tai L)");
            lisaaLomakeAtribuutit(model);
            return "addvaate";
        }

        // Jos lelu, poistetaan koko
        if (tyyppi != null && "lelu".equalsIgnoreCase(tyyppi.getNimi()))
            vaate.setKoko(null);

        vaateRepository.save(vaate);
        return "redirect:/homepage";
    }

    @GetMapping("/delete/{id}")
    public String deleteVaate(@PathVariable("id") Long id) {
        if (!vaateRepository.existsById(id))
            throw new IllegalArgumentException("Vaate ei loydy annetulla id:lla");
        vaateRepository.deleteById(id);
        return "redirect:/homepage";
    }

    @GetMapping("/edit/{id}")
    public String editVaate(@PathVariable("id") Long id, Model model) {
        Vaate vaate = vaateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaate ei loydy annetulla id:lla"));
        model.addAttribute("vaate", vaate);
        lisaaLomakeAtribuutit(model);
        return "editvaate";
    }
}
