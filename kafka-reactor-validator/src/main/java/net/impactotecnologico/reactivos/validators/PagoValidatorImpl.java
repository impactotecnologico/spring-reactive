package net.impactotecnologico.reactivos.validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.impactotecnologico.reactivos.event.PagosEvent;

@Component
public class PagoValidatorImpl implements PagoValidator {

    private Logger LOGGER = LoggerFactory.getLogger(PagoValidatorImpl.class);

    @Override
    public void calculaPago(PagosEvent paymentEvent) {
        LOGGER.info("Procesando " + paymentEvent.getCreditCardNumber() + " " + paymentEvent.getAmount());
    }
}