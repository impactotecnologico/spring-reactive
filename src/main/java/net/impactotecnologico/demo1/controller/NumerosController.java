package net.impactotecnologico.demo1.controller;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.impactotecnologico.demo1.subscriber.Suscriptor;
import reactor.core.publisher.Flux;

@RestController
public class NumerosController {

    @GetMapping(path = "/numeros", produces = "text/event-stream")
    public Flux<Integer> all () {
        Flux<Integer> flux = Flux.range(1,30)
                .delayElements(Duration.ofSeconds(1));

        flux.subscribe(System.out::println); // suscriptor 1
        flux.subscribe(Suscriptor::multiplicar); // suscriptor 2
        return flux; // retornamos el elemento. Sería como el suscriptor 3
    }
}