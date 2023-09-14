package com.msys.digitalwallet.notification.common.exception;

import com.msys.digitalwallet.notification.common.exception.enums.ExceptionType;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

    private ExceptionType exceptionType;
    private HttpStatus httpStatus;
    public BusinessException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
        this.httpStatus=HttpStatus.OK;

    }

    public BusinessException(BusinessException businessException) {
        super(businessException);
    }

    public BusinessException(HttpStatus httpStatus,ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
        this.httpStatus=httpStatus;

    }


    public ExceptionType getExceptionType(){
        return this.exceptionType;
    }
}