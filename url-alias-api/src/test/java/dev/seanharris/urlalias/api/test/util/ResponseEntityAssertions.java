package dev.seanharris.urlalias.api.test.util;

import lombok.experimental.UtilityClass;
import org.assertj.core.api.Condition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.function.Predicate;

@UtilityClass
public class ResponseEntityAssertions {

    public static <T> Condition<ResponseEntity<T>> httpStatusOf(HttpStatus expectedStatus) {
        return new Condition<>(response -> response.getStatusCode() == expectedStatus, "%s", expectedStatus.toString());
    }

    public static <T> Condition<ResponseEntity<T>> locationHeaderSetTo(URI expectedLocation) {
        return new Condition<>(response -> expectedLocation.equals(response.getHeaders().getLocation()), "%s", expectedLocation);
    }

    public static <T> Condition<ResponseEntity<T>> body(Predicate<T> predicate) {
        return new Condition<>(response -> predicate.test(response.getBody()), "%s", predicate);
    }


}
