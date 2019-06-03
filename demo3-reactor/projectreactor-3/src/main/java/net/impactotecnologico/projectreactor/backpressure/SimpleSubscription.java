package net.impactotecnologico.projectreactor.backpressure;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;

public class SimpleSubscription {

	public static void main(String[] args) {
		Flux.just(1, 2, 3, 4)
		  .log()
		  .subscribe(new Subscriber<Integer>() {

			@Override
			public void onSubscribe(Subscription s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNext(Integer t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
