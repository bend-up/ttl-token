package com.github.bendup.ttltoken.token;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
public class TokenTests {

    @Test
    public void tokenGetsCreatedTest() {
        final String url = "http://test.com";
        Token token = Token.fromUrl(url);

        assertThat(token).isNotNull();
        assertThat(token.getId()).isNotNull();
        assertThat(token.getUrl()).isNotBlank();
        assertThat(token.getUrl()).isEqualTo(url);
    }

    @Test
    public void tokenRequiresValidUrlTest() {
        assertThatIllegalArgumentException().isThrownBy(() -> Token.fromUrl(""));
        assertThatIllegalArgumentException().isThrownBy(() -> Token.fromUrl(null));
        assertThatIllegalArgumentException().isThrownBy(() -> Token.fromUrl("test"));
        assertThatIllegalArgumentException().isThrownBy(() -> Token.fromUrl("http:/test.com"));
        assertThatIllegalArgumentException().isThrownBy(() -> Token.fromUrl("http://"));
        assertThatIllegalArgumentException().isThrownBy(() -> Token.fromUrl("http:///test.com"));
        assertThatIllegalArgumentException().isThrownBy(() -> Token.fromUrl("http://test"));
    }

}
