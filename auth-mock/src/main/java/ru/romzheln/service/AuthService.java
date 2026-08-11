package ru.romzheln.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import ru.romzheln.dto.TokenRequest;
import ru.romzheln.dto.TokenResponse;
import ru.romzheln.security.KeyProvider;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class AuthService {

    private final KeyProvider provider;

    private static final Duration EXPIRATION = Duration.ofHours(1);
    private static final String TOKEN_TYPE = "Bearer";

    public AuthService(KeyProvider provider) {
        this.provider = provider;
    }

    public TokenResponse generateJwt(TokenRequest request)
             {

                 RSAKey key = provider.getRsaKey();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("http://auth-mock:8080")
                .subject(request.name())
                .claim("role", request.role().name())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(EXPIRATION)))
                .build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(key.getKeyID())
                .build();
        SignedJWT jwt = new SignedJWT(header, claims);
                 try {
                     jwt.sign(new RSASSASigner(key.toPrivateKey()));
                 } catch (JOSEException e) {
                     throw new RuntimeException(e);
                 }
                 return new TokenResponse(jwt.serialize(), TOKEN_TYPE, EXPIRATION.getSeconds());
    }

    public Map<String, Object> getKey(){
        return new JWKSet(provider.getRsaKey().toPublicJWK())
                .toJSONObject();
    }
}
