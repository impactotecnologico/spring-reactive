package net.impactotecnologico.reactivos.serversend;
import java.time.Duration;
import java.time.LocalTime;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;


@RestController
public class ServerSendingController {

    @GetMapping(path = "/sending", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> all () {
    	return Flux.interval(Duration.ofSeconds(1))
        .map(sequence -> ServerSentEvent.<String> builder()
          .id(String.valueOf(sequence))
            .event("periodic-event")
            .data("SSE - " + LocalTime.now().toString())
            .build());
    }
}
