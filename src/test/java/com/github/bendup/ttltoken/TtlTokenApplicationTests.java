package com.github.bendup.ttltoken;

import com.github.bendup.ttltoken.token.repository.TtlReactiveTokenRepository;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.cassandraunit.spring.CassandraUnit;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionIntegrationTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.Duration;

import static io.restassured.RestAssured.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ComponentScan
@ContextConfiguration
@TestExecutionListeners({ CassandraUnitDependencyInjectionIntegrationTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class })
@TestPropertySource(locations="classpath:test.properties")
@CassandraUnit
public class TtlTokenApplicationTests {

	@Value("${local.server.port}")
	private int port;
	@Value("${token.ttl}")
	private int ttl;

	@Autowired
	TtlReactiveTokenRepository tokenRepository;


	@Test
	public void createsToken() throws InterruptedException {

		final String url = "http://www.google.com";
		final String api = "http://localhost:" + port;

		given()
				.baseUri(api).
		when()
				.get( "/token/12345678-0000-0000-0000-123456789abc").
		then()
				.log().all()
				.statusCode(HttpStatus.SC_NOT_FOUND);

		final String id =
		given()
				.baseUri(api)
				.queryParam("url", url).
		when()
				.post("/generateToken").
		then()
				.log().all()
				.statusCode(HttpStatus.SC_OK)
				.extract().body().asString();


		given()
				.baseUri(api).
		when()
				.get("/token/" + id).
		then()
				.log().all()
				.statusCode(HttpStatus.SC_OK)
				.header(HttpHeaders.LOCATION, url);


		Thread.sleep(Duration.ofSeconds(ttl).toMillis());


		given()
				.baseUri(api).
		when()
				.get( "/token/" + id).
		then()
				.log().all()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}

}
