package backend.team3.pro.Controller;

import backend.team3.pro.Model.Asiakas;
import backend.team3.pro.Repository.AsiakasRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AsiakasController {

    private final AsiakasRepository asiakasRepository;

    public AsiakasController(AsiakasRepository asiakasRepository) {
        this.asiakasRepository = asiakasRepository;
    }

    @GetMapping("/addasiakas")
    public String addAsiakas(Model model) {
        model.addAttribute("asiakas", new Asiakas());
        return "addasiakas";
    }

    @PostMapping("/saveasiakas")
    public String saveAsiakas(@Valid Asiakas asiakas, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (asiakas.getId() == null) {
                return "addasiakas";
            } else {
                return "editasiakas";
            }
        }

        asiakasRepository.save(asiakas);
        return "redirect:/homepage";
    }

    @GetMapping("/editasiakas/{id}")
    public String editAsiakas(@PathVariable("id") Long id, Model model) {
        Asiakas asiakas = asiakasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asiakasta ei löydy annetulla id:llä"));
        
        model.addAttribute("asiakas", asiakas);
        return "editasiakas";
    }

    @GetMapping("/deleteasiakas/{id}")
    public String deleteAsiakas(@PathVariable("id") Long id) {
        if (!asiakasRepository.existsById(id)) {
            throw new IllegalArgumentException("Asiakasta ei löydy annetulla id:llä");
        }

        asiakasRepository.deleteById(id);
        return "redirect:/homepage";
    }

    @GetMapping("/asiakkaat")
    public String naytaAsiakkaat(Model model) {
        model.addAttribute("asiakkaat", asiakasRepository.findAll());
        return "asiakkaat";
    }
}