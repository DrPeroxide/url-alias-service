package dev.seanharris.urlalias.api.service;

public class CreateAliasException extends RuntimeException {

    public CreateAliasException(String alias, String reason) {
        super(String.format("Cannot create alias '%s'; %s", alias, reason));
    }
}
