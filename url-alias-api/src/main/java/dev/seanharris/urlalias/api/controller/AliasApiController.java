package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.AliasApi;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AliasApiController implements AliasApi {

    private final UrlAliasManager urlAliasManager;

    /// See [AliasApi#deleteAlias]
    @Override
    public ResponseEntity<Void> deleteAlias(String alias) {
        Optional<UrlAlias> foundAlias = urlAliasManager.getAlias(alias);
        if (foundAlias.isEmpty()) {
            return UrlAlias.notFound();
        }
        urlAliasManager.deleteAlias(foundAlias.get());
        return ResponseEntity.noContent().build();
    }

    /// See [AliasApi#redirectToAlias(String)]
    @Override
    public ResponseEntity<Void> redirectToAlias(String alias) {
        return urlAliasManager.getAlias(alias).map(UrlAlias::found).orElseGet(UrlAlias::notFound);
    }

}
