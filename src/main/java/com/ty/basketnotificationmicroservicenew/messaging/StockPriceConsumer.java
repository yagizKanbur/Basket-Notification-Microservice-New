package com.ty.basketnotificationmicroservicenew.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.basketnotificationmicroservicenew.model.UsersHaveProduct;
import com.ty.basketnotificationmicroservicenew.service.UsersHaveProductServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockPriceConsumer implements Consumer {
    private final UsersHaveProductServiceV1 service;


    @KafkaListener(groupId = "notification-consumer-group", topics = "stock.update")
    public void stockPriceUpdateCallback(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        service.updateProduct(mapper.readValue(message, UsersHaveProduct.class));
    }
    @KafkaListener(groupId = "notification-consumer-group", topics = "stock.create")
    public void stockPriceCreateCallback(String message) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        service.addProduct(mapper.readValue(message, UsersHaveProduct.class));
    }
}
