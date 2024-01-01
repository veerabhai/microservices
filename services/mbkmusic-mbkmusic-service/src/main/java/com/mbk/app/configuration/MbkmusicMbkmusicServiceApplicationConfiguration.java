/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of mbkmusic-mbkmusic-service.
 *
 * mbkmusic-mbkmusic-service project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.configuration;

import com.mbk.app.commons.web.configuration.*;
import com.mbk.app.commons.web.configuration.properties.*;

import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

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
public class MbkmusicMbkmusicServiceApplicationConfiguration {
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