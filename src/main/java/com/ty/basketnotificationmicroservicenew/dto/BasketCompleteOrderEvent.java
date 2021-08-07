package com.ty.basketnotificationmicroservicenew.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class BasketCompleteOrderEvent {
    private Long userId;
    private List<Long> productIds = new ArrayList<>();

    public BasketCompleteOrderEvent(Long userId, List<Long> productIds) {
        this.userId = userId;
        this.productIds = productIds;
    }
}
