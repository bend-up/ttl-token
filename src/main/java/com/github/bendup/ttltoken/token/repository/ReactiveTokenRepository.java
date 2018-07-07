package com.github.bendup.ttltoken.token.repository;

import com.github.bendup.ttltoken.token.Token;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ReactiveTokenRepository extends ReactiveCrudRepository<Token, UUID>{
}