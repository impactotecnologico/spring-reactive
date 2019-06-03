package net.impactotecnologico.projectreactor.basesubscriber.sample;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<T> extends BaseSubscriber<T> {

	public void hookOnSubscribe(Subscription subscription) {
		System.out.println("Estoy suscrito");
		request(1);
	}

	public void hookOnNext(T value) {
		System.out.println(value + "*");
		request(1);
	}
}