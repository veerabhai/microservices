/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Configuration properties instance that holds the security aspects of the application.
 *
 * @author Editor
 */
@ConfigurationProperties(prefix = "com.mbk.app.configuration.security")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationSecurityProperties {
    /** Authentication related settings. */
    private Authentication auth = new Authentication();

    /** Endpoints security. */
    private Endpoints endpoints = new Endpoints();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Endpoints {
        /** Collection of unsecured endpoints. */
        private Collection<String> unsecured = new LinkedHashSet<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Authentication {
        /** Token related settings. */
        private AuthToken token = new AuthToken();

        /** Validate Token url is used for fetching the url of the API to validate the token. */
        private ValidateTokenUrl validateTokenUrl = new ValidateTokenUrl();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AuthToken {
        /** Secret that is used for signing the token. */
        private String secret;

        /** JWT Token expiration interval in milliseconds. Defaulted to 7 days. */
        private Integer expirationIntervalInHours = 7 * 24;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ValidateTokenUrl {
        /** Url of the validate token API. */
        private String url;
    }
}