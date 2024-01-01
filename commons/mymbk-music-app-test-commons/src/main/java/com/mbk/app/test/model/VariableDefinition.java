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

import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import com.mbk.app.test.data.DataGenerationStrategy;

/**
 * Implementation class that captures the details of a variable definition.
 *
 * @author Editor
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class VariableDefinition {
    /** Data generation strategy. */
    private DataGenerationStrategy strategy;

    /** Applicable for strategies that deal with Strings. This indicates the length of the string. */
    private int length;

    /** Start value (inclusive). For strings, this indicates the minimum length. */
    private int startInclusive;

    /** End value (exclusive). For strings, this indicates the maximum length. */
    private int endExclusive;

    /** Generated value for this variable in the context of a specific test-case definition. */
    private Object value;

    /**
     * This method returns a boolean indicating if this variable definition uses a data generation strategy or none.
     *
     * @return True if this variable definition uses a data generation strategy i.e. other than {@code
     * DataGenerationStrategy.NONE}.
     */
    public boolean usesDataGenerationStrategy() {
        return Objects.nonNull(strategy) && !strategy.equals(DataGenerationStrategy.NONE);
    }
}