package dev.seanharris.urlalias.api.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.net.URI;

/// Utility service to provide the complete host URI for the API.
/// Protocol and hostname/IP are configured via the properties
/// Port is initially sourced from the server.port property,
/// but is updated when the web server is initialised.
@RequiredArgsConstructor
@Configuration
public class HostProvider {

    @Value("${server.port}")
    private int port;

    private final RedirectProperties redirectProperties;

    @EventListener
    public void onApplicationEvent(final ServletWebServerInitializedEvent event) {
        port = event.getWebServer().getPort();
    }

    public URI rootUri() {
        return URI.create(redirectProperties.host() + ":" + port);
    }
}
