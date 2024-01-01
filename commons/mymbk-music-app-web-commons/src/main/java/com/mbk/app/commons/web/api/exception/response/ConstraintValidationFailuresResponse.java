/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.api.exception.response;

import java.util.Collection;
import java.util.LinkedHashSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response object that will be used by the APIs to send the error / exception details back to the client.
 *
 * @author Editor
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConstraintValidationFailuresResponse {
    /** Error code */
    private String errorCode;

    /** Error messages - if more than one error message needs to be associated. */
    @Builder.Default
    private Collection<FieldError> fieldErrors = new LinkedHashSet<>();

    @EqualsAndHashCode(of = {"fieldName"})
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError {
        /** Name of the field that has incorrect value. */
        private String fieldName;

        /** Error message details. */
        private String errorMessage;
    }
}