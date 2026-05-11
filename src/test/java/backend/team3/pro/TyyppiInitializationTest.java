package backend.team3.pro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import backend.team3.pro.Repository.TyyppiRepository;

@SpringBootTest
class TyyppiInitializationTest {

    @Autowired
    private TyyppiRepository tyyppiRepository;

    @Test
    void defaultTypesAreAvailable() {
        assertTrue(tyyppiRepository.existsByNimi("vaate"));
        assertTrue(tyyppiRepository.existsByNimi("ruoka"));
        assertTrue(tyyppiRepository.existsByNimi("lelu"));
    }
}
