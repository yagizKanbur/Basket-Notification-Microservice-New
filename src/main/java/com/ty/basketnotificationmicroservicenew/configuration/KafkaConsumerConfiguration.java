package com.ty.basketnotificationmicroservicenew.configuration;

import com.ty.basketnotificationmicroservicenew.dto.BasketItemEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    private static final String BOOTSTRAP_ADDRESS = "kafka:9092";
    private static final String GROUP_ID = "notification-consumer-group";

    @Bean
    public ConsumerFactory<String, BasketItemEvent> consumerFactory() {
        JsonDeserializer<BasketItemEvent> deserializer = new JsonDeserializer<>(BasketItemEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, BasketItemEvent.class);
        return new DefaultKafkaConsumerFactory<String, BasketItemEvent>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(BasketItemEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BasketItemEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BasketItemEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
