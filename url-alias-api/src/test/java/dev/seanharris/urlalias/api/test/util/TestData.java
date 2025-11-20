package dev.seanharris.urlalias.api.test.util;

import dev.seanharris.urlalias.api.model.UrlAlias;
import lombok.experimental.UtilityClass;

import java.net.URI;

@UtilityClass
public class TestData {
    public static final String TEST_HOST = "http://localhost:8080/";
    public static final URI TEST_HOST_URI = URI.create(TEST_HOST);
    public static final String TEST_ALIAS = "my-alias";
    public static final String TEST_REDIRECT = "http://google.com/";
    public static final UrlAlias TEST_ALIAS_RECORD = getTestAlias();

    private static UrlAlias getTestAlias() {
        return new UrlAlias(TEST_ALIAS, URI.create(TEST_REDIRECT), TEST_HOST_URI);
    }
}
