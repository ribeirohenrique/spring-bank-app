package com.course.springbankapp.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object resource) {
        super("Resource not found: " + resource);
    }
}
