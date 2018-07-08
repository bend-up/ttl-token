package com.github.bendup.ttltoken.token.repository.cassandra;

import com.github.bendup.ttltoken.token.Token;
import com.github.bendup.ttltoken.token.repository.ReactiveTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.WriteResult;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CassandraTtlReactiveTokenRepositoryTests {

    @Autowired
    private CassandraTtlReactiveTokenRepository cassandraTtlReactiveTokenRepository;
    @MockBean
    private ReactiveCassandraOperations reactiveCassandraOperations;
    @MockBean
    private ReactiveTokenRepository reactiveTokenRepository;

    final String url = "http://test.com";
    final Token token = Token.fromUrl(url);
    final UUID id = token.getId();

    @Test
    public void saveWithTtlTest() {

        given(reactiveCassandraOperations
                .insert(any(Token.class), any(InsertOptions.class))
        ).willReturn(Mono.just(mock(WriteResult.class)));

        Token resultToken = cassandraTtlReactiveTokenRepository.saveWithTtl(token, 30).block();

        assertThat(resultToken).isNotNull();
        assertThat(resultToken.getId()).isEqualTo(id);
        assertThat(resultToken.getUrl()).isEqualTo(url);
    }

    @Test
    public void saveWithTtlRequiresValidArgumentsTest() {
        assertThatIllegalArgumentException().isThrownBy(() -> cassandraTtlReactiveTokenRepository.saveWithTtl(null, 10));
        assertThatIllegalArgumentException().isThrownBy(() -> cassandraTtlReactiveTokenRepository.saveWithTtl(null, null));
        assertThatIllegalArgumentException().isThrownBy(() -> cassandraTtlReactiveTokenRepository.saveWithTtl(token, null));
        assertThatIllegalArgumentException().isThrownBy(() -> cassandraTtlReactiveTokenRepository.saveWithTtl(token, 0));
        assertThatIllegalArgumentException().isThrownBy(() -> cassandraTtlReactiveTokenRepository.saveWithTtl(token, -1));
        assertThatIllegalArgumentException().isThrownBy(() -> cassandraTtlReactiveTokenRepository.saveWithTtl(token, Integer.MIN_VALUE));
    }

}
