package com.inditex.similarproductsbackend.exception;

public class ServiceException extends BaseException {
    public ServiceException(Throwable t) {
        super(t);
    }
    public ServiceException(String message) {
        super(message);
    }
}
