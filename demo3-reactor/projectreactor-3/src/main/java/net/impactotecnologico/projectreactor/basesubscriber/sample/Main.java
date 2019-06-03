package net.impactotecnologico.projectreactor.basesubscriber.sample;

import reactor.core.publisher.Flux;

public class Main {

	public static void main(String[] args) {
		SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
		
		Flux<Integer> ints = Flux.range(1, 40);
		
		ints.subscribe(i -> System.out.println(i),
		    error -> System.err.println("Error " + error),
		    () -> {System.out.println("Done");},
		    s -> s.request(10));
		ints.subscribe(ss);
	}
}
