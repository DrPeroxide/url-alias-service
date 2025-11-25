package dev.seanharris.urlalias.api.service;

import dev.seanharris.urlalias.api.configuration.HostProvider;
import dev.seanharris.urlalias.api.configuration.RedirectProperties;
import dev.seanharris.urlalias.api.model.UrlAlias;
import dev.seanharris.urlalias.api.repository.UrlAliasDocument;
import dev.seanharris.urlalias.api.repository.UrlAliasRepository;
import dev.seanharris.urlalias.api.test.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static dev.seanharris.urlalias.api.test.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlAliasManagerServiceTest {

    @InjectMocks
    private UrlAliasManagerService urlAliasManager;

    @Mock
    private UrlAliasRepository mockRepository;

    @Mock
    private HostProvider mockHost;

    @Test
    void givenAliasExists_whenGetAlias_thenReturnAliasRecord() {
        when(mockHost.rootUri()).thenReturn(TEST_HOST_URI);
        when(mockRepository.findById(TEST_ALIAS)).thenReturn(Optional.of(testDoc()));
        Optional<UrlAlias> result = urlAliasManager.getAlias(TEST_ALIAS);
        assertThat(result).contains(testData());
    }

    @Test
    void givenAliasDoesNotExist_whenGetAlias_thenReturnEmpty() {
        when(mockRepository.findById(TEST_ALIAS)).thenReturn(Optional.empty());
        Optional<UrlAlias> result = urlAliasManager.getAlias(TEST_ALIAS);
        assertThat(result).isEmpty();
    }

    @Test
    void givenAliasExists_whenDeleteAlias_thenDeleteAliasAndReturnTrue() {
        when(mockRepository.existsById(TEST_ALIAS)).thenReturn(true);
        assertThat(urlAliasManager.deleteAlias(TEST_ALIAS)).isTrue();
        verify(mockRepository).deleteById(TEST_ALIAS);
    }

    @Test
    void givenAliasDoesNotExist_whenDeleteAlias_thenReturnFalse() {
        assertThat(urlAliasManager.deleteAlias(TEST_ALIAS)).isFalse();
        verify(mockRepository, never()).deleteById(TEST_ALIAS);
    }

    @Test
    void givenAliasDoesNotExist_whenCreateAlias_thenCreateAliasAndReturn() {
        when(mockHost.rootUri()).thenReturn(TEST_HOST_URI);
        String shortenedUrl = "google.com";
        when(mockRepository.save(new UrlAliasDocument(TEST_ALIAS, shortenedUrl))).thenReturn(testDoc());
        UrlAlias result = urlAliasManager.createAlias(TEST_ALIAS, shortenedUrl);
        assertThat(result).isEqualTo(testData());
    }

    @Test
    void givenAliasExists_whenGetAllAlias_ReturnAllAliasRecord() {
        when(mockHost.rootUri()).thenReturn(TEST_HOST_URI);
        when(mockRepository.findAll()).thenReturn(List.of(testDoc()));
        List<UrlAlias> result = urlAliasManager.getAllAliases();
        assertThat(result).contains(testData());
    }

    private static UrlAliasDocument testDoc() {
        return new UrlAliasDocument(TEST_ALIAS, TestData.TEST_REDIRECT);
    }

    private static UrlAlias testData() {
        return new UrlAlias(TEST_ALIAS, URI.create(TestData.TEST_REDIRECT), TEST_HOST);
    }

}