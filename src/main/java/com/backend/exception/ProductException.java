package com.backend.exception;

public class ProductException extends BaseException{

    public ProductException(String message) {
        super("product." + message);
    }

    public static ProductException notFound(){
        return new ProductException("not.found");
    }
}
