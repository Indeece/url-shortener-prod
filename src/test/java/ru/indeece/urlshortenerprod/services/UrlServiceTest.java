package ru.indeece.urlshortenerprod.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.indeece.urlshortenerprod.services.impl.UrlServiceImpl;
import ru.indeece.urlshortenerprod.services.impl.UrlStorageServiceImpl;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    private UrlStorageServiceImpl urlStorageService;
    private UrlServiceImpl urlService;

    @BeforeEach
    void setUp() {
        urlStorageService = mock(UrlStorageServiceImpl.class);
        urlService = new UrlServiceImpl(urlStorageService);
    }

    @Test
    void generateRandomSlug_shouldReturn6Chars() {
        String slug = urlService.generateRandomSlug("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");

        assertNotNull(slug);
        assertEquals(6, slug.length());
        assertTrue(Pattern.matches("[a-zA-Z0-9]{6}", slug));
    }

    @Test
    void generateShortUrl_shouldReturnSlugAndSaveToStorage() {
        String longUrl = "https://google.com";

        String slug = urlService.generateShortUrl(longUrl);

        assertNotNull(slug);
        assertEquals(6, slug.length());

        ArgumentCaptor<String> slugCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

        verify(urlStorageService, times(1)).save(slugCaptor.capture(), urlCaptor.capture());
        assertEquals(slug, slugCaptor.getValue());
        assertEquals(longUrl, urlCaptor.getValue());
    }

    @Test
    void redirectToUrl_shouldReturnLongUrlFromStorage() {
        String slug = "abc123";
        String longUrl = "https://google.com";

        when(urlStorageService.get(slug)).thenReturn(longUrl);

        String result = urlService.redirectToUrl(slug);

        assertEquals(longUrl, result);
    }

    @Test
    void redirectToUrl_shouldReturnNullIfNotFound() {
        String slug = "unknown";

        when(urlStorageService.get(slug)).thenReturn(null);

        String result = urlService.redirectToUrl(slug);

        assertNull(result);
    }
}
