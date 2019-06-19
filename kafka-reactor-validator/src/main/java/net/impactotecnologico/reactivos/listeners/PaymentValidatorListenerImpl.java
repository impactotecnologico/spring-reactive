package net.impactotecnologico.reactivos.listeners;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.impactotecnologico.reactivos.event.PagosEvent;
import net.impactotecnologico.reactivos.validators.PagoValidator;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class PaymentValidatorListenerImpl {

    private static final Logger log = LoggerFactory.getLogger(PaymentValidatorListenerImpl.class.getName());

    private KafkaReceiver kafkaReceiver;

    private Random r = new Random();

    private ObjectMapper objectMapper = new ObjectMapper();

    private PagoValidator pagoValidator;

    public PaymentValidatorListenerImpl(PagoValidator paymentValidator) {
        this.pagoValidator = paymentValidator;

        final Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "payment-validator-1");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-validator");
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        ReceiverOptions<Object, Object> consumerOptions = ReceiverOptions.create(consumerProps)
                .subscription(Collections.singleton("unconfirmed-transactions"))
                .addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));

        kafkaReceiver = KafkaReceiver.create(consumerOptions);

        ((Flux<ReceiverRecord>) kafkaReceiver.receive())
                .doOnNext(r -> {

                    final PagosEvent paymentEvent = fromBinary((String) r.value(), PagosEvent.class);
                    processEvent(paymentEvent);
                    r.receiverOffset().acknowledge();
                })
                .subscribe();
    }

    private void processEvent(PagosEvent paymentEvent) {
        pagoValidator.calculaPago(paymentEvent);
    }

    private <T> T fromBinary(String object, Class<T> resultType) {
        try {
            return objectMapper.readValue(object, resultType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}