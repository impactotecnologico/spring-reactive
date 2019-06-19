package net.impactotecnologico.reactivos.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.impactotecnologico.reactivos.command.Pago;
import net.impactotecnologico.reactivos.gateway.PagosGateway;
import reactor.core.publisher.Mono;

@RestController
public class PagosController {

    private final PagosGateway paymentGateway;

    public PagosController(PagosGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    /**
     * The Mono returned by the call will be sent to Spring Webflux, which relies on an multi-reactor event-loop and NIO
     * to handle requests in a non-blocking manner, enabling far more concurrent requests. The result will be sent over
     * HTTP through a mechanism called Server Sent Events
     **/
    @PostMapping(value = "/payment")
    public Mono<Void> procesarPago(@RequestBody Pago payment) {
        /**
         When calling the doPayment method, we send our payment information, getting a Mono<Void> in return.
         This event will resolve when our payment is sent succesfully to the Kafka topic
         **/
        return paymentGateway.procesarPago(payment);
    }
}
