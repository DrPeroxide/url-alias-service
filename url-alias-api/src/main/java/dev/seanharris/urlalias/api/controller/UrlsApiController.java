package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.UrlsApi;
import dev.seanharris.urlalias.api.model.GetAllUrls200ResponseInner;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UrlsApiController implements UrlsApi {

    private final UrlAliasManager urlAliasManager;

    /// See [UrlsApi#getAllUrls()]
    @Override
    public ResponseEntity<List<GetAllUrls200ResponseInner>> getAllUrls() {
        List<GetAllUrls200ResponseInner> foundAliases = urlAliasManager.getAllAliases().stream().map(toApiModel()).toList();
        return ResponseEntity.ok(foundAliases);
    }

    private static Function<UrlAlias, GetAllUrls200ResponseInner> toApiModel() {
        return alias -> new GetAllUrls200ResponseInner()
                .fullUrl(alias.redirectUrl().toString())
                .shortUrl(alias.shortenedUrl().toString())
                .alias(alias.alias());
    }
}
