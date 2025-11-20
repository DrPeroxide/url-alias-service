package dev.seanharris.urlalias.api.model;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static dev.seanharris.urlalias.api.test.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class UrlAliasTest {

    @Test
    void givenAliasAndHost_whenGetShortenedUrl_thenHostWithAliasPathIsReturned() {
        var newRecord = new UrlAlias(TEST_ALIAS, URI.create(TEST_REDIRECT), TEST_HOST);
        assertThat(newRecord.shortenedUrl())
                .isNotNull()
                .extracting(URI::toString)
                .isEqualTo(TEST_HOST + TEST_ALIAS);
    }

}