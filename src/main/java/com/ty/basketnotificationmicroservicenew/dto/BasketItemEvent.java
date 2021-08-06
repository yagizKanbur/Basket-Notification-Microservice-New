package com.ty.basketnotificationmicroservicenew.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketItemEvent {
    private String basketId;
    private Long userId;
    private Long productId;
}
