package com.example.urlshortener.dto;

import java.time.LocalDateTime;

public class ShortenResponse {

    private String shortUrl;
    private String shortCode;
    private String longUrl;
    private LocalDateTime expiresAt;

    public ShortenResponse(String shortUrl, String shortCode, String longUrl, LocalDateTime expiresAt) {
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.longUrl = longUrl;
        this.expiresAt = expiresAt;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}