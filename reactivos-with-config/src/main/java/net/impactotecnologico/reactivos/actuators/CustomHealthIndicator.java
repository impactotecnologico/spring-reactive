package net.impactotecnologico.reactivos.actuators;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up()
                .withDetail("app", "Estoy operativo!!")
                .withDetail("error", "Error");
    }
}