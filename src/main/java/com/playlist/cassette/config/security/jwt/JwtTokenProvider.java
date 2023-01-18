package com.playlist.cassette.config.security.jwt;

import com.playlist.cassette.dto.auth.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

//    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 7200000; // 2 hour
    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 20000; // 2 hour

    //    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 1209600000; // 14 * 24 * 60 * 60 * 1000 = 2 weeks
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 20000 * 10; // 100 seconds

    private final Environment env;

    public TokenDto generateAccessToken(Authentication authentication) {
        Date expiredTime = getExpirationDate(ACCESS_TOKEN_EXPIRATION_TIME);
        String token = Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setIssuedAt(new Date())
                .setExpiration(expiredTime)
                .signWith(getJwtSecretKey(), SignatureAlgorithm.HS512)
                .compact();
        return TokenDto.builder()
                .value(token)
                .expiredTime(expiredTime)
                .build();
    }

    public TokenDto generateRefreshToken(Authentication authentication) {
        Date expiredTime = getExpirationDate(REFRESH_TOKEN_EXPIRATION_TIME);
        String token = Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate(REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(getJwtSecretKey(), SignatureAlgorithm.HS512)
                .compact();
        return TokenDto.builder()
                .value(token)
                .expiredTime(expiredTime)
                .build();
    }

    public Long getUserFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getJwtSecretKey())
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }

    public JwtValidationType validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getJwtSecretKey()).parseClaimsJws(token);
            return JwtValidationType.VALID_JWT;
        } catch (SignatureException ex) {
            return JwtValidationType.INVALID_JWT_SIGNATURE;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    private SecretKey getJwtSecretKey() {
        String secretKey = env.getProperty("jwt.secret");
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    private Date getExpirationDate(int tokenExpirationTime) {
        Date currentDate = new Date();
        return new Date(currentDate.getTime() + tokenExpirationTime);
    }

}
