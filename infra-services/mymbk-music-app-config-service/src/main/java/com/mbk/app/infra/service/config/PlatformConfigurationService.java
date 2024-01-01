/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.infra.service.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Main class for the configuration service and provides a centralized way of serving microservice-specific
 * configurations.
 *
 * @author Editor
 */
@EnableConfigServer
@SpringBootApplication
public class PlatformConfigurationService {
    public static void main(final String[] args) {
        SpringApplication.run(PlatformConfigurationService.class, args);
    }
}