package com.github.bendup.ttltoken.token;

import java.util.UUID;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Token {

    @PrimaryKey
    private UUID id;
    private String url;

    private Token() {}

    public static Token fromUrl(String url) {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setUrl(url);
        return token;
    }

    public UUID getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", url=" + url + "]";
    }

    private void setId(UUID id) {
        if (id != null) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Invalid id");
        }
    }

    private void setUrl(String url) {
        if (UrlValidator.getInstance().isValid(url)) {
            this.url = url;
        } else {
            throw new IllegalArgumentException("Invalid url");
        }
    }

}
