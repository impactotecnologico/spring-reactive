package net.impactotecnologico.reactivos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactivosApplicationServerSend {

	public static void main(String[] args) {
		SpringApplication.run(ReactivosApplicationServerSend.class, args);

		WebClient client = WebClient.create("http://localhost:9092");
		
		
		ParameterizedTypeReference<ServerSentEvent<String>> type 
		= new ParameterizedTypeReference<ServerSentEvent<String>>() {
		};

		Flux<ServerSentEvent<String>> eventStream = 
				client.get().uri("/sending").retrieve().bodyToFlux(type);

		eventStream.subscribe(
		content -> {
			System.out.print(content.event() + ":");
			System.out.print(content.id() + ":");
			System.out.println(content.data());
		}, error -> System.err.println("Error receiving SSE: " + error), 
		() -> {
			System.out.println("Completed!!!");
		});
	}

}
