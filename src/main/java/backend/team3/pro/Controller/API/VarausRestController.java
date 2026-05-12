package backend.team3.pro.Controller.API;

import backend.team3.pro.Model.Asiakas;
import backend.team3.pro.Model.Vaate;
import backend.team3.pro.Model.Varaus;
import backend.team3.pro.Repository.AsiakasRepository;
import backend.team3.pro.Repository.VaateRepository;
import backend.team3.pro.Repository.VarausRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/varaukset")
public class VarausRestController {

    private final VarausRepository varausRepository;
    private final AsiakasRepository asiakasRepository;
    private final VaateRepository vaateRepository;

    public VarausRestController(VarausRepository varausRepository, AsiakasRepository asiakasRepository, VaateRepository vaateRepository) {
        this.varausRepository = varausRepository;
        this.asiakasRepository = asiakasRepository;
        this.vaateRepository = vaateRepository;
    }

    @PostMapping
    public ResponseEntity<String> teeVaraus(@RequestParam Long asiakasId, @RequestParam Long vaateId) {
        Asiakas asiakas = asiakasRepository.findById(asiakasId)
                .orElseThrow(() -> new IllegalArgumentException("Asiakasta ei löydy"));
        
        Vaate vaate = vaateRepository.findById(vaateId)
                .orElseThrow(() -> new IllegalArgumentException("Vaatetta ei löydy"));

        Varaus varaus = new Varaus();
        varaus.setAsiakas(asiakas);
        varaus.setVaate(vaate);
        
        varausRepository.save(varaus);
        
        return ResponseEntity.ok("Varaus luotu onnistuneesti");
    }

    @GetMapping("/asiakas/{asiakasId}")
    public List<Varaus> haeAsiakkaanVaraukset(@PathVariable Long asiakasId) {
        return varausRepository.findByAsiakasId(asiakasId);
    }
}