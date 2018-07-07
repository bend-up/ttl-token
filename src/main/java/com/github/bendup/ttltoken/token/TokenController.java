package com.github.bendup.ttltoken.token;

import java.util.UUID;

import com.github.bendup.ttltoken.token.repository.TtlReactiveTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    TtlReactiveTokenRepository tokenRepository;

    @Value("${token.ttl}")
    private Integer ttl;

    @PostMapping("/generateToken")
    public Mono<UUID> createToken(@RequestParam("url") String url) {
        return tokenRepository
                .saveWithTtl(Token.fromUrl(url), ttl)
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