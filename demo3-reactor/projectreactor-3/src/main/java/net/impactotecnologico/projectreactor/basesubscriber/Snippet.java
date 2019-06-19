package net.impactotecnologico.projectreactor.basesubscriber;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class Snippet {
	public static void main(String[] args) {
		Flux.range(1, 10).doOnRequest(r -> System.out.println("request of " + r))
				.subscribe(new BaseSubscriber<Integer>() {

					@Override
					public void hookOnSubscribe(Subscription subscription) {
						request(50);
					}

					@Override
					public void hookOnNext(Integer integer) {
						
						// alguna lógica de validación aquí
						System.out.println("Cancelando después de recibir " + integer);
						
//						if (condition) {
//							cancel();
//						}
//						
//						request(1);
					}
				});
	}
}
