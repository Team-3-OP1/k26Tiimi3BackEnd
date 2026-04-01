package backend.team3.pro.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.team3.pro.Model.Valmistaja;

@Repository
public interface ValmistajaRepository extends CrudRepository<Valmistaja, Long> {

	boolean existsByName(String name);

}
