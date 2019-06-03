package net.impactotecnologico.projectreactor.backpressure;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;

public class ToCollection {

	public static void main(String[] args) {
		
		
		List<Integer> elements = new ArrayList<Integer>();
		Flux.just(1, 2, 3, 4)
		  .log()
		  .map(i -> i * 2)
		  .subscribe(elements ::add);

	}

}
