/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.token;

import com.mbk.app.security.properties.ApplicationSecurityProperties;
import com.mbk.app.security.userdetails.UserPrincipal;
import com.mbk.app.security.utils.AuthenticationUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * This class provides the functionality for generating JWT tokens, validating the JWT tokens, etc. This is generally
 * called during the request processing.
 *
 * @author Editor
 */
@Slf4j
public class JwtTokenProvider {
    /** Properties instance that holds the JWT token configuration details. */
    private final ApplicationSecurityProperties applicationSecurityProperties;

    /**
     * Constructor.
     *
     * @param applicationSecurityProperties
     *         Properties instance that holds the JWT token configuration details.
     */
    public JwtTokenProvider(final ApplicationSecurityProperties applicationSecurityProperties) {
        this.applicationSecurityProperties = applicationSecurityProperties;
    }

    /**
     * For the provided {@link Authentication} instance, this method generates a JWT token.
     *
     * @param authentication
     *         Authentication instance that holds the current logged-in user (i.e. the user principal who has
     *         logged-in).
     *
     * @return JWT token.
     */
    public String generateToken(final Authentication authentication) {
        final ApplicationSecurityProperties.AuthToken token = applicationSecurityProperties.getAuth().getToken();
        final Date currentDate = new Date();
        final Date tokenExpirationDate = DateUtils.addMilliseconds(currentDate, token.getExpirationIntervalInHours() * 60 * 60 * 1000);

        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
       

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(tokenExpirationDate)
                .signWith(SignatureAlgorithm.HS512, token.getSecret())
                .compact();
    }

    /**
     * This method attempts to validate the provided JWT token.
     *
     * @param token
     *         JWT token that needs to be validated.
     *
     * @return True if the JWT token is a valid one, false otherwise.
     */
    public boolean isTokenValid(final String token) {
        boolean verdict = false;
        try {
            final ApplicationSecurityProperties.AuthToken authToken = applicationSecurityProperties.getAuth().getToken();
            Jwts.parser().setSigningKey(authToken.getSecret()).parseClaimsJws(token);
            verdict = true;
        } catch (final IllegalArgumentException | SignatureException | MalformedJwtException | UnsupportedJwtException | ExpiredJwtException ex) {
            JwtTokenProvider.LOGGER.error(ex.getMessage(), ex);
        }
        return verdict;
    }

    /**
     * This method attempts to parse the JWT token and extracts the Subject from the claims.
     *
     * @param token
     *         JWT Token from which the subject needs to be extracted.
     *
     * @return Unique identifier of the subject (i.e. user principal in question).
     */
    public String getSubjectForToken(final String token) {
        final ApplicationSecurityProperties.AuthToken authToken = applicationSecurityProperties.getAuth().getToken();

        final Claims claims = Jwts.parser()
                .setSigningKey(authToken.getSecret())
                .parseClaimsJws(token)
                .getBody();
//        SecurityContextHolder.setStrategy   Name(claims.getSubject());
//        SecurityContextHolder.getContextHolderStrategy().
        return claims.getSubject();
    }
}