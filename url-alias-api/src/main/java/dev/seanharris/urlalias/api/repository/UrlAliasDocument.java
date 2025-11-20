package dev.seanharris.urlalias.api.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "url-aliases")
public record UrlAliasDocument(@Id String alias, String fullUrl) {


}
