package net.impactotecnologico.reactivos.endpoints;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.log4j.Log4j2;
import net.impactotecnologico.reactivos.handlers.ProfileHandler;

@Log4j2
@Configuration
class ProfileEndpointConfiguration {

//    @Bean
//    RouterFunction<ServerResponse> routes(ProfileHandler handler) { 
//        return route(GET("/profiles"), handler::all) 
//            .andRoute(GET("/profiles/{id}"), handler::getById)
//            .andRoute(DELETE("/profiles/{id}"), handler::deleteById) 
//            .andRoute(POST("/profiles"), handler::create)
//            .andRoute(PUT("/profiles/{id}"), handler::updateById);
//    }
    
    @Autowired
    ProfileHandler handler;
    
    
    @Bean
	public RouterFunction<ServerResponse> routerFunctionB() {
    	RouterFunction<ServerResponse> route = route()
        		.path("/profiles", builder1 -> builder1
        			.nest(accept(MediaType.APPLICATION_JSON), builder2 -> builder2 // (0)
        				.GET("/{id}", handler::getById)
        				.GET("", handler::all)
        				)
        			.POST("/person", handler::create))
        		.after((request, response) -> logResponse(response)) // (2)
        		.build();
    	
    	
    	return route;
	}
    
    
    private ServerResponse logResponse(ServerResponse response) {
    	log.debug(response.toString());
    	return response;
    }

}