package net.impactotecnologico.reactivos.events.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.impactotecnologico.reactivos.events.ProfileCreatedEvent;

@Component
public class ProfileEventListener implements ApplicationListener<ProfileCreatedEvent> {

	@Override
	public void onApplicationEvent(ProfileCreatedEvent event) {
		System.out.println("Fuente original" + event.getSource());
		
	}

}
