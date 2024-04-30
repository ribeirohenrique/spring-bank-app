package com.course.springbankapp.services.exceptions;

public class AccountLimitException extends RuntimeException {

    public AccountLimitException(Object resource) {
        super("Insufficient account limit");
    }
}
