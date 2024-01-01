/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.security.configuration;

import com.mbk.app.security.properties.ApplicationSecurityProperties;
import com.mbk.app.security.token.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class responsible to configure the security aspects for the microservice /
 * application in consideration.
 *
 * @author Editor
 */
@Slf4j
@Order(SecurityProperties.BASIC_AUTH_ORDER - 2)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Import(value = {JwtTokenProvider.class})
@Configuration
public class WebSecurityConfiguration extends AbstractWebSecurityConfiguration {
    /**
     * Constructor.
     *
     * @param jwtTokenProvider instance of type {@link JwtTokenProvider}.
     * @param userDetailsService instance of type {@link UserDetailsService}.
     * @param applicationSecurityProperties instance of type {@link ApplicationSecurityProperties}.
     * @param passwordEncoder instance of type {@link PasswordEncoder}.
     */
    public WebSecurityConfiguration(
            final JwtTokenProvider jwtTokenProvider,
            final UserDetailsService userDetailsService,
            final ApplicationSecurityProperties applicationSecurityProperties,
            final PasswordEncoder passwordEncoder) {
        super(jwtTokenProvider, userDetailsService, applicationSecurityProperties, passwordEncoder);
    }
}
