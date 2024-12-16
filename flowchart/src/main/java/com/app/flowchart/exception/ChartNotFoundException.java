package com.app.flowchart.exception;

public class ChartNotFoundException extends RuntimeException {
    public ChartNotFoundException(String message){
        super(message);
    }
}
