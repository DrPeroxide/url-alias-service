package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.ShortenApi;
import dev.seanharris.urlalias.api.model.CreatedAlias;
import dev.seanharris.urlalias.api.model.NewAlias;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ShortenApiController implements ShortenApi {

    private final UrlAliasManager urlAliasManager;

    /// See [ShortenApi#createNewAlias(NewAlias)]
    @Override
    public ResponseEntity<CreatedAlias> createNewAlias(NewAlias request) {
        log.debug("New Alias request received: {}", request);
        UrlAlias newAlias = urlAliasManager.createAlias(request.getCustomAlias(), request.getFullUrl());
        log.info("Created new alias: {}", newAlias);
        return createdResponse(newAlias);
    }

    private static ResponseEntity<CreatedAlias> createdResponse(UrlAlias newAlias) {
        var response = new CreatedAlias().shortUrl(newAlias.shortenedUrl().toString());
        return ResponseEntity.created(newAlias.shortenedUrl()).body(response);
    }

}
