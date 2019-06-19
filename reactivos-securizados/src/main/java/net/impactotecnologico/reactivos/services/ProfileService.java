package net.impactotecnologico.reactivos.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import net.impactotecnologico.reactivos.models.Profile;
import net.impactotecnologico.reactivos.models.security.Role;
import net.impactotecnologico.reactivos.repositories.ProfileRepository;
import net.impactotecnologico.reactivos.security.PBKDF2Encoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository; 
    
	@Autowired
    private PBKDF2Encoder encoder;


    public Flux<Profile> all() { 
    	log.info("obteniendo todos");
        return this.profileRepository.findAll();
    }

    public Mono<Profile> get(String username) { 
        return this.profileRepository.findOneByUsername(username);
    }
    
    public Mono<Profile> create(String username, String password, Role role) { 
    	
    	Profile p = new Profile(null, username, 
    			encoder.encode(password), Boolean.TRUE, Arrays.asList(role));
        return this.profileRepository.save(p);
    }

    public Mono<Profile> delete(String username) { 
        return this.profileRepository
            .findOneByUsername(username)
            .flatMap(p -> this.profileRepository.deleteById(p.getId()).thenReturn(p));
    }
    
    public Mono<Void> deleteAll() { 
        return this.profileRepository.deleteAll();
    }

}