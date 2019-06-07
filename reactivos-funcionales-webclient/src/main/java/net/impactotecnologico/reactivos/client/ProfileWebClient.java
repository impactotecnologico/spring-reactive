package net.impactotecnologico.reactivos.client;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import net.impactotecnologico.reactivos.models.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProfileWebClient {
    private WebClient client = WebClient.create("http://localhost:8088");
    private Mono<ClientResponse> result = client.get()
            .uri("/profiles")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange();
    
    private Mono<Profile> singleProfile = client.get()
            .uri("/profiles/5cf56cacf0c1d851704b0905")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .flatMap(res -> res.bodyToMono(Profile.class));
    

    public List<Profile> getResult() {
            Flux<Profile> userList = result.flatMapMany(res -> res.bodyToFlux(Profile.class));
            return userList.collectList().block();
    }
    
    
    public Profile getSingleResult() {
        Mono<Profile> userList = singleProfile.single();
        return userList.block();
}
    
    
}