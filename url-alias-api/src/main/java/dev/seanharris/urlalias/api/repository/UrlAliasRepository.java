package dev.seanharris.urlalias.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlAliasRepository extends MongoRepository<UrlAliasDocument, String> {
}
