/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.error;

/**
 * Enum constants that represent the common error codes and messages that can be used across the application.
 * <p>
 * For more details, see the documentation on {@link IError} contract.
 *
 * @author Editor
 */
public enum CommonErrors implements IError {
    // NOTE:
    // Whenever a new constant is added here, ensure that the error message for the same constant is added in
    // src/main/resources/l10n/common_error_messages.properties
    DOMAIN_IS_BLACKLISTED,
    DOMAIN_ALREADY_EXISTS,
    DECRYPTION_FAILED,
    ENCRYPTION_FAILED,
    ILLEGAL_ARGUMENT,
    ILLEGAL_ARGUMENT_DETAILED,
    INVALID_CREDENTIALS,
    JSON_SERIALIZATION_FAILED,
    JSON_DESERIALIZATION_FAILED,
    MISSING_KEY_IN_MAP,
    PATTERN_REPLACEMENT_FAILED,
    RESOURCE_NOT_FOUND,
    RESOURCE_NOT_FOUND_DETAILED,
    RESOURCES_NOT_FOUND,
    RESOURCES_NOT_FOUND_DETAILED,
    USER_NOT_AUTHENTICATED,

    GENERIC_ERROR,
    INVALID_PASSWORD,
    USER_ACCOUNT_EXPIRY,
    NOT_APPROVED,
    PLEASE_WAIT_UNTIL_YOUR_ACCOUNT_IS_APPROVED,
    ADMIN_REJECTED,
    REJECTED,

    MISSING_TENANT,
    MISSING_USER_TENANT_ASSOCIATION,

    TENANT_NOT_FOUND,

    ZIP_EXCEPTION,
    FILE_EXTENSION_TYPE_IS_NOT_SUPPORTED_OR_MAXIMUM_FILE_SIZE_IS_EXCEEDED;

    /** Reference to {@link IErrorMessages}, which holds the error messages. */
    private static final ErrorMessages ERROR_MESSAGES = ErrorMessages.instance("l10n/common_error_messages", CommonErrors.class.getClassLoader());

    @Override
    public IErrorMessages getErrorMessages() {
        return CommonErrors.ERROR_MESSAGES;
    }
}