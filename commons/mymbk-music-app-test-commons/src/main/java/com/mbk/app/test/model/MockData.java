/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.test.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * An implementation of an experience model that captures mock data (input and output) when few layers are mocked as
 * part of unit testing.
 * <p>
 * For example: When mocking repository layer while writing unit tests for Service layer, this class can be used to mock
 * the input and output for repository methods. tests.
 *
 * @author Editor
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MockData {
    /** Mocked input data for this area. */
    private TestData input;

    /** Mocked output data for this area. */
    private TestData output;
}