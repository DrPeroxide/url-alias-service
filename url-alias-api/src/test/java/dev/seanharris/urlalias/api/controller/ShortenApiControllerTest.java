package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.model.CreatedAlias;
import dev.seanharris.urlalias.api.model.NewAlias;
import dev.seanharris.urlalias.api.service.CreateAliasException;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

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
        when(mockUrlAliasManager.createAlias(TEST_ALIAS, TEST_REDIRECT)).thenReturn(TEST_ALIAS_RECORD);

        var request = NewAlias.builder().customAlias(TEST_ALIAS).fullUrl(TEST_REDIRECT).build();
        var response = controller.createNewAlias(request);
        assertThat(response).has(httpStatusOf(HttpStatus.CREATED));
        var responseBody = response.getBody();
        assertThat(responseBody).isEqualTo(expectedResponseBody());
    }

    private CreatedAlias expectedResponseBody() {
        return new CreatedAlias().shortUrl(TEST_ALIAS_RECORD.shortenedUrl().toString());
    }
}