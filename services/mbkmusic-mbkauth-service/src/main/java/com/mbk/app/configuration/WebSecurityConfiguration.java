/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of mbkmusic-mbkauth-service.
 *
 * mbkmusic-mbkauth-service project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.configuration;

import com.mbk.app.commons.web.filter.SecurityAuthenticationFilter;
import com.mbk.app.security.token.JwtTokenProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.extern.slf4j.Slf4j;

import com.mbk.app.commons.web.security.AbstractWebSecurityConfigurerAdapter;
import com.mbk.app.security.properties.ApplicationSecurityProperties;

/**
 * Configuration class responsible to configure the security aspects for the microservice / application in
 * consideration.
 *
 * @author Editor
 */
@Slf4j
@Order(SecurityProperties.BASIC_AUTH_ORDER - 2)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends AbstractWebSecurityConfigurerAdapter {
    /**
     * Constructor.
     *
     * @param applicationSecurityProperties
     *         Configurable properties instance of type {@link ApplicationSecurityProperties}.
     * @param securityAuthenticationFilter
     *         Filter instance of type {@link SecurityAuthenticationFilter}.
     */
    public WebSecurityConfiguration(final ApplicationSecurityProperties applicationSecurityProperties,
                                    final SecurityAuthenticationFilter securityAuthenticationFilter, final JwtTokenProvider jwtTokenProvider) {
        super(applicationSecurityProperties, securityAuthenticationFilter,jwtTokenProvider);
    }
}