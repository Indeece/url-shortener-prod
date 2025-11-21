package ru.indeece.urlshortenerprod.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.indeece.urlshortenerprod.services.UrlService;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlStorageServiceImpl urlStorageService;

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz" +
                                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                                    "0123456789";

    @Override
    public String generateRandomSlug(String ALPHABET) {

        StringBuilder slug = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 6; ++i) {
            slug.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return slug.toString();
    }

    @Override
    public String generateShortUrl(String url) {
        String slug = generateRandomSlug(ALPHABET);
        urlStorageService.save(slug, url);
        return slug;
    }

    @Override
    public String redirectToUrl(String slug) {
        String longUrl = urlStorageService.get(slug);
        return longUrl;
    }
}
