package dev.seanharris.urlalias.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("redirect")
public record RedirectProperties(String host) {

    @ConstructorBinding
    public RedirectProperties {
    }
}
