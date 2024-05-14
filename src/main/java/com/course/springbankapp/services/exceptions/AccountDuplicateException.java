package com.course.springbankapp.services.exceptions;

public class AccountDuplicateException extends RuntimeException {

    public AccountDuplicateException(Object resource) {
        super("Account already exists: " + resource);
    }
}
