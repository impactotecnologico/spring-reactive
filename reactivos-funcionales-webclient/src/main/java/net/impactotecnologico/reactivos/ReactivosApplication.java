package net.impactotecnologico.reactivos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.impactotecnologico.reactivos.client.ProfileWebClient;

@SpringBootApplication
public class ReactivosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactivosApplication.class, args);
		
		ProfileWebClient uwc = new ProfileWebClient();
		System.out.println(uwc.getResult());
		
		System.out.println(uwc.getSingleResult());

		
	}

}
