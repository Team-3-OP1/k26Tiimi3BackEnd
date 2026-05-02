package backend.team3.pro.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.team3.pro.Model.Tyyppi;

@Repository
public interface TyyppiRepository extends CrudRepository<Tyyppi, Long> {
    boolean existsByNimi(String nimi);
    Tyyppi findByNimi(String nimi);
}
