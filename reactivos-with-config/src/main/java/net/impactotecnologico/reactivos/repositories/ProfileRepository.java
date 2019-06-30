package net.impactotecnologico.reactivos.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import net.impactotecnologico.reactivos.models.Profile;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}
