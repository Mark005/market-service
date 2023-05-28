package com.bmo.common.market_service.core.exception;

import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(MarketServiceBusinessException.class)
  public ResponseEntity<ExceptionResponseBody> handleBadCredentialsException(MarketServiceBusinessException e) {
    log.warn(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponseBody.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(e.getMessage())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ExceptionResponseBody> handleBadCredentialsException(EntityNotFoundException e) {
    log.warn(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ExceptionResponseBody.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message(e.getMessage())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionResponseBody> handleBadCredentialsException(IllegalArgumentException e) {
    log.warn(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponseBody.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(e.getMessage())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseBody> handleBadCredentialsException(MethodArgumentNotValidException e) {
    log.warn(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponseBody.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(e.getAllErrors().toString())
            .timestamp(LocalDateTime.now())
            .build());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ExceptionResponseBody> handleUncaughtExceptions(Exception e) {
    log.error(e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionResponseBody.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message(e.getMessage())
            .timestamp(LocalDateTime.now())
            .build());
  }
}
