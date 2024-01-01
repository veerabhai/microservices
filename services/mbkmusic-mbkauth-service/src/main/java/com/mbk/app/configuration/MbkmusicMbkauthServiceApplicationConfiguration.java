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

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import com.mbk.app.commons.web.configuration.AuthWebConfiguration;
import com.mbk.app.commons.web.configuration.properties.ApiDocumentationSettings;
import com.mbk.app.commons.web.configuration.properties.ApplicationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class responsible to configure the beans, etc. for the microservice that contains this configuration.
 *
 * @author Editor
 */
@Import(value = {AuthWebConfiguration.class})
@PropertySource("classpath:/l10n/ValidationMessages.properties")
@PropertySource("classpath:/l10n/error_messages.properties")
@EnableJpaRepositories(basePackages = {"com.mbk.app.features.platform.data.repository"})
@EntityScan(basePackages = {"com.mbk.app.features.platform.data.model.persistence"})

@Configuration
public class MbkmusicMbkauthServiceApplicationConfiguration {
    /**
     * Bean that captures the API documentation settings.
     *
     * @return Bean instance of type {@link ApiDocumentationSettings}.
     */
    @ConfigurationProperties(prefix = "com.mbk.app.configuration")
    @Bean
    public ApiDocumentationSettings apiDocumentationSettings() {
        return new ApiDocumentationSettings();
    }

    /**
     * Bean that captures the application security settings.
     *
     * @return Bean instance of type {@link ApplicationProperties}.
     */
    @ConfigurationProperties(prefix = "spring")
    @Bean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }
}