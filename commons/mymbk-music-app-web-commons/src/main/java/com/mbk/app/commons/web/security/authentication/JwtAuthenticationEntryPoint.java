/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

import com.mbk.app.commons.web.filter.SecurityAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import lombok.extern.slf4j.Slf4j;

/**
 * Used by the {@code ExceptionTranslationFilter} to commence authentication via the {@link
 * SecurityAuthenticationFilter}.
 *
 * @author Editor
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /** Error message. */
    private static final String ERROR_MESSAGE = "Authentication failed. Error message: {0}";

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        // Get the exception & log.
        final String exceptionMessage = authException.getMessage();
        JwtAuthenticationEntryPoint.LOGGER.error(exceptionMessage, authException);

        response.sendError(HttpStatus.UNAUTHORIZED.value(), MessageFormat.format(JwtAuthenticationEntryPoint.ERROR_MESSAGE, exceptionMessage));
    }
}