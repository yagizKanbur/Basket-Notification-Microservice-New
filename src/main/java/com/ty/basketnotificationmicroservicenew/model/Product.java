package com.ty.basketnotificationmicroservicenew.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document
@Getter
@Setter
public class Product {

    @Id
    private Long productId;
    @Field
    private Double productPrice;
    @Field
    private String productName;
    @Field
    private Integer stockQuantity;
    @Field
    private Set<Long> userIds;

    public Product(){
        userIds = new HashSet<>();
    }

    public void addUserToSet (Long userId) {
        userIds.add(userId);
    }

    public void removeUserFromSet (Long userId){
        userIds.remove(userId);
    }

}
