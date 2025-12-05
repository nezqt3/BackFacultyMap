package org.example.middleware;

import org.example.middleware.exceptions.NotEnoughArguments;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsWithRequests {

    @ExceptionHandler(NotEnoughArguments.class)
    public ResponseEntity<String> handleBookAlreadyExists(NotEnoughArguments ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(ex.getMessage());
    }

}
