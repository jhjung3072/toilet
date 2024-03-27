package com.jaeho.toilet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ToiletApplicationException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public ToiletApplicationException(ErrorCode errorCode){
        this.errorCode=errorCode;
        this.message=null;
    }

    @Override
    public String getMessage() {
        if (message==null){
            return errorCode.getMessage();
        }
        return String.format("%s. %s",errorCode.getMessage(),message);
    }
}
