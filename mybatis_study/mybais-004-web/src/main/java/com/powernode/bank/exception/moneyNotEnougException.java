package com.powernode.bank.exception;


//余额不足异常
public class moneyNotEnougException extends Exception{
    public moneyNotEnougException() {
        super();
    }

    public moneyNotEnougException(String message) {
        super(message);
    }
}
