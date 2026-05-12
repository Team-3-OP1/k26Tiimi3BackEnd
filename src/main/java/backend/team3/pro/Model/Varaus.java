package backend.team3.pro.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Varaus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asiakas_id", nullable = false)
    private Asiakas asiakas;

    @ManyToOne
    @JoinColumn(name = "vaate_id", nullable = false)
    private Vaate vaate;

    private LocalDateTime varausAika;

    private String tila;

    public Varaus() {
        this.varausAika = LocalDateTime.now();
        this.tila = "VARATTU";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asiakas getAsiakas() {
        return asiakas;
    }

    public void setAsiakas(Asiakas asiakas) {
        this.asiakas = asiakas;
    }

    public Vaate getVaate() {
        return vaate;
    }

    public void setVaate(Vaate vaate) {
        this.vaate = vaate;
    }

    public LocalDateTime getVarausAika() {
        return varausAika;
    }

    public void setVarausAika(LocalDateTime varausAika) {
        this.varausAika = varausAika;
    }

    public String getTila() {
        return tila;
    }

    public void setTila(String tila) {
        this.tila = tila;
    }
}