package com.salvatorechiacchio.mygym.exception;

public class UtilsException extends RuntimeException {
    public UtilsException(utilsExceptionCode message) {
        super(message.toString());
    }

    public enum utilsExceptionCode {
        DATA_WRONG_FORMAT
    }
}
