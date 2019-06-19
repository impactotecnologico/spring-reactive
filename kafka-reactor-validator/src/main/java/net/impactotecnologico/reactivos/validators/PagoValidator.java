package net.impactotecnologico.reactivos.validators;

import net.impactotecnologico.reactivos.event.PagosEvent;

public interface PagoValidator {

    void calculaPago(PagosEvent pago);

}