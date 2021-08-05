package com.ty.basketnotificationmicroservicenew.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.basketnotificationmicroservicenew.dto.BasketCompleteOrderEvent;
import com.ty.basketnotificationmicroservicenew.dto.BasketEvent;
import com.ty.basketnotificationmicroservicenew.model.Product;
import com.ty.basketnotificationmicroservicenew.service.ProductServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasketConsumer implements Consumer {
    private final ProductServiceV1 service;

    @KafkaListener(groupId = "notification-consumer-group", topics = "basket.update")
    public void basketUpdateCallback(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        service.updateProductShoppersSet(mapper.readValue(message, BasketEvent.class));
    }
    @KafkaListener(groupId = "notification-consumer-group", topics = "basket.create")
    public void basketCreateCallback(String message) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        service.saveToProductShoppersSet(mapper.readValue(message, BasketEvent.class));
    }
    @KafkaListener(groupId = "notification-consumer-group", topics = "basket.order")
    public void basketOrderCallback(String message) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        service.removeFromProductsShoppersSets(mapper.readValue(message, BasketCompleteOrderEvent.class));
    }
}

