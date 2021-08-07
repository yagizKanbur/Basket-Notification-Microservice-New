package com.ty.basketnotificationmicroservicenew.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketItemEvent {
    private String basketId;
    private Long userId;
    private Long productId;

    public BasketItemEvent(String basketId, Long userId, Long productId) {
        this.basketId = basketId;
        this.userId = userId;
        this.productId = productId;
    }

    public BasketItemEvent(){

    }
}
