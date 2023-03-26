package com.vinay.kafka.KafkaDemoTest.exception;

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
