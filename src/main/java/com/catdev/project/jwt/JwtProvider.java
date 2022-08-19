package com.catdev.project.jwt;

import com.catdev.project.security.service.UserPrinciple;
import com.catdev.project.util.DateUtil;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Log4j2
public class JwtProvider {

    @Value("${catdev.app.jwtSecret}")
    private String jwtSecret;

    @Value("${catdev.app.jwtExpiration}")
    private int jwtExpiration;

    public JwtProvider() {
    }

    public String generateJwtToken(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        return generateJwtToken(userPrincipal);
    }

    public String generateJwtToken(UserPrinciple userPrincipal) {
        return generateTokenFromEmail(userPrincipal.getEmail());
    }

    public String generateTokenFromEmail(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(DateUtil.convertInstantToDate(Instant.now()))
                .setExpiration(DateUtil.convertInstantToDate(Instant.now().plus(jwtExpiration,ChronoUnit.SECONDS)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Long getRemainTimeFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getExpiration().getTime() - DateUtil.getInstantNow().get(ChronoField.MILLI_OF_SECOND);
    }

    public boolean validateJwtToken(String authToken) {
        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return getRemainTimeFromJwtToken(authToken) > 0;

        } catch (Exception e) {
            log.error("Error validateJwtToken -> Message : ",e);
        }
        return false;
    }
}
