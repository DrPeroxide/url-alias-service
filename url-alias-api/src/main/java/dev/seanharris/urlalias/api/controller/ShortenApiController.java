package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.ShortenApi;
import dev.seanharris.urlalias.api.model.ShortenPost201Response;
import dev.seanharris.urlalias.api.model.ShortenPostRequest;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShortenApiController implements ShortenApi {

    private final UrlAliasManager urlAliasManager;

    /// See [ShortenApi#shortenPost(ShortenPostRequest)]
    @Override
    public ResponseEntity<ShortenPost201Response> shortenPost(ShortenPostRequest request) {
        log.info("Shorten URL request received: {}", request);
        Optional<UrlAlias> foundAlias = urlAliasManager.getAlias(request.getCustomAlias());
        if (foundAlias.isEmpty()) {
            try {
                UrlAlias newAlias = urlAliasManager.createAlias(request.getCustomAlias(), request.getFullUrl());
                return createdResponse(newAlias);
            } catch (IllegalArgumentException e) {
                log.info("Shorten URL request {} failed;", request.getCustomAlias(), e);
                return ResponseEntity.badRequest().build();
            }
        } else {
            log.info("Shorten URL request {} failed; alias already exists!", request.getCustomAlias());
            return ResponseEntity.badRequest().build();
        }
    }

    private static ResponseEntity<ShortenPost201Response> createdResponse(UrlAlias newAlias) {
        var response = new ShortenPost201Response().shortUrl(newAlias.shortenedUrl().toString());
        return ResponseEntity.created(newAlias.shortenedUrl()).body(response);
    }

}
