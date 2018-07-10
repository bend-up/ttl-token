package com.github.bendup.ttltoken.token.repository.cassandra;

import com.github.bendup.ttltoken.token.Token;
import com.github.bendup.ttltoken.token.repository.ReactiveTokenRepository;
import com.github.bendup.ttltoken.token.repository.TtlReactiveTokenRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class CassandraTtlReactiveTokenRepository implements TtlReactiveTokenRepository {

    private ReactiveCassandraOperations reactiveCassandraOperations;
    private ReactiveTokenRepository reactiveTokenRepository;

    @Autowired
    public CassandraTtlReactiveTokenRepository(ReactiveCassandraOperations reactiveCassandraOperations,
                                               ReactiveTokenRepository reactiveTokenRepository) {
        this.reactiveCassandraOperations = reactiveCassandraOperations;
        this.reactiveTokenRepository = reactiveTokenRepository;

    }

    @Override
    public <S extends Token> Mono<S> saveWithTtl(S entity, Integer ttl) {

        Assert.notNull(entity, "Entity must not be null");
        Assert.notNull(ttl, "Ttl must not be null");
        Assert.isTrue(ttl.compareTo(0) == 1, "Ttl must be greater that 0");

        return reactiveCassandraOperations.
                insert(entity, InsertOptions.builder().ttl(ttl).build())
                .thenReturn(entity);
    }

    @Override
    public <S extends Token> Mono<S> save(S entity) {
        return reactiveTokenRepository.save(entity);
    }

    @Override
    public <S extends Token> Flux<S> saveAll(Iterable<S> entities) {
        return reactiveTokenRepository.saveAll(entities);
    }

    @Override
    public <S extends Token> Flux<S> saveAll(Publisher<S> entityStream) {
        return reactiveTokenRepository.saveAll(entityStream);
    }

    @Override
    public Mono<Token> findById(UUID uuid) {
        return reactiveTokenRepository.findById(uuid);
    }

    @Override
    public Mono<Token> findById(Publisher<UUID> id) {
        return reactiveTokenRepository.findById(id);
    }

    @Override
    public Mono<Boolean> existsById(UUID uuid) {
        return reactiveTokenRepository.existsById(uuid);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<UUID> id) {
        return reactiveTokenRepository.existsById(id);
    }

    @Override
    public Flux<Token> findAll() {
        return reactiveTokenRepository.findAll();
    }

    @Override
    public Flux<Token> findAllById(Iterable<UUID> uuids) {
        return reactiveTokenRepository.findAllById(uuids);
    }

    @Override
    public Flux<Token> findAllById(Publisher<UUID> idStream) {
        return reactiveTokenRepository.findAllById(idStream);
    }

    @Override
    public Mono<Long> count() {
        return reactiveTokenRepository.count();
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return reactiveTokenRepository.deleteById(uuid);
    }

    @Override
    public Mono<Void> deleteById(Publisher<UUID> id) {
        return reactiveTokenRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Token entity) {
        return reactiveTokenRepository.delete(entity);
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends Token> entities) {
        return reactiveTokenRepository.deleteAll(entities);
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends Token> entityStream) {
        return reactiveTokenRepository.deleteAll(entityStream);
    }

    @Override
    public Mono<Void> deleteAll() {
        return reactiveTokenRepository.deleteAll();
    }

}
