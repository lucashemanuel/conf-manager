package com.lucashemanuel.confmanager.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucashemanuel.confmanager.domain.event.exceptions.EventNotFoundException;

@ControllerAdvice
public class ExceptionEntityHandler {
  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity handleEventNotFound(EventNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }
}
