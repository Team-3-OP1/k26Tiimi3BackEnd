package backend.team3.pro.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vaate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String size;
    private double price;

    @ManyToOne
    @JoinColumn(name = "valmistaja_id")
    private Valmistaja valmistaja;

    public Vaate() {
    }

    public Vaate(String name, String size, double price, Valmistaja valmistaja) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.valmistaja = valmistaja;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Valmistaja getValmistaja() {
        return valmistaja;
    }

    public void setValmistaja(Valmistaja valmistaja) {
        this.valmistaja = valmistaja;
    }
}