/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.configuration.properties;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Configuration properties that holds CORS settings.
 *
 * @author Editor
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "com.mbk.app.configuration.security")
public class CORSProperties {
    /** Cors configuration. */
    private Cors cors = new Cors();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Cors {
        /** Cors headers. */
        private CorsHeaders headers = new CorsHeaders();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CorsHeaders {
        /** Allowed origins. */
        private Collection<String> allowedOrigins = new LinkedHashSet<>();

        /** Allowed methods. */
        private Collection<String> allowedMethods = new LinkedHashSet<>();

        /** Allowed methods. */
        private Collection<String> allowedHeaders = new LinkedHashSet<>();

        /** Max age. */
        private long maxAge = 3600L;

        /** Allow credentials. */
        private boolean allowCredentials = true;
    }
}