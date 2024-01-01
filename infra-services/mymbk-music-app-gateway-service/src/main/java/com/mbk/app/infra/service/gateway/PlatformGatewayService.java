/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.infra.service.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class that is responsible to start the db auth service and exposes the functionality over the specified
 * port.
 *
 * @author Editor
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PlatformGatewayService {

	public static void main(String[] args) {
		SpringApplication.run(PlatformGatewayService.class, args);
	}
}