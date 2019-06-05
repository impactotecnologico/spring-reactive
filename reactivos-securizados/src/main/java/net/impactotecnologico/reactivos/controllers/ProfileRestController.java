package net.impactotecnologico.reactivos.controllers;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.impactotecnologico.reactivos.models.Profile;
import net.impactotecnologico.reactivos.models.security.AuthRequest;
import net.impactotecnologico.reactivos.models.security.AuthResponse;
import net.impactotecnologico.reactivos.models.security.Role;
import net.impactotecnologico.reactivos.security.JWTUtil;
import net.impactotecnologico.reactivos.security.PBKDF2Encoder;
import net.impactotecnologico.reactivos.services.ProfileService;
import reactor.core.publisher.Mono;

@RestController 
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)  
public class ProfileRestController {

	private final MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
    
	@Autowired
	private ProfileService profileService;
    
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private PBKDF2Encoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    Publisher<Profile> getAll() {
        return this.profileService.all();
    }

	@PostMapping("/login")
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
		return profileService.get(ar.getUsername()).map((userDetails) -> {
			if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/new")
    Publisher<ResponseEntity<Profile>> createUser(@RequestBody Profile profile) {
        return this.profileService
            .create(profile.getUsername(), profile.getPassword(), Role.ROLE_USER)
            .map(p -> ResponseEntity.created(URI.create("/profiles/" + p.getUsername()))
                .contentType(mediaType)
                .build());
    }
	
	@PostMapping("/admin")
    Publisher<ResponseEntity<Profile>> createAdmin(@RequestBody Profile profile) {
        return this.profileService
            .create(profile.getUsername(), profile.getPassword(), Role.ROLE_ADMIN)
            .map(p -> ResponseEntity.created(URI.create("/profiles/" + p.getUsername()))
                .contentType(mediaType)
                .build());
    }
	
	@DeleteMapping("/{username}")
	@PreAuthorize("hasRole('ADMIN')")
    Publisher<Profile> deleteById(@PathVariable String username) {
        return this.profileService.delete(username);
    }
	
	
	@DeleteMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
    Publisher<Void> deleteAll() {
        return this.profileService.deleteAll();
    }
   
}