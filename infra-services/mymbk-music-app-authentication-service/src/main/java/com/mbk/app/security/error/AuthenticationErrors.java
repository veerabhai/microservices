/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.error;

import com.mbk.app.commons.error.IError;
import com.mbk.app.commons.error.ErrorMessages;
import com.mbk.app.commons.error.IErrorMessages;
/**
 * Enum constants that represent the authentication error codes and messages that can be used across the authentication
 * service.
 * <p>
 * For more details, see the documentation on {@link IError} contract.
 *
 * @author Editor
 */
public enum AuthenticationErrors implements IError {
    // NOTE:
    // Whenever a new constant is added here, ensure that the error message for the same constant is added in
    // src/main/resources/l10n/authentication_error_messages.properties

    INVALID_CREDENTIALS;

    /** Reference to {@link IErrorMessages}, which holds the error messages. */
    private static final ErrorMessages ERROR_MESSAGES = ErrorMessages.instance("l10n/authentication_error_messages", AuthenticationErrors.class.getClassLoader());

    @Override
    public IErrorMessages getErrorMessages() {
        return null;
    }
}