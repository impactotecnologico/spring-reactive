package net.impactotecnologico.stream_reactive;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import net.impactotecnologico.stream_reactive.NeverEndingStockStream.Handler;

/**
 * Publisher of stock prices from a never ending stream.
 * <p>
 * It will share a single underlying stream with as many subscribers as it receives.
 * <p>
 * If the subscriber can not keep up, it will drop (different strategies could be implemented, configurable, etc).
 */
public class StockPricePublisher implements Publisher<Stock> {

    
	@Override
	public void subscribe(Subscriber<? super Stock> s) {
        s.onSubscribe(new Subscription() {

            AtomicLong capacity = new AtomicLong();
            EventHandler handler = new EventHandler(s, capacity);

            @Override
            public void request(long n) {
                if (capacity.getAndAdd(n) == 0) {
                    // was at 0, so start up consumption again
                    startConsuming();
                }
            }

            @Override
            public void cancel() {
                System.out.println("StockPricePublisher => Cancel Subscription");
                NeverEndingStockStream.removeHandler(handler);
            }

            public void startConsuming() {
                NeverEndingStockStream.addHandler(handler);
            }

        });

    }

    private static final class EventHandler implements Handler {
        private final Subscriber<? super Stock> s;
        private final AtomicLong capacity;

        private EventHandler(Subscriber<? super Stock> s2, AtomicLong capacity) {
            this.s = s2;
            this.capacity = capacity;
        }

        @Override
        public void handle(Stock event) {
            long c = capacity.get();
            if (c == 0) {
                // shortcut instead of doing decrement/increment loops while no capacity
                return;
            }
            if (capacity.getAndDecrement() > 0) {
                s.onNext(event);
            } else {
                // we just decremented below 0 so increment back one
                capacity.incrementAndGet();
            }
        }
    }

	

}
