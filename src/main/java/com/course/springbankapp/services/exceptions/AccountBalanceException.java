package com.course.springbankapp.services.exceptions;

public class AccountBalanceException extends RuntimeException {

    public AccountBalanceException(Object resource) {
        super("Insufficient account balance");
    }
}
