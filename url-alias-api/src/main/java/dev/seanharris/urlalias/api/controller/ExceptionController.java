package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.service.CreateAliasException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleException(RuntimeException exception) {
        log.error("Unexpected error", exception);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(CreateAliasException.class)
    public ResponseEntity<Void> handleCreateAliasFailure(CreateAliasException exception) {
        log.info("Failed to create new alias", exception);
        return ResponseEntity.internalServerError().build();
    }
}
