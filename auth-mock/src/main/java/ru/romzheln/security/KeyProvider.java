package ru.romzheln.security;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Component
public class KeyProvider {

    private final RSAKey key;

    public KeyProvider() {
        this.key = generateKey();
    }

    public RSAKey getRsaKey(){
        return key;
    }

    private RSAKey generateKey() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            return new RSAKey.Builder((java.security.interfaces.RSAPublicKey) keyPair.getPublic())
                    .privateKey(keyPair.getPrivate())
                    .keyID("auth-key")
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка генерации RSA ключа", e);
        }
    }
}
