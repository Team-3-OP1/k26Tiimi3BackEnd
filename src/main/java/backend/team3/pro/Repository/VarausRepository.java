package backend.team3.pro.Repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import backend.team3.pro.Model.Varaus;
import org.springframework.transaction.annotation.Transactional;

public interface VarausRepository extends CrudRepository<Varaus, Long> {
    List<Varaus> findByAsiakasId(Long asiakasId);
    
    @Transactional
    void deleteByAsiakasId(Long asiakasId);
}