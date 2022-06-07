package com.salvatorechiacchio.mygym.exception;

public class UtilsException extends RuntimeException {
    public enum utilsExceptionCode{
       DATA_WRONG_FORMAT
    }
    public UtilsException(utilsExceptionCode message) {
        super(message.toString());
    }
}
