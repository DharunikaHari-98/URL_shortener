package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ShortenRequest;
import com.example.urlshortener.dto.ShortenResponse;
import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@RequestBody ShortenRequest request) {
        try {
            UrlMapping mapping = service.shortenUrl(request);

            ShortenResponse response = new ShortenResponse(
                    "http://localhost:8080/u/" + mapping.getShortCode(),
                    mapping.getShortCode(),
                    mapping.getLongUrl(),
                    mapping.getExpiresAt()
            );

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/u/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = service.getOriginalUrl(code);

        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(302)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping("/analytics/{code}")
    public ResponseEntity<?> analytics(@PathVariable String code) {
        UrlMapping mapping = service.getAnalytics(code);

        if (mapping == null || mapping.isExpired()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapping);
    }
}