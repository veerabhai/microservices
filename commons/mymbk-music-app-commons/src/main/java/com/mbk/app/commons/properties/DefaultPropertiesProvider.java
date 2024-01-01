/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.properties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Default implementation of a properties provider where the key is of type {@link String} and value is of type {@link
 * Object}.
 *
 * @author Editor
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true, of = {})
@EqualsAndHashCode(callSuper = true, of = {})
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class DefaultPropertiesProvider extends AbstractPropertiesProvider<String, Object> {
}