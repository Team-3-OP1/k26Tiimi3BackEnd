package backend.team3.pro.Controller.API;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import backend.team3.pro.Model.Vaate;
import backend.team3.pro.Repository.VaateRepository;

@RestController
public class VaateRestController {

    private final VaateRepository vaateRepository;

    public VaateRestController(VaateRepository vaateRepository) {
        this.vaateRepository = vaateRepository;
    }

    @GetMapping("/api/tuotteet")
    public @ResponseBody List<Vaate> tuoteListRest() {
        return (List<Vaate>) vaateRepository.findAll();
    }

    @GetMapping("/api/tuote/{id}")
    public @ResponseBody Vaate findTuoteRest(@PathVariable("id") Long id) {
        return vaateRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/vaatteet")
    public @ResponseBody List<Vaate> vaateListRest() {
        return (List<Vaate>) vaateRepository.findAllByType("Vaate");
    }

}
