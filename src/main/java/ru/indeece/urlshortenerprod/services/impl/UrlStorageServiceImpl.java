package ru.indeece.urlshortenerprod.services.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import ru.indeece.urlshortenerprod.services.UrlStorageService;

import java.util.concurrent.TimeUnit;

@Service
public class UrlStorageServiceImpl implements UrlStorageService {

    private final StringRedisTemplate redis;

    private static final long TTL = 60 * 60 * 24 * 30;

    public UrlStorageServiceImpl(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public void save(String slug, String longUrl) {
        redis.opsForValue().set(slug, longUrl, TTL, TimeUnit.SECONDS);
    }

    public String get(String slug) {
        return redis.opsForValue().get(slug);
    }
}
