package net.impactotecnologico.reactivos;


import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import net.impactotecnologico.reactivos.models.Profile;
import net.impactotecnologico.reactivos.repositories.ProfileRepository;
import net.impactotecnologico.reactivos.services.ProfileService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@DataMongoTest 
@Import(ProfileService.class) 
public class ReactivosApplicationTests {

    private final ProfileService service;
    private final ProfileRepository repository;

    public ReactivosApplicationTests(@Autowired ProfileService service, 
                              @Autowired ProfileRepository repository) {
        this.service = service;
        this.repository = repository;
    }
    
    @Test
    public void save() {
        Mono<Profile> profileMono = this.service.create("email@email.com");
        StepVerifier
            .create(profileMono)
            .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
            .verifyComplete();
    }
    

    @Test
    public void update() throws Exception {
        Mono<Profile> saved = this.service
            .create("test")
            .flatMap(p -> this.service.update(p.getId(), "test1"));
        StepVerifier
            .create(saved)
            .expectNextMatches(p -> p.getEmail().equalsIgnoreCase("test1"))
            .verifyComplete();
    }

    @Test
    public void getById() {
        String test = UUID.randomUUID().toString();
        Mono<Profile> deleted = this.service
            .create(test)
            .flatMap(saved -> this.service.get(saved.getId()));
        StepVerifier
            .create(deleted)
            .expectNextMatches(profile -> StringUtils.hasText(profile.getId()) && test.equalsIgnoreCase(profile.getEmail()))
            .verifyComplete();
    }
    
    @Test
    public void delete() {
        String test = "test";
        Mono<Profile> deleted = this.service
            .create(test)
            .flatMap(saved -> this.service.delete(saved.getId()));
        StepVerifier
            .create(deleted)
            .expectNextMatches(profile -> profile.getEmail().equalsIgnoreCase(test))
            .verifyComplete();
    }

}