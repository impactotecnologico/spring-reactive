package net.impactotecnologico.reactivos.gateway;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.impactotecnologico.reactivos.command.Pago;
import net.impactotecnologico.reactivos.event.PagosEvent;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

@Component
public class PagosGatewayImpl implements PagosGateway {

    private KafkaSender kafkaProducer;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String gatewayName = "1";


    public PagosGatewayImpl() {
        final Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        final SenderOptions<Integer, String> producerOptions 
        = SenderOptions.create(producerProps);

        kafkaProducer = KafkaSender.create(producerOptions);
    }


    @Override
    public Mono<Void> procesarPago(final Pago createPayment) {
        final PagosEvent payment = new PagosEvent(createPayment.getId(), 
        		createPayment.getCreditCardNumber(), createPayment.getAmount(), gatewayName);

        String payload = toBinary(payment);

        SenderRecord<Integer, String, Integer> message = 
        		SenderRecord.create(
        				new ProducerRecord<>("unconfirmed-transactions", payload), 1);
        return kafkaProducer.send(Mono.just(message)).next();
    }

    private String toBinary(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
