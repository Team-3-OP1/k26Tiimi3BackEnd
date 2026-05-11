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
            @RequestParam(value = "valmistaja.id", required = false) Long valmistajaId,
            Model model) {

        boolean virhe = false;

        if (tyyppiId != null) {
            tyyppiRepository.findById(tyyppiId).ifPresent(vaate::setTyyppi);
        } else {
            model.addAttribute("tyyppiVirhe", "Tyyppi on pakollinen");
            virhe = true;
        }

        if (valmistajaId != null) {
            valmistajaRepository.findById(valmistajaId).ifPresent(vaate::setValmistaja);
        } else {
            model.addAttribute("valmistajaVirhe", "Valmistaja on pakollinen");
            virhe = true;
        }

        if (bindingResult.hasErrors() || virhe) {
            lisaaLomakeAtribuutit(model);

            if (vaate.getId() == null) {
                return "addvaate";
            } else {
                return "editvaate";
            }
        }

        Tyyppi tyyppi = vaate.getTyyppi();

        if (tyyppi != null && "vaate".equalsIgnoreCase(tyyppi.getNimi()) && vaate.getKoko() == null) {
            model.addAttribute("kokoVirhe", "Koko on pakollinen vaatteille (S, M tai L)");
            lisaaLomakeAtribuutit(model);

            if (vaate.getId() == null) {
                return "addvaate";
            } else {
                return "editvaate";
            }
        }

        if (tyyppi != null && !"vaate".equalsIgnoreCase(tyyppi.getNimi())) {
            vaate.setKoko(null);
        }

        vaateRepository.save(vaate);
        return "redirect:/homepage";
    }

    @PostMapping("/toimita/{id}")
    public String toimitaTuote(@PathVariable("id") Long id) {
        Vaate vaate = vaateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tuotetta ei löydy annetulla id:llä"));

        if (vaate.getVarastoMaara() > 0) {
            vaate.setVarastoMaara(vaate.getVarastoMaara() - 1);
            vaateRepository.save(vaate);
        }

        return "redirect:/varasto";
    }

    @GetMapping("/saatavuus")
    public String naytaSaatavuus(Model model) {
        model.addAttribute("tuotteet", vaateRepository.findAll());
        return "saatavuus";
    }

    @GetMapping("/varasto")
    public String naytaVarasto(Model model) {
        model.addAttribute("tuotteet", vaateRepository.findAll());
        return "varasto";
    }

    @GetMapping("/paivitavarasto")
    public String naytaVarastonPaivitys(Model model) {
        model.addAttribute("tuotteet", vaateRepository.findAll());
        return "paivitavarasto";
    }

    @PostMapping("/paivitavarasto/{id}")
    public String paivitaVarasto(@PathVariable("id") Long id,
            @RequestParam("varastoMaara") int varastoMaara) {
        Vaate vaate = vaateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tuotetta ei löydy annetulla id:llä"));

        if (varastoMaara >= 0) {
            vaate.setVarastoMaara(varastoMaara);
            vaateRepository.save(vaate);
        }

        return "redirect:/paivitavarasto";
    }

    @GetMapping("/delete/{id}")
    public String deleteVaate(@PathVariable("id") Long id) {
        if (!vaateRepository.existsById(id)) {
            throw new IllegalArgumentException("Vaate ei löydy annetulla id:llä");
        }

        vaateRepository.deleteById(id);
        return "redirect:/homepage";
    }

    @GetMapping("/edit/{id}")
    public String editVaate(@PathVariable("id") Long id, Model model) {
        Vaate vaate = vaateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaate ei löydy annetulla id:llä"));

        model.addAttribute("vaate", vaate);
        lisaaLomakeAtribuutit(model);
        return "editvaate";
    }
}
