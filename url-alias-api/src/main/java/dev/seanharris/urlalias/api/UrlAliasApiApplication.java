package dev.seanharris.urlalias.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EnableAutoConfiguration
@ConfigurationPropertiesScan("dev.seanharris.urlalias.api.configuration")
public class UrlAliasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlAliasApiApplication.class, args);
	}

}
