package com.ty.basketnotificationmicroservicenew.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketEvent {
    private Long userId;
    private Long productId;
}
