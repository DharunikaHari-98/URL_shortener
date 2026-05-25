package com.example.urlshortener.model;

import java.time.LocalDateTime;

public class UrlMapping {

    private String shortCode;
    private String longUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private int clickCount;

    public UrlMapping(String shortCode, String longUrl, LocalDateTime expiresAt) {
        this.shortCode = shortCode;
        this.longUrl = longUrl;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = expiresAt;
        this.clickCount = 0;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void increaseClickCount() {
        this.clickCount++;
    }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }
}