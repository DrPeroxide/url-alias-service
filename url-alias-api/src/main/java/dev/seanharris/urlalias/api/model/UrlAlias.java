package dev.seanharris.urlalias.api.model;

import dev.seanharris.urlalias.api.repository.UrlAliasDocument;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

/// Complete data record for a given alias, containing serialisable and dynamic data alike.
/// Provides type conversion methods for convenience.
public record UrlAlias(String alias, URI redirectUrl, URI host, URI shortenedUrl) {

    public UrlAlias(String alias, URI redirectUrl, String host) {
        this(alias, redirectUrl, URI.create(host));
    }

    public UrlAlias(String alias, URI redirectUrl, URI host) {
        this(alias, redirectUrl, host, host.resolve(alias));
    }

    /// Returns a response entity representing a not found result.
    public static ResponseEntity<Void> notFound() {
        return ResponseEntity.notFound().build();
    }

    /// Returns a response entity representing this alias as a redirection.
    public ResponseEntity<Void> found() {
        return ResponseEntity.status(HttpStatus.FOUND).location(this.redirectUrl()).build();
    }

    /// Returns serialisable data as a document model
    public UrlAliasDocument toDocument() {
        return new UrlAliasDocument(alias, redirectUrl.toString());
    }

}
