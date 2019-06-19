package net.impactotecnologico.projectreactor.hotstream;

import static java.time.Duration.ofSeconds;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class Snippet {
	public static void main(String[] args) throws InterruptedException {
		ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
			while (true) {
				fluxSink.next(System.currentTimeMillis());
			}
		}).sample(ofSeconds(2)).publish();

		publish.subscribe(System.out::println);
		
		
		
		
		
		publish.connect();
		
		System.out.println("------------");
		
		publish.subscribe(System.out::println);

		
		
//		
//
//		Flux<Integer> source = Flux.range(1, 3).doOnSubscribe(s -> System.out.println("subscribed to source"));
//
//		ConnectableFlux<Integer> co = source.publish();
//
//		co.subscribe(System.out::println, error -> {
//			System.out.println(error);
//		}, () -> {
//			System.out.println("done suscriptor 1");
//		});
//		co.subscribe(System.out::println, error -> {
//		}, () -> {
//			System.out.println("done suscriptor 2");
//		});
//
//		System.out.println("done subscribing");
//		Thread.sleep(500);
//		System.out.println("will now connect");
//
//		co.connect();
	}

}
