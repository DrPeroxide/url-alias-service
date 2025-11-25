package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import dev.seanharris.urlalias.api.test.util.TestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static dev.seanharris.urlalias.api.test.util.ResponseEntityAssertions.httpStatusOf;
import static dev.seanharris.urlalias.api.test.util.ResponseEntityAssertions.locationHeaderSetTo;
import static dev.seanharris.urlalias.api.test.util.TestData.TEST_ALIAS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AliasApiControllerTest {

    @InjectMocks
    AliasApiController controller;

    @Mock
    UrlAliasManager mockUrlAliasManager;

    @Test
    void givenAlias_whenGetAliasRedirect_thenResponseContainingRedirectionShouldBeReturned() {
        UrlAlias expectedAlias = TestData.TEST_ALIAS_RECORD;
        when(mockUrlAliasManager.getAlias(TEST_ALIAS)).thenReturn(Optional.of(expectedAlias));
        ResponseEntity<Void> response = controller.getRedirectByAlias(TEST_ALIAS);
        assertThat(response)
                .has(httpStatusOf(HttpStatus.FOUND))
                .has(locationHeaderSetTo(expectedAlias.redirectUrl()));
    }

    @Test
    void givenAliasDoesNotExist_whenGetAliasRedirect_thenNotFoundResponseShouldBeReturned() {
        when(mockUrlAliasManager.getAlias(TEST_ALIAS)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = controller.getRedirectByAlias(TEST_ALIAS);
        assertThat(response).has(httpStatusOf(HttpStatus.NOT_FOUND));
    }

    @ParameterizedTest
    @EnumSource(DeleteAliasLogicTable.class)
    void givenAlias_whenDeleteAlias_thenHttpResponseShouldBeReturned(DeleteAliasLogicTable params) {
        when(mockUrlAliasManager.deleteAlias(TEST_ALIAS)).thenReturn(params.deleteResult);
        ResponseEntity<Void> response = controller.deleteAlias(TEST_ALIAS);
        assertThat(response).has(httpStatusOf(params.expectedHttpStatus));
    }

    @RequiredArgsConstructor
    enum DeleteAliasLogicTable {
        DELETE_SUCCESS(true, HttpStatus.NO_CONTENT),
        NOT_FOUND(false, HttpStatus.NOT_FOUND);

        private final boolean deleteResult;
        private final HttpStatus expectedHttpStatus;
    }

}