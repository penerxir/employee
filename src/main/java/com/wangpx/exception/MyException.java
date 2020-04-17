package com.wangpx.exception;

public class MyException extends Exception {

    //异常信息
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public MyException(){}

    public MyException(String message) {

        this.message = message;
    }
}
