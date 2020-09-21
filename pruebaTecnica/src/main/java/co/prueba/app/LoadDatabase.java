package co.prueba.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import co.prueba.app.repository.ClienteRepository;

@Configuration
public class LoadDatabase {

	  @Bean
	  CommandLineRunner initDatabase(ClienteRepository repository) {

	    return args -> {
	    
	    };
	  }
}
