package backend.team3.pro.Repository;

import org.springframework.data.repository.CrudRepository;
import backend.team3.pro.Model.Asiakas;
import java.util.Optional;

public interface AsiakasRepository extends CrudRepository<Asiakas, Long> {
    Optional<Asiakas> findByUsername(String username);
}