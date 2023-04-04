package com.powernode.bank.exception;


//转账异常
public class TransferException extends Exception{
    public TransferException() {
        super();
    }

    public TransferException(String message) {
        super(message);
    }
}
