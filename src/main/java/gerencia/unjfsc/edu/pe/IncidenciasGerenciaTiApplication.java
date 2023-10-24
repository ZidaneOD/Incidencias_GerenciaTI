package gerencia.unjfsc.edu.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class IncidenciasGerenciaTiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidenciasGerenciaTiApplication.class, args);
	}

}
