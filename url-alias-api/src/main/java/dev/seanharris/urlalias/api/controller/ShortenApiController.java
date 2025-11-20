package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.ShortenApi;
import dev.seanharris.urlalias.api.model.ShortenPost201Response;
import dev.seanharris.urlalias.api.model.ShortenPostRequest;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShortenApiController implements ShortenApi {

    private final UrlAliasManager urlAliasManager;

    /// See [ShortenApi#shortenPost(ShortenPostRequest)]
    @Override
    public ResponseEntity<ShortenPost201Response> shortenPost(ShortenPostRequest shortenPostRequest) {
        Optional<UrlAlias> foundAlias = urlAliasManager.getAlias(shortenPostRequest.getCustomAlias());
        if (foundAlias.isEmpty()) {
            UrlAlias newAlias = urlAliasManager.createAlias(shortenPostRequest.getCustomAlias(), shortenPostRequest.getFullUrl());
            return createdResponse(newAlias);
        }
        return ResponseEntity.badRequest().build();
    }

    private static ResponseEntity<ShortenPost201Response> createdResponse(UrlAlias newAlias) {
        var response = new ShortenPost201Response().shortUrl(newAlias.shortenedUrl().toString());
        return ResponseEntity.created(newAlias.shortenedUrl()).body(response);
    }

}
