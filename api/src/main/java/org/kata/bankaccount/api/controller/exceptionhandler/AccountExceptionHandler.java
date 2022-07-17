package org.kata.bankaccount.api.controller.exceptionhandler;

import org.kata.bankaccount.api.controller.dto.ErrorMessageDto;
import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.exception.InvalidOperationAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AccountExceptionHandler.class);

    @ExceptionHandler({InvalidOperationAmountException.class, InsufficientBalanceException.class})
    protected ResponseEntity<Object> handleAccountExceptions(RuntimeException exception) {
        ErrorMessageDto errorDto = ErrorMessageDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();

        logger.warn("An account exception is raised -> {}",
                exception.getMessage());

        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }
}
