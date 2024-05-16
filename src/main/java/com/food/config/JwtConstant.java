package com.food.config;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtConstant {
    public static final String SECRET_KEY = Base64.getEncoder().encodeToString(
            Keys.secretKeyFor(SignatureAlgorithm.HS384).getEncoded()
    );
    public static final String JWt_HEADER = "Authorization";
}
