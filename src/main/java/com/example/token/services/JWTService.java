package com.example.token.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.example.token.constants.Constant.*;

@Service
public class JWTService {
    /**
     * Функция генерации токена по значению
     * @param subject значение по которому будет сгенерирован токен, либо имя пользователя, либо id сессии
     * @return токен в виде String
     */
    public String getTokenBySubject(String subject){
        ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.HOURS);
        String token = Jwts.builder().setSubject(subject)
                .setExpiration(Date.from(expirationTimeUTC.toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return token;
    }

    /**
     * Функция возвращает токен по id сессии и имени пользователя
     * @param sessionId id сессии
     * @param username имя пользователя
     * @return токен
     */
    public String getTokenBySessionIdAndUsername(String sessionId, String username){
        ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.HOURS);
        String token = Jwts.builder().setSubject(username)
                .setId(sessionId)
                .setExpiration(Date.from(expirationTimeUTC.toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return token;
    }

    /**
     * Функция генерирует и возвращает токен, при этом записывает его в сессию
     * @param username имя пользователя
     * @param response http ответ, в сессию которого будет записан токен
     * @return токен в виде String значения
     */
    public String generateTokenByUsername(String username, HttpServletResponse response) throws IOException {
        String token = this.getTokenBySubject(username);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        return token;//+new Date();
    }
}
