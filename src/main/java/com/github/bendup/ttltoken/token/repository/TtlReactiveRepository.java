package com.github.bendup.ttltoken.token.repository;

import reactor.core.publisher.Mono;

public interface TtlReactiveRepository<T, ID> {

    <S extends T> Mono<S> saveWithTtl(S entity, Integer ttl);

}
