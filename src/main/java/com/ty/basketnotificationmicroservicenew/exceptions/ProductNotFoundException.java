package com.ty.basketnotificationmicroservicenew.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ProductException{
    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Product Not Found");
    }

}
