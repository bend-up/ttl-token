package com.github.bendup.ttltoken.token.repository;

import com.github.bendup.ttltoken.token.Token;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

@NoRepositoryBean
public interface TtlReactiveTokenRepository extends TtlReactiveRepository<Token, UUID>,
        ReactiveCrudRepository<Token, UUID> {
}
