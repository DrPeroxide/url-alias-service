package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.model.ShortenPost201Response;
import dev.seanharris.urlalias.api.model.ShortenPostRequest;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static dev.seanharris.urlalias.api.test.util.ResponseEntityAssertions.httpStatusOf;
import static dev.seanharris.urlalias.api.test.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ShortenApiControllerTest {

    @InjectMocks
    ShortenApiController controller;

    @Mock
    UrlAliasManager mockUrlAliasManager;

    @Test
    void givenNoExistingAlias_whenShortenUrl_thenReturnShortenedUrl() {
        when(mockUrlAliasManager.getAlias(TEST_ALIAS)).thenReturn(Optional.empty());
        when(mockUrlAliasManager.createAlias(TEST_ALIAS, TEST_REDIRECT)).thenReturn(getTestAlias());

        var request = ShortenPostRequest.builder().customAlias(TEST_ALIAS).fullUrl(TEST_REDIRECT).build();
        var response = controller.shortenPost(request);
        assertThat(response).has(httpStatusOf(HttpStatus.CREATED));
        var responseBody = response.getBody();
        assertThat(responseBody).isEqualTo(expectedResponseBody());
    }

    @Test
    void givenExistingAlias_whenShortenUrl_thenReturn400Response() {
        when(mockUrlAliasManager.getAlias(TEST_ALIAS)).thenReturn(Optional.of(getTestAlias()));

        var request = ShortenPostRequest.builder().customAlias(TEST_ALIAS).fullUrl(TEST_REDIRECT).build();
        var response = controller.shortenPost(request);
        assertThat(response).has(httpStatusOf(HttpStatus.BAD_REQUEST));
        var responseBody = response.getBody();
        assertThat(responseBody).isNull();
    }

    private ShortenPost201Response expectedResponseBody() {
        return new ShortenPost201Response().shortUrl(getTestAlias().shortenedUrl().toString());
    }
}