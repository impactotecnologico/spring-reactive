package net.impactotecnologico.reactivos.gateway;

import net.impactotecnologico.reactivos.command.Pago;
import reactor.core.publisher.Mono;

public interface PagosGateway {

    Mono<Void> procesarPago(Pago crearPago);

}
