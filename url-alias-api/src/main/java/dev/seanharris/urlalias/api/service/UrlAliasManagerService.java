package dev.seanharris.urlalias.api.service;

import dev.seanharris.urlalias.api.configuration.HostProvider;
import dev.seanharris.urlalias.api.configuration.RedirectProperties;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.repository.UrlAliasDocument;
import dev.seanharris.urlalias.api.repository.UrlAliasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlAliasManagerService implements UrlAliasManager {

    private final HostProvider hostProvider;
    private final UrlAliasRepository urlAliasRepository;

    private UrlAlias fromDocument(UrlAliasDocument urlAliasDocument) {
        return new UrlAlias(urlAliasDocument.alias(), URI.create(urlAliasDocument.fullUrl()), hostProvider.rootUri());
    }

    @Override
    public Optional<UrlAlias> getAlias(String alias) {
        return urlAliasRepository.findById(alias).map(this::fromDocument);
    }

    @Override
    public void deleteAlias(UrlAlias urlAlias) {
        urlAliasRepository.deleteById(urlAlias.alias());
    }

    @Override
    public UrlAlias createAlias(String customAlias, String fullUrl) {
        UrlAlias newAlias = new UrlAlias(customAlias, URI.create(fullUrl), hostProvider.rootUri());
        UrlAliasDocument createdAlias = urlAliasRepository.save(newAlias.toDocument());
        return fromDocument(createdAlias);
    }

    @Override
    public List<UrlAlias> getAllAliases() {
        return urlAliasRepository.findAll().stream().map(this::fromDocument).toList();
    }
}
