package com.github.bendup.ttltoken.token;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import java.util.UUID;

public interface TokenRepository extends ReactiveCrudRepository<Token, UUID>{

}