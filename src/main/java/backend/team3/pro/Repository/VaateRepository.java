package backend.team3.pro.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.team3.pro.Model.Vaate;

@Repository
public interface VaateRepository extends CrudRepository<Vaate, Long> {
    // CRUD-metodit (Create, Read, Update, Delete) periytyvät CrudRepositoryltä
}
