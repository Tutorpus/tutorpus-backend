package com.tutorpus.tutorpus.exception;

public class PasswordIncorrectException extends RuntimeException{
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
