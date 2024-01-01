/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import com.mbk.app.commons.Name;
import com.mbk.app.commons.SpecialCharacter;
import com.mbk.app.commons.web.api.client.DefaultRestClient;
import com.mbk.app.commons.web.api.client.RestApi;
import com.mbk.app.commons.web.utils.AuthHttpUtils;
import com.mbk.app.security.properties.ApplicationSecurityProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

import com.mbk.app.security.token.JwtTokenProvider;
import com.mbk.app.security.token.TokenType;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.mbk.app.commons.web.utils.AuthHttpUtils;

/**
 * A filter that gets called before {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 * and is responsible to validate the JWT token.
 *
 * @author Editor
 */
@Slf4j
public class SecurityAuthenticationFilter extends OncePerRequestFilter {
    /** JWT token provider that is responsible for token generation, validation, etc. */
    private final JwtTokenProvider jwtTokenProvider;

    /** Default Rest Client{@link DefaultRestClient} .*/
    private final DefaultRestClient defaultRestClient;

    /** Properties file {@link ApplicationSecurityProperties}. */
    private final ApplicationSecurityProperties applicationSecurityProperties;

    /** Service implementation of type {@link UserDetailsService}. */
    private final UserDetailsService userDetailsService;


    /**
     * Constructor.
     *
     * @param jwtTokenProvider
     *         JWT token provider that is responsible for token generation, validation, etc.
     * @param userDetailsService
     *         Service implementation of type {@link UserDetailsService}.
     * @param applicationSecurityProperties
     *         Service implementation of type {@link ApplicationSecurityProperties}.
     */
    public SecurityAuthenticationFilter(final JwtTokenProvider jwtTokenProvider,
                                        final UserDetailsService userDetailsService,
                                        final DefaultRestClient defaultRestClient,
                                        final ApplicationSecurityProperties applicationSecurityProperties) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.defaultRestClient = defaultRestClient;
        this.applicationSecurityProperties = applicationSecurityProperties;
    }

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request,
                                    @NonNull final HttpServletResponse response,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {
        try {
            final String bearerToken = AuthHttpUtils.extractTokenFromAuthorizationHeader(request, TokenType.BEARER);
            if (StringUtils.isNotBlank(bearerToken) && jwtTokenProvider.isTokenValid(bearerToken)) {
                // TODO: Convert the token to UserDetails (thereby avoiding database trips).
                final String username = jwtTokenProvider.getSubjectForToken(bearerToken);
                if (Objects.nonNull(username) && !username.equals(StringUtils.EMPTY)) {
                    // Build the Rest Api instance with the required details.
                    final RestApi validateTokenApi = RestApi.instance()
                            .authorization(bearerToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .requestMethod(HttpMethod.GET)
                            .header(Name.TAG_TENANT.key(),request.getHeader(Name.TAG_TENANT.key()))
                            .header(Name.ORIGIN.key(),request.getHeader(Name.ORIGIN.key()))
                            .url(applicationSecurityProperties.getAuth().getValidateTokenUrl().getUrl());

                    try {
                        // Invoke the Rest Api.
                        final ResponseEntity<Map<String, Object>> authResponse = defaultRestClient.invoke(validateTokenApi, new ParameterizedTypeReference<Map<String, Object>>() {
                        });


                        // Validate the response and return.
                        if (authResponse.getStatusCode().is2xxSuccessful() && authResponse.hasBody()) {
                            ArrayList authority = (ArrayList) authResponse.getBody().get(Name.AUTHORITIES.key());
                            LinkedHashMap authorityMap = (LinkedHashMap) authority.get(0);
                            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                            if (Objects.nonNull(authorityMap)) {
                                final String authorityValues = (String) authorityMap.get(Name.AUTHORITY.key());
                                if (!authorityValues.isEmpty()) {
                                    final String[] authorityData = authorityValues.split(SpecialCharacter.COMMA.getValue());
                                    if (Objects.nonNull(authorityData) && ObjectUtils.isNotEmpty(authorityData))
                                        for (String authorityValue : authorityData) {
                                            grantedAuthorities.add(new SimpleGrantedAuthority(authorityValue));
                                        }
                                }

                            }
                            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authResponse.getBody().get(Name.USERNAME.key()), null, grantedAuthorities);
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);

                        }
                    } catch (final Exception ex) {
                        SecurityAuthenticationFilter.LOGGER.error(ex.getMessage());
                        if (SecurityAuthenticationFilter.LOGGER.isDebugEnabled()) {
                            SecurityAuthenticationFilter.LOGGER.error(ex.getMessage(), ex);
                        }
                    }
                }
            }
        } catch (final Exception ex) {
            SecurityAuthenticationFilter.LOGGER.error(ex.getMessage());
            // If debug is enabled, print the trace.
            if (SecurityAuthenticationFilter.LOGGER.isDebugEnabled()) {
                SecurityAuthenticationFilter.LOGGER.error(ex.getMessage(), ex);
            }
        }
        filterChain.doFilter(request, response);
    }
}