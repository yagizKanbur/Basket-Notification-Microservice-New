package com.ty.basketnotificationmicroservicenew.exceptions;

import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public ProductException(){
        super();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        message = "Something happened";
    }

    public ProductException(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
