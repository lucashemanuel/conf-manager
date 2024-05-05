package com.lucashemanuel.confmanager.config;

import com.lucashemanuel.confmanager.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.lucashemanuel.confmanager.domain.attendee.exceptions.AttendeeNotFoundException;
import com.lucashemanuel.confmanager.domain.checkin.exceptions.CheckInAlreadyExistsException;
import com.lucashemanuel.confmanager.domain.event.exceptions.EventFullException;
import com.lucashemanuel.confmanager.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

  @ExceptionHandler(EventFullException.class)
  public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception) {
    return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
  }

  @ExceptionHandler(AttendeeNotFoundException.class)
  public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(AttendeeAlreadyExistException.class)
  public ResponseEntity handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }

  @ExceptionHandler(CheckInAlreadyExistsException.class)
  public ResponseEntity handleCheckInAlreadyExist(CheckInAlreadyExistsException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }
}
