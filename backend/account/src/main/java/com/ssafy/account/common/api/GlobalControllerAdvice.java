package com.ssafy.account.common.api;

import com.ssafy.account.common.api.exception.DuplicatedException;
import com.ssafy.account.common.api.exception.InsufficientBalanceException;
import com.ssafy.account.common.api.exception.NotCorrectException;
import com.ssafy.account.common.api.exception.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 예외를 처리해주는 클래스
@RestControllerAdvice
public class GlobalControllerAdvice {

    // @ExceptionHandler: @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능
    @ExceptionHandler(NotFoundException.class)
    public Response handleNotFoundException(NotFoundException exception) {
        return Response.fail(exception.getFailCode());
    }

    @ExceptionHandler(DuplicatedException.class)
    public Response handleDuplicatedException(DuplicatedException exception) {
        return Response.fail(exception.getFailCode());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public Response handleInsufficientBalanceException(InsufficientBalanceException exception) {
        return Response.fail(exception.getFailCode());
    }

    @ExceptionHandler(NotCorrectException.class)
    public Response handleNotCorrectException(NotCorrectException exception) {
        return Response.fail(exception.getFailCode());
    }
}
