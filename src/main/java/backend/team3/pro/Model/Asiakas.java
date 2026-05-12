package backend.team3.pro.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity

public class Asiakas {


@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nimi on pakollinen")
    private String etunimi;
    private String sukunimi;
    private String sposti;
    private String username;

    

    public Asiakas() {
    }

    public Asiakas(String etunimi, String sukunimi, String sposti, String username) {
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.sposti = sposti;
        this.username = username;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getSposti() {
        return sposti;
    }

    public void setSposti(String sposti) {
        this.sposti = sposti;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    

}