package com.ssafy.account.common.api;

import com.ssafy.account.common.api.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 예외를 처리해주는 클래스
@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    // @ExceptionHandler: @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능
    @ExceptionHandler(GlobalRuntimeException.class)
    public ResponseEntity<FailResponse> handleGlobalRuntimeException(GlobalRuntimeException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<FailResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<FailResponse> handleDuplicatedException(DuplicatedException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<FailResponse> handleInsufficientBalanceException(InsufficientBalanceException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }

    @ExceptionHandler(NotCorrectException.class)
    public ResponseEntity<FailResponse> handleNotCorrectException(NotCorrectException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }
    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<FailResponse> invalidPaymentException(InvalidPaymentException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }

    @ExceptionHandler(RestrictedBusinessException.class)
    public ResponseEntity<FailResponse> restrictedBusinessException(RestrictedBusinessException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }

    @ExceptionHandler(InvalidTransferException.class)
    public ResponseEntity<FailResponse> invalidPaymentException(InvalidTransferException exception) {
        return ResponseEntity.status(exception.getFailCode().getStatus()).body(FailResponse.fail(exception.getFailCode()));
    }

}
