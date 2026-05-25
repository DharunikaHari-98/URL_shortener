package com.example.urlshortener.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UrlShortenerService {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public String shortenUrl(String originalUrl) {
        String code;
        do {
            code = generateCode();
        } while (store.containsKey(code));

        store.put(code, originalUrl);
        return code;
    }

    public String getOriginalUrl(String code) {
        return store.get(code);
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
