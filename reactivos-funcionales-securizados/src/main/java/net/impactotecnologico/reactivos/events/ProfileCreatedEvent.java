package net.impactotecnologico.reactivos.events;
import org.springframework.context.ApplicationEvent;

import net.impactotecnologico.reactivos.models.Profile;

public class ProfileCreatedEvent extends ApplicationEvent {

    public ProfileCreatedEvent(Profile source) {
        super(source);
    }
}