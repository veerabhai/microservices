/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.configuration;

import com.mbk.app.commons.web.api.client.DefaultRestClient;
import com.mbk.app.security.properties.ApplicationSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mbk.app.commons.web.FeatureScanner;
import com.mbk.app.commons.web.filter.SecurityAuthenticationFilter;
import com.mbk.app.security.access.IPermissionEvaluator;
import com.mbk.app.security.access.UserPermissionEvaluator;
import com.mbk.app.security.token.JwtTokenProvider;

/**
 * Configuration class that creates the necessary beans and enables the component scan for the packages that this module
 * is responsible for in case of security enabled.
 *
 * @author Editor
 */
@ComponentScan(basePackageClasses = {FeatureScanner.class})
@EnableConfigurationProperties(value = {ApplicationSecurityProperties.class})
@Configuration
public class AuthWebConfiguration{
    /** Number of rounds of hashing to apply and used by {@link BCryptPasswordEncoder}. */
    private static final int NUMBER_OF_ROUNDS_FOR_HASHING = 4;

    /**
     * This method creates a singleton bean instance of type {@link BCryptPasswordEncoder} using the strength as defined
     * by {@code WebSecurityConfiguration.NUMBER_OF_ROUNDS_FOR_HASHING}.
     *
     * @return Singleton bean instance of type {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(AuthWebConfiguration.NUMBER_OF_ROUNDS_FOR_HASHING);
    }

    /**
     * This method creates a bean instance of type {@link UserPermissionEvaluator}, which can be used in the @{@link
     * org.springframework.security.access.prepost.PreAuthorize} annotation for permission evaluation.
     * <p>
     * Example: @PreAuthorize("@userPermissionEvaluator.hasPermission('CREATE_USER')")
     *
     * @return Permission evaluator instance of type {@link IPermissionEvaluator}.
     */
    @Bean
    public IPermissionEvaluator userPermissionEvaluator() {
        return new UserPermissionEvaluator();
    }

    /**
     * This method creates a bean instance of type {@link JwtTokenProvider}, which provides functionality for JWT token
     * generation, token validation, etc.
     *
     * @param applicationSecurityProperties
     *         Configuration properties instance of type {@link ApplicationSecurityProperties}.
     *
     * @return JWT token provider instance of type {@link JwtTokenProvider}.
     */
    @Bean
    public JwtTokenProvider jwtTokenProvider(final ApplicationSecurityProperties applicationSecurityProperties) {
        return new JwtTokenProvider(applicationSecurityProperties);
    }

    /**
     * This method creates a bean of type {@link SecurityAuthenticationFilter} and configured to be called before {@link
     * UsernamePasswordAuthenticationFilter}.
     *
     * @param jwtTokenProvider
     *         JWT Token provider instance of type {@link JwtTokenProvider}.
     * @param userDetailsService
     *         Service instance of type {@link UserDetailsService}.
     * @param defaultRestClient
     *         Rest Client instance of type {@link DefaultRestClient}.
     * @param applicationSecurityProperties
     *         Properties instance of type {@link ApplicationSecurityProperties}.
     *
     * @return Bean instance of type {@link SecurityAuthenticationFilter}.
     */
    @Bean
    public SecurityAuthenticationFilter jwtAuthenticationFilter(final JwtTokenProvider jwtTokenProvider,
                                                                final UserDetailsService userDetailsService,
                                                                final DefaultRestClient defaultRestClient,
                                                                final ApplicationSecurityProperties applicationSecurityProperties) {
        return new SecurityAuthenticationFilter(jwtTokenProvider,userDetailsService,defaultRestClient,applicationSecurityProperties);
    }
}