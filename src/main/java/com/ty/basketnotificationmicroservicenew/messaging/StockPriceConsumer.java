package com.ty.basketnotificationmicroservicenew.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.basketnotificationmicroservicenew.model.Product;
import com.ty.basketnotificationmicroservicenew.service.ProductServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockPriceConsumer implements Consumer {
    private final ProductServiceV1 service;


    @KafkaListener(groupId = "notification-consumer-group", topics = "stock.update")
    public void stockPriceUpdateCallback(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        service.updateProduct(mapper.readValue(message, Product.class));
    }
    @KafkaListener(groupId = "notification-consumer-group", topics = "stock.create")
    public void stockPriceCreateCallback(String message) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        service.addProduct(mapper.readValue(message, Product.class));
    }
}
