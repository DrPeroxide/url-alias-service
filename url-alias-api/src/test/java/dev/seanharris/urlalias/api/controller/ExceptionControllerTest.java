package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.service.CreateAliasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static dev.seanharris.urlalias.api.test.util.TestData.TEST_ALIAS;
import static org.assertj.core.api.Assertions.assertThat;

class ExceptionControllerTest {

    private ExceptionController exceptionController;

    @BeforeEach
    void setUp() {
        exceptionController = new ExceptionController();
    }

    @Test
    void givenAnyException_whenHandleException_thenReturnInternalServerError() {
        ResponseEntity<Void> response = exceptionController
                .handleException(new IllegalArgumentException());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenAndCreateAliasException_whenHandleCreateAliasFailure_thenReturnBadRequest() {
        ResponseEntity<Void> response = exceptionController
                .handleCreateAliasFailure(new CreateAliasException(TEST_ALIAS, "error"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}