/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.db.service;

import com.mbk.app.commons.instrumentation.Instrument;
import com.mbk.app.security.token.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation that provides the capabilities for validating the bearer token.
 *
 * @author Editor
 */
@Slf4j
@Service
public class TokenValidationService {
    /** Service implementation of type {@link UserDetailsService}. */
    private final UserDetailsService userDetailsService;

    /** JWT token provider that is responsible for token generation, validation, etc. */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor.
     *
     * @param userDetailsService
     *      Service instance of type {@link UserDetailsService}.
     * @param jwtTokenProvider
     *      Service instance of type {@link JwtTokenProvider}.
     */
    public TokenValidationService(final UserDetailsService userDetailsService, final JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Instrument
    @Transactional(readOnly = true)
    public UserDetails validateToken(final String accessToken) {
        try {
            if (StringUtils.isNotBlank(accessToken) && jwtTokenProvider.isTokenValid(accessToken)) {
                final String username = jwtTokenProvider.getSubjectForToken(accessToken);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return userDetails;
            }
        } catch (final Exception ex) {
            TokenValidationService.LOGGER.error(ex.getMessage());
            // If debug is enabled, print the trace.
            if (TokenValidationService.LOGGER.isDebugEnabled()) {
                TokenValidationService.LOGGER.error(ex.getMessage(), ex);
            }
        }
        return null;
    }
}