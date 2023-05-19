/**
 * Classe utilitaria per jwt
 * @author Tornaghi Omar
 * @version 1.0
 */
package com.mondorevive.TRESPOT.utils;

import com.mondorevive.TRESPOT.exceptions.InvalidJwtException;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final Key hmacKey = loadJwtSecretKeyFromResources();
    private final int jwtExpirationMs = 43200000;
    public String generateJwtToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(hmacKey)
                .compact();
    }

    public String parseUsernameFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String parseJwt(String headerAuth) {
        try {
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7);
            }
        }
        catch (Exception ex) { throw new InvalidJwtException(); }
        return null;
    }

    public String getUsername(String token){
        return parseUsernameFromJwt(parseJwt(token));
    }

    private Key loadJwtSecretKeyFromResources(){
        final String PERCORSO_FILE_SECRET_KEY = "static/security/jwt_secret_key.txt";
        try {
            InputStream inputStream = ResourceUtils.getFileFromResourceAsStream(PERCORSO_FILE_SECRET_KEY);
            byte[] bytes = inputStream.readAllBytes();
            Base64 base64 = new Base64();
            return new SecretKeySpec(base64.decode(bytes),
                    SignatureAlgorithm.HS512.getJcaName());
        }
        catch (IOException ex){ ex.printStackTrace();}
        return null;
    }
}
