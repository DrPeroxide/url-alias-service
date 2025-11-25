package dev.seanharris.urlalias.api.service;

import dev.seanharris.urlalias.api.model.UrlAlias;

import java.util.List;
import java.util.Optional;

/// Interacts with the data source to create, retrieve and delete aliases.
public interface UrlAliasManager {

    /// @return url data for given alias, or empty if no data exists.
    Optional<UrlAlias> getAlias(String alias);

    /// Deletes any stored data for the given alias,
    /// @return true if the deletion succeeded, false if no data exists
    boolean deleteAlias(String alias);

    /// Creates and stores a new alias using the given parameters.
    ///
    /// @return url data for the created alias *after* creation
    UrlAlias createAlias(String customAlias, String fullUrl);

    /// @return all url data for all stored aliases
    List<UrlAlias> getAllAliases();
}
