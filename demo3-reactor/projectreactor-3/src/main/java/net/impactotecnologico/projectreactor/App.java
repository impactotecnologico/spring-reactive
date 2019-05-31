package net.impactotecnologico.projectreactor;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

		List<String> iterable = Arrays.asList("foo", "bar", "foobar");
		Flux<String> seq2 = Flux.fromIterable(iterable);

		Mono<String> noData = Mono.empty();

		Mono<String> data = Mono.just("foo");

		Flux<Integer> numeros = Flux.range(5, 3);
		numeros.subscribe(i -> System.out.println(i));

		Flux<Integer> ints = Flux.range(1, 4).map(i -> {
			if (i <= 3)
				return i;
			throw new RuntimeException("Falla en el 4");
		});
		ints.subscribe(i -> System.out.println(i), error -> System.err.println("Error: " + error));

		
		System.out.println("........................");
		
		Flux<Integer> ints2 = Flux.range(1, 4); 
		ints2.subscribe(i -> System.out.println(i),
		    error -> System.err.println("Error " + error),
		    () -> System.out.println("Done")); 
		
		System.out.println("........................");
		
		
		Flux<Integer> ints3 = Flux.range(1, 40);
		ints3.subscribe(i -> System.out.println(i),
		    error -> System.err.println("Error " + error),
		    () -> System.out.println("Done"),
		    sub -> sub.request(10));
		
	}
}
