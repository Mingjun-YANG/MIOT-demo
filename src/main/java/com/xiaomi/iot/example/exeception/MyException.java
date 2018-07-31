package com.xiaomi.iot.example.exeception;

public class MyException extends Exception {

    private int httpStatus;

    public MyException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
