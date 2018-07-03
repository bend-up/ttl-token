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

    public Token() {
    }

    public Token(String url) {
        setId(UUID.randomUUID());
        setUrl(url);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id != null) {
            this.id = id;
        } else {
            throw new IllegalStateException("Invalid id");
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (UrlValidator.getInstance().isValid(url)) {
            this.url = url;
        } else {
            throw new IllegalStateException("Invalid url");
        }
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", url=" + url + "]";
    }
}
