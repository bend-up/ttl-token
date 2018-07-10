# Setup

+ Install Cassandra and start it (consider using Docker image)
+ Set proper Cassandra configuration if needed (application should create keyspace and schema automatically)
+ Run application:
```
mvn spring-boot:run
```
+ Enjoy generating TTL tokens:

```
   [m@localhost ttl-token]$ curl -d "url=https://google.pl" http://127.0.0.1:8090/generateToken
   936df19d-100f-4194-b8e5-a542893c5f4a
   [m@localhost ttl-token]$ curl -i http://127.0.0.1:8090/token/936df19d-100f-4194-b8e5-a542893c5f4a
   HTTP/1.1 200 
   Location: https://google.pl
   Content-Length: 0
   Date: Tue, 10 Jul 2018 23:04:03 GMT
   
   [m@localhost ttl-token]$ curl -i http://127.0.0.1:8090/token/936df19d-100f-4194-b8e5-a542893c5f4a
   HTTP/1.1 200 
   Location: https://google.pl
   Content-Length: 0
   Date: Tue, 10 Jul 2018 23:04:24 GMT
   
   [m@localhost ttl-token]$ curl -i http://127.0.0.1:8090/token/936df19d-100f-4194-b8e5-a542893c5f4a
   HTTP/1.1 404 
   Content-Length: 0
   Date: Tue, 10 Jul 2018 23:04:46 GMT   
```

