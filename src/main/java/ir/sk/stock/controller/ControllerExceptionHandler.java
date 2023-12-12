package ir.sk.stock.controller;

import ir.sk.stock.exception.ErrorMessage;
import ir.sk.stock.exception.StockAlreadyExistsException;
import ir.sk.stock.exception.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Created by sad.kayvanfar on 12/21/2021 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(StockNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleStockNotFoundException(StockNotFoundException ex) {
    log.info("Stack Not Found Exception " + ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorMessage.of(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
  }

  @ExceptionHandler(StockAlreadyExistsException.class)
  public ResponseEntity<ErrorMessage> handleStockAlreadyExistsException(
      StockAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ErrorMessage.of(HttpStatus.CONFLICT.value(), ex.getMessage()));
  }
}
