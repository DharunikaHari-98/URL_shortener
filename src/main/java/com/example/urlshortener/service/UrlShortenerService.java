package com.example.urlshortener.service;

import com.example.urlshortener.dto.ShortenRequest;
import com.example.urlshortener.model.UrlMapping;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UrlShortenerService {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    private final Map<String, UrlMapping> store = new ConcurrentHashMap<>();

    public UrlMapping shortenUrl(ShortenRequest request) {
        validateUrl(request.getLongUrl());

        String code;

        if (request.getCustomAlias() != null && !request.getCustomAlias().isBlank()) {
            code = request.getCustomAlias();

            if (store.containsKey(code)) {
                throw new IllegalArgumentException("Custom alias already exists");
            }
        } else {
            do {
                code = generateCode();
            } while (store.containsKey(code));
        }

        LocalDateTime expiresAt = null;

        if (request.getExpiryMinutes() != null && request.getExpiryMinutes() > 0) {
            expiresAt = LocalDateTime.now().plusMinutes(request.getExpiryMinutes());
        }

        UrlMapping mapping = new UrlMapping(code, request.getLongUrl(), expiresAt);
        store.put(code, mapping);

        return mapping;
    }

    public String getOriginalUrl(String code) {
        UrlMapping mapping = store.get(code);

        if (mapping == null) {
            return null;
        }

        if (mapping.isExpired()) {
            store.remove(code);
            return null;
        }

        mapping.increaseClickCount();
        return mapping.getLongUrl();
    }

    public UrlMapping getAnalytics(String code) {
        return store.get(code);
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("URL must start with http:// or https://");
        }
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(
                    BASE62.charAt(ThreadLocalRandom.current().nextInt(BASE62.length()))
            );
        }

        return sb.toString();
    }
}