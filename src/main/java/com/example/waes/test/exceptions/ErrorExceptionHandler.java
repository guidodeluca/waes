package com.example.waes.test.exceptions;

import com.example.waes.test.api.Response;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<?> handleRuntimeException() {
        return new ResponseEntity<String>("ERR_GRL", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public final ResponseEntity<?> handleParseException() {
        return new ResponseEntity<String>("ERR_PARSE", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncompleteDataException.class)
    public final ResponseEntity<?> handleIncompleteDataException(IncompleteDataException e) {
        return new ResponseEntity<String>(e.getCode(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationDataException.class)
    public final ResponseEntity<?> handleValidationDataException(ValidationDataException e) {
        return new ResponseEntity<String>(e.getCode(), HttpStatus.CONFLICT);
    }
}
