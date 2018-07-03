package com.github.bendup.ttltoken.token;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TokenController {

    @Autowired
    TokenRepository tokenRepository;

    @PostMapping("/generateToken")
    public Mono<UUID> createToken(@RequestParam("url") String url) {
        return tokenRepository
                .save(Token.fromUrl(url))
                .map(savedToken -> savedToken.getId());
    }

    @GetMapping("/token/{id}")
    public Mono<ResponseEntity<Object>> findById(@PathVariable("id") UUID id) {
        return tokenRepository.findById(id)
                .map(savedToken -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.LOCATION, savedToken.getUrl());
                    return headers;
                })
                .map(headers -> new ResponseEntity<>(headers, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}