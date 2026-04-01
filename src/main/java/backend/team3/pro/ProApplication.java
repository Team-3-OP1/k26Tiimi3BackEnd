package backend.team3.pro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.team3.pro.Model.Valmistaja;
import backend.team3.pro.Repository.ValmistajaRepository;

@SpringBootApplication
public class ProApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProApplication.class, args);
	}

	@Bean
	CommandLineRunner initValmistajat(ValmistajaRepository valmistajaRepository) {
		return args -> {
			if (!valmistajaRepository.existsByName("Rukka")) {
				valmistajaRepository.save(new Valmistaja("Rukka"));
			}
			if (!valmistajaRepository.existsByName("Luhta")) {
				valmistajaRepository.save(new Valmistaja("Luhta"));
			}
		};
	}

}
