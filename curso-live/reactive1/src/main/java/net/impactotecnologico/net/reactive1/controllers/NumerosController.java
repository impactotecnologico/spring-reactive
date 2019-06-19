package net.impactotecnologico.net.reactive1.controllers;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.impactotecnologico.net.reactive1.suscriptores.Suscriptor;
import reactor.core.publisher.Flux;

@RestController
public class NumerosController {

	@GetMapping(path="/numeros",produces = "text/event-stream")
	public Flux<Integer> all() {
		Flux<Integer> flux = Flux.range(1, 30).delayElements(Duration.ofSeconds(1));
		
		flux.subscribe(Suscriptor::multiplicar);
		
		flux.subscribe(System.out::println);
		
		
		return flux;
	}
}
