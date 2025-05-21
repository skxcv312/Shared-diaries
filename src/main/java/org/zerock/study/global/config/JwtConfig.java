package org.zerock.study.global.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {
    private final Key secretKey;
    private final long accessTokenValidTime = 30 * 24 * 60 * 60 * 1000L; // 1h
    private final long refreshTokenValidTime = 24 * 60 * 60 * 1000L; //1 day

    public JwtConfig() {
        String secret = "W91ci0zMi1ieXRlLXNlY3JldC1rZXktZm9yLUpXVDo1415fsdfhkwf1552";
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

}
