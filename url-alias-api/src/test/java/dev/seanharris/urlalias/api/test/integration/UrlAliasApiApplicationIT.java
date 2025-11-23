package dev.seanharris.urlalias.api.test.integration;

import dev.seanharris.urlalias.api.configuration.RedirectProperties;
import dev.seanharris.urlalias.api.model.ShortenPostRequest;
import dev.seanharris.urlalias.api.repository.UrlAliasRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;

import static dev.seanharris.urlalias.api.test.util.TestData.TEST_ALIAS;
import static dev.seanharris.urlalias.api.test.util.TestData.TEST_REDIRECT;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class UrlAliasApiApplicationIT {

    @LocalServerPort
    int port;

    @Autowired
    private UrlAliasRepository urlAliasRepository;

    @BeforeEach
    void init() {
        RestAssured.port = port;
    }

    @AfterEach
    void cleanUp() {
        urlAliasRepository.deleteAll();
    }

    @Test
    void givenAliasAndFullUrl_whenShortenUrl_thenGetAlias_thenRedirectIsReturned() {
        var request = new ShortenPostRequest().customAlias(TEST_ALIAS).fullUrl(TEST_REDIRECT);
        String shortenedUri = given().contentType(ContentType.JSON)
                .body(request)
                .post("/shorten")
                .path("shortUrl");

        given().redirects()
                .follow(false)
                .get(URI.create(shortenedUri))
                .then()
                .statusCode(is(HttpStatus.FOUND.value()))
                .and()
                .header("Location", is(TEST_REDIRECT));
    }

    @Test
    void givenAlias_whenShortenUrl_thenDeleteAlias_thenAliasIsDeleted() {
        var request = new ShortenPostRequest().customAlias(TEST_ALIAS).fullUrl(TEST_REDIRECT);
        String shortenedUri = given().contentType(ContentType.JSON)
                .body(request)
                .post("/shorten")
                .path("shortUrl");

        assertThat(urlAliasRepository.findAll()).isNotEmpty();

        given().delete(URI.create(shortenedUri))
                .then()
                .statusCode(is(HttpStatus.NO_CONTENT.value()));

        assertThat(urlAliasRepository.findAll()).isEmpty();
    }

    @Test
    void givenAlias_whenShortenUrl_thenGetAliases_thenAliasIsDisplayedInList() {
        var request = new ShortenPostRequest().customAlias(TEST_ALIAS).fullUrl(TEST_REDIRECT);
        given().contentType(ContentType.JSON)
                .body(request)
                .post("/shorten")
                .path("shortUrl");

        given().get("urls")
                .then()
                .statusCode(is(HttpStatus.OK.value()))
                .and()
                .body("alias", contains(TEST_ALIAS));
    }
}
