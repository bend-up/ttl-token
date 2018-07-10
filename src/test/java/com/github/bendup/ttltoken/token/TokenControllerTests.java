package com.github.bendup.ttltoken.token;

import com.github.bendup.ttltoken.token.repository.TtlReactiveTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest
public class TokenControllerTests {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    TtlReactiveTokenRepository tokenRepository;

    final String url = "http://test.com";
    final Token token = Token.fromUrl(url);
    final String id = token.getId().toString();

    @Test
    public void createTokenTest() {

        given(tokenRepository.saveWithTtl(any(Token.class), anyInt())).willReturn(Mono.just(token));

        String response = webTestClient
                .post()
                .uri(uriBuilder ->
                    uriBuilder.path("/generateToken")
                            .queryParam("url", url)
                            .build()
                )
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .blockFirst()
                .toString();

        assertThat(response).isEqualTo(id);
    }

    @Test
    public void getExistingTokenTest() {

        given(tokenRepository.findById(any(UUID.class))).willReturn(Mono.just(token));

        webTestClient
                .get()
                .uri("/token/" + id)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .valueEquals(HttpHeaders.LOCATION, url)
                .expectBody()
                .isEmpty();
    }

    @Test
    public void getNonExistingTokenTest() {

        given(tokenRepository.findById(any(UUID.class))).willReturn(Mono.empty());

        webTestClient
                .get()
                .uri("/token/" + id)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .isEmpty();
    }

}
