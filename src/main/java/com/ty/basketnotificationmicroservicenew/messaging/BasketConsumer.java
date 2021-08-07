package com.ty.basketnotificationmicroservicenew.messaging;

import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.basketnotificationmicroservicenew.dto.BasketItemEvent;
import com.ty.basketnotificationmicroservicenew.service.UsersHaveProductServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BasketConsumer implements Consumer {
    private final UsersHaveProductServiceV1 service;

    @KafkaListener(groupId = "notification-consumer-group", topics = "basket.update")
    public void basketUpdateCallback(BasketItemEvent message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        service.updateProductShoppersSet(message);
    }
    @KafkaListener(groupId = "notification-consumer-group", topics = "basket.create")
    public void basketCreateCallback(BasketItemEvent message) throws IOException {
        //ObjectMapper mapper = new ObjectMapper();
        service.saveToProductShoppersSet(message);
    }
    /*@KafkaListener(groupId = "notification-consumer-group", topics = "basket.order")
    public void basketOrderCallback(ItemEvent message) throws IOException {
        //ObjectMapper mapper = new ObjectMapper();
        service.removeFromProductsShoppersSets(mapper.readValue((DataInput) message, BasketCompleteOrderEvent.class));
    }*/
}

