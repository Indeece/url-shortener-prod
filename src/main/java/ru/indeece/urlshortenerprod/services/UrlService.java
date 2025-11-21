package ru.indeece.urlshortenerprod.services;

public interface UrlService {

    public String generateRandomSlug(String alphabet);

    public String generateShortUrl(String url);

    public String redirectToUrl(String slug);

}
