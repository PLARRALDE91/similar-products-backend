package com.inditex.similarproductsbackend.exception;

public class ProductDataProviderException extends BaseException {
    public ProductDataProviderException(String message, Throwable ex) {
        super(message, ex);
    }
    public ProductDataProviderException(String message) {
        super(message);
    }
}
