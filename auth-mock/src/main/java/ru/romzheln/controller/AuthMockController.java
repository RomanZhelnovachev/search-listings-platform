package ru.romzheln.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.web.bind.annotation.*;
import ru.romzheln.dto.TokenRequest;
import ru.romzheln.dto.TokenResponse;
import ru.romzheln.security.KeyProvider;
import ru.romzheln.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthMockController {

    private final AuthService service;

    public AuthMockController(AuthService service) {
        this.service = service;
    }

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> keys() {
        return service.getKey();
    }

    @PostMapping("/jwt")
    public TokenResponse createJwt(@RequestBody TokenRequest request) {
        return service.generateJwt(request);
    }
}
