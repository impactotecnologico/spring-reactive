package net.impactotecnologico.reactivos.websockethtml;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class EchoHandler implements WebSocketHandler {
	@Override
	public Mono<Void> handle(WebSocketSession session) {
		return session
				.send(session.receive().map(msg -> 
				"Recibido :: " + msg.getPayloadAsText(
				)).map(session::textMessage));
	}
}