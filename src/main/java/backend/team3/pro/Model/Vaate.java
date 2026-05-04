package backend.team3.pro.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Vaate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nimi on pakollinen")
    private String name;

    @ManyToOne
    @JoinColumn(name = "tyyppi_id")
    private Tyyppi tyyppi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Koko koko; // null jos tuote on lelu

    @DecimalMin(value = "0.01", message = "Hinnan pitää olla suurempi kuin 0")
    private double price;

    @Min(value = 0, message = "Varastomäärä ei voi olla negatiivinen")
    private int varastoMaara;

    @ManyToOne
    @JoinColumn(name = "valmistaja_id")
    private Valmistaja valmistaja;

    public Vaate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tyyppi getTyyppi() {
        return tyyppi;
    }

    public void setTyyppi(Tyyppi tyyppi) {
        this.tyyppi = tyyppi;
    }

    public Koko getKoko() {
        return koko;
    }

    public void setKoko(Koko koko) {
        this.koko = koko;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVarastoMaara() {
        return varastoMaara;
    }

    public void setVarastoMaara(int varastoMaara) {
        this.varastoMaara = varastoMaara;
    }

    public Valmistaja getValmistaja() {
        return valmistaja;
    }

    public void setValmistaja(Valmistaja valmistaja) {
        this.valmistaja = valmistaja;
    }
}