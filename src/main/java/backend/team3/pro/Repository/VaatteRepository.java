package backend.team3.pro.Repository;
import org.springframework.data.repository.CrudRepository;
import backend.team3.pro.Model.Vaatte;
import org.springframework.stereotype.Repository;

@Repository
public interface VaatteRepository extends CrudRepository<Vaatte, Long> {
    // CRUD-metodit (Create, Read, Update, Delete) periytyvät CrudRepositoryltä
}

