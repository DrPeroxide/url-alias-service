package dev.seanharris.urlalias.api.controller;

import dev.seanharris.urlalias.api.AliasApi;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.service.UrlAliasManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AliasApiController implements AliasApi {

    private final UrlAliasManager urlAliasManager;

    /// See [AliasApi#deleteAlias]
    @Override
    public ResponseEntity<Void> deleteAlias(String alias) {
        log.info("Delete alias request received for {}", alias);
        if (urlAliasManager.deleteAlias(alias)) {
            return ResponseEntity.noContent().build();
        } else {
            return UrlAlias.notFound();

        }
    }

    /// See [AliasApi#getRedirectByAlias(String)]
    @Override
    public ResponseEntity<Void> getRedirectByAlias(String alias) {
        log.debug("Redirect alias request received for {}", alias);
        return urlAliasManager.getAlias(alias)
                .map(UrlAlias::found)
                .orElseGet(UrlAlias::notFound);
    }

}
