package com.ty.basketnotificationmicroservicenew.messaging;

import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.basketnotificationmicroservicenew.dto.BasketItemEvent;
import com.ty.basketnotificationmicroservicenew.model.UsersHaveProduct;
import com.ty.basketnotificationmicroservicenew.service.UsersHaveProductServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.DataInput;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasketConsumer implements Consumer {
    private final UsersHaveProductServiceV1 service;

    @KafkaListener(groupId = "notification-consumer-group", topics = "basket.update")
    public void basketUpdateCallback(BasketItemEvent message) {
        //ObjectMapper mapper = new ObjectMapper();
        log.info(String.valueOf(message));
        service.updateProductShoppersSet(message);
    }
    @KafkaListener(groupId = "notification-consumer-group", topics = "basket.create")
    public void basketCreateCallback(BasketItemEvent message) {
        //ObjectMapper mapper = new ObjectMapper();
        log.info(String.valueOf(message));
        service.saveToProductShoppersSet(message);
    }
    /*@KafkaListener(groupId = "notification-consumer-group", topics = "basket.order")
    public void basketOrderCallback(ItemEvent message) throws IOException {
        //ObjectMapper mapper = new ObjectMapper();
        service.removeFromProductsShoppersSets(mapper.readValue((DataInput) message, BasketCompleteOrderEvent.class));
    }*/
}

