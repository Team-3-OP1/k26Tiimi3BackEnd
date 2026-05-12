package backend.team3.pro.Repository;

import backend.team3.pro.Model.Varaus;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface VarausRepository extends CrudRepository<Varaus, Long> {
    List<Varaus> findByAsiakasId(Long asiakasId);
}