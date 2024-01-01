/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of inno-ecom.
 *
 * inno-ecom project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Configuration properties instance that holds the security aspects of the application.
 *
 * @author Editor
 */
@ConfigurationProperties(prefix = "spring")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationProperties {

    /** Datasource jpa related settings. */
    private DatabaseDetails jpa = new DatabaseDetails();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DatabaseDetails {
        /** Driver class settings. */
        private String database;

        /** Authentication related settings. */
        private DatabaseDetailProperties properties = new DatabaseDetailProperties();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DatabaseDetailProperties {

        /** Authentication related settings. */
        private SchemaProperties hibernate = new SchemaProperties();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SchemaProperties {
        /** Driver class settings. */
        private String defaultSchema;

        /** Authentication related settings. */
        private String ddlAuto;
    }
}