package com.zipe.exception;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/1/4 上午 08:46
 **/
public class InvoiceException extends Exception {

    public InvoiceException(String msg) {
        super(msg);
    }

    public InvoiceException(String msg, Throwable t) {
        super(msg, t);
    }
}
