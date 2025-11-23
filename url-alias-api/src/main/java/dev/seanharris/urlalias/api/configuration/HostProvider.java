package dev.seanharris.urlalias.api.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.net.URI;

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
