package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.model.GetAllUrls200ResponseInner;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static dev.seanharris.urlalias.api.test.util.ResponseEntityAssertions.httpStatusOf;
import static dev.seanharris.urlalias.api.test.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlsApiControllerTest {

    @InjectMocks
    UrlsApiController controller;

    @Mock
    UrlAliasManager mockUrlAliasManager;

    @Test
    void givenExistingAlias_whenGetAllUrls_thenReturnAllAliasData() {
        when(mockUrlAliasManager.getAllAliases()).thenReturn(List.of(TEST_ALIAS_RECORD));
        var response = controller.getAllUrls();
        assertThat(response).has(httpStatusOf(HttpStatus.OK));
        var responseBody = response.getBody();
        assertThat(responseBody).contains(expectedResponseBody());
    }

    private GetAllUrls200ResponseInner expectedResponseBody() {
        return new GetAllUrls200ResponseInner()
                .shortUrl(TEST_ALIAS_RECORD.shortenedUrl().toString())
                .alias(TEST_ALIAS)
                .fullUrl(TEST_REDIRECT);
    }
}