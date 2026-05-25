package com.example.urlshortener.dto;

public class ShortenRequest {

    private String longUrl;
    private String customAlias;
    private Long expiryMinutes;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

    public Long getExpiryMinutes() {
        return expiryMinutes;
    }

    public void setExpiryMinutes(Long expiryMinutes) {
        this.expiryMinutes = expiryMinutes;
    }
}