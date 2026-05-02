package backend.team3.pro.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.team3.pro.Model.Vaate;

@Repository
public interface VaateRepository extends CrudRepository<Vaate, Long> {

    List<Vaate> findAllByValmistaja_Id(Long valmistaja_id);

    List<Vaate> findAllByTyyppi_Nimi(String tyyppiNimi);
}
