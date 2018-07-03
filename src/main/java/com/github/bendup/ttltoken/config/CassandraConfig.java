package com.github.bendup.ttltoken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${cassandra.contactpoints}") private String contactPoints;
    @Value("${cassandra.port}") private int port;
    @Value("${cassandra.keyspace}") private String keyspace;
    @Value("${cassandra.basepackages}") private String basePackages;

    @Override protected String getKeyspaceName() {
        return keyspace;

    }

    @Override protected String getContactPoints() {
        return contactPoints;
    }

    @Override protected int getPort() {
        return port;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return Arrays.asList(specification);
    }

    @Override public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE;
    }

    @Override public String[] getEntityBasePackages() {
        return new String[] {
                basePackages
        };
    }

}