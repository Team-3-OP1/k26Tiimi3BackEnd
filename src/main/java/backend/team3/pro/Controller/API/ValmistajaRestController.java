package backend.team3.pro.Controller.API;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import backend.team3.pro.Model.Valmistaja;
import backend.team3.pro.Repository.ValmistajaRepository;

@CrossOrigin(origins = "https://frontendtiimi3-opt3frontend.2.rahtiapp.fi/") // Salli CORS-pyynnöt localhost:5173:sta
@RestController
public class ValmistajaRestController {

    private final ValmistajaRepository valmistajaRepository;

    public ValmistajaRestController(ValmistajaRepository valmistajaRepository) {
        this.valmistajaRepository = valmistajaRepository;
    }

    @GetMapping("/api/valmistajat")
    public @ResponseBody List<Valmistaja> valmistajaListRest() {
        return (List<Valmistaja>) valmistajaRepository.findAll();
    }
}
