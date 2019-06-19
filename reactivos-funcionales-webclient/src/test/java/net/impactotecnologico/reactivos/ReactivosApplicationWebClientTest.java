package net.impactotecnologico.reactivos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import net.impactotecnologico.reactivos.models.Profile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactivosApplicationWebClientTest {
	
	@Autowired 
	private WebTestClient webTestClient; 
	
	@Test
	public void getTest() {
		
		webTestClient.get().uri("/profiles")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().is4xxClientError()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Profile.class)
			.consumeWith(response -> 
				Assertions.assertFalse(response.getResponseBody().isEmpty()));
		
	}
	


}
