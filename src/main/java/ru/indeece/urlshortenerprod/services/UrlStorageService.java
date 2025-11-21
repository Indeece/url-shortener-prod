package ru.indeece.urlshortenerprod.services;

public interface UrlStorageService {

    public void save(String slug, String longUrl);

    public String get(String slug);

}