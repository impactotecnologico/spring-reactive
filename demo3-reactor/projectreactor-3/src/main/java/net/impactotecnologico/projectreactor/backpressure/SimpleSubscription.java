package net.impactotecnologico.projectreactor.backpressure;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;

public class SimpleSubscription {

	public static void main(String[] args) {
		Flux.just(1, 2, 3, 4, 5, 6)
		  .log()
		  .subscribe(new Subscriber<Integer>() {
			  
			  private Subscription s;
			  int onNextAmount;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(2);
				
			}

			@Override
			public void onNext(Integer t) {
				onNextAmount++;
				
				if (onNextAmount % 2 == 0) {
					s.request(2);
				}
				
				System.out.println(t);
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onComplete() {
				System.out.println("FIN");
				
			}
		});

	}

}
