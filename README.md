# Setup

+ Install Cassandra
+ Create keyspace in cqls:

```
create keyspace ttltoken with replication={'class':'SimpleStrategy', 'replication_factor':1};
```

