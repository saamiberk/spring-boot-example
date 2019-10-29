package com.spring.security.example.exceptions;

public class UserServiceException extends RuntimeException {

    /*
     it provide different logic like error messages, json, etc
     if we need to.
    */
    public UserServiceException(String message) {
        super(message);
    }

}
