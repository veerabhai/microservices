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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation class that is responsible to load the resource bundle and provides the capability to read the entries
 * from the resource bundle. This class is primarily meant for loading error messages configured in the resource
 * bundles.
 * <p>
 * These messages primarily take the below form:
 * <p>
 * [Error Code]=[Error Message]
 * <p>
 * Example:
 * <p>
 * GENERIC_ERROR=A problem was encountered while processing your request. Please try after some time.
 * USER_NOT_FOUND=Unable to find an user with username {0}.
 * <p>
 * The error codes are defined in an enumerated data type (typically an enum that implements {@link IError} and this
 * implementation gets used in the {@link IError} implementations to load the messages for a specific message / error
 * code. Please see {@link IError} for details.
 *
 * @author Editor
 */
@Slf4j
public final class ErrorMessages implements IErrorMessages {
    /** Reference to the resource bundle name that holds the error messages. */
    public static final String ERROR_MESSAGES_RESOURCE_BUNDLE_NAME = "error_messages";

    /**
     * Reference to a map of resource bundles where the key is a combination of bundleName and locale name
     * (error_messages.en_US) and the value is the ResourceBundle object for that locale.
     */
    private final Map<String, ResourceBundle> errorMessageBundles = new ConcurrentHashMap<>();

    /**
     * Constructor.
     *
     * @param bundleName
     *         Resource bundle name without the .properties extension.
     * @param classLoader
     *         Class loader.
     * @param locales
     *         Collection of locales to be considered while loading the resource bundle entries.
     */
    private ErrorMessages(final String bundleName, final ClassLoader classLoader, final Collection<Locale> locales) {
        // Private constructor to prevent creation of instances of this class.
        final String bundleNameToUse = StringUtils.isBlank(bundleName)
                ? ErrorMessages.ERROR_MESSAGES_RESOURCE_BUNDLE_NAME
                : bundleName;

        // Loop through the locales and load the bundles for that locale one at a time.
        Optional.ofNullable(locales)
                .orElse(Collections.singleton(LocaleContextHolder.getLocale()))
                .stream()
                .filter(Objects::nonNull)
                .forEach(locale -> {
                    final String forLocale = locale.toString();
                    final ResourceBundle rb = ErrorMessages.loadResourceBundle(bundleNameToUse, locale, classLoader);
                    if (Objects.nonNull(rb)) {
                        // Successfully loaded the bundle. Add this bundle to the list that we are maintaining.
                        ErrorMessages.LOGGER.debug("Loaded resource bundle {} for locale {}", bundleName, forLocale);
                        errorMessageBundles.put(forLocale, rb);
                    }
                });
    }

    @Override
    public String getString(final String errorCode, final Locale locale) {
        final Locale localeToUse = Optional.ofNullable(locale).orElse(getLocale());

        // Get the resource bundle for the specified locale.
        final String localeName = localeToUse.toString();
        final ResourceBundle rb = Optional.ofNullable(errorMessageBundles.get(localeName)).orElse(errorMessageBundles.get(getLocale().toString()));

        // Start with the value as the key itself.
        String value = errorCode;
        if (Objects.nonNull(rb)) {
            // Get the string and if the key is not found, a MissingResourceException is thrown. We will catch
            // it and log a message.
            try {
                value = rb.getString(errorCode);
            } catch (final MissingResourceException e) {
                // Log a debug message stating that the entry is not found.
                ErrorMessages.LOGGER.debug("Missing resource: key {}, error {}", errorCode, e.getMessage());
            }
        }
        return value;
    }

    @Override
    public String getFormattedString(final String errorCode, final Locale locale, final Object... messageArgs) {
        // Delegate to the other api to retrieve the value from the resource bundle.
        String value = getString(errorCode, locale);
        // We need to apply the specified message arguments if and only if the value is not null and it is not the
        // same as the specified key.
        if (StringUtils.isNotBlank(value) && !value.equals(errorCode)) {
            value = MessageFormat.format(value, messageArgs);
        }
        return value;
    }

    /**
     * Returns the singleton instance of the {@link ErrorMessages} instance.
     *
     * @param bundleName
     *         Resource bundle name without the .properties extension (e.g. error_messages). If the resource bundle is
     *         nested inside a subfolder (e.g. src/main/resources/l10n/error_messages.properties), then specify the
     *         bundle name as "l10n/error_messages".
     *
     * @return Loaded {@link ErrorMessages} instance.
     */
    public static synchronized ErrorMessages instance(final String bundleName) {
        return ErrorMessages.instance(bundleName, null, null);
    }

    /**
     * Returns the singleton instance of the {@link ErrorMessages} that holds the resource bundle entries based on the
     * provided details.
     *
     * @param bundleName
     *         Resource bundle name without the .properties extension (e.g. error_messages). If the resource bundle is
     *         nested inside a subfolder (e.g. src/main/resources/l10n/error_messages.properties), then specify the
     *         bundle name as "l10n/error_messages".
     * @param classLoader
     *         Class loader to use.
     *
     * @return Loaded {@link ErrorMessages} instance.
     */
    public static synchronized ErrorMessages instance(final String bundleName, final ClassLoader classLoader) {
        return ErrorMessages.instance(bundleName, classLoader, null);
    }

    /**
     * Returns the singleton instance of the {@link ErrorMessages} that holds the resource bundle entries based on the
     * provided details.
     *
     * @param bundleName
     *         Resource bundle name without the .properties extension (e.g. error_messages). If the resource bundle is
     *         nested inside a subfolder (e.g. src/main/resources/l10n/error_messages.properties), then specify the
     *         bundle name as "l10n/error_messages".
     * @param classLoader
     *         Class loader to use.
     * @param locales
     *         Collection of locales to be considered.
     *
     * @return Loaded {@link ErrorMessages} instance.
     */
    public static synchronized ErrorMessages instance(final String bundleName, final ClassLoader classLoader,
                                                      final Collection<Locale> locales) {
        return new ErrorMessages(bundleName, classLoader, locales);
    }

    /**
     * This method loads the resource bundle based on the provided details and returns the loaded {@link ResourceBundle}
     * instance.
     *
     * @param bundleName
     *         Bundle name without the properties extension (i.e. error_messages instead of error_messages.properties).
     * @param locale
     *         Locale.
     * @param classLoader
     *         Class loader.
     *
     * @return Loaded ResourceBundle.
     */
    private static ResourceBundle loadResourceBundle(final String bundleName, final Locale locale,
                                                     final ClassLoader classLoader) {
        ResourceBundle rb = null;
        try {
            if (Objects.nonNull(classLoader)) {
                rb = ResourceBundle.getBundle(bundleName, locale, classLoader);
            } else {
                rb = ResourceBundle.getBundle(bundleName, locale);
            }
        } catch (final MissingResourceException ex) {
            ErrorMessages.LOGGER.debug("Unable to load resource bundle {} for locale {}", bundleName, locale);
            ErrorMessages.LOGGER.error(ex.getMessage(), ex);
        }
        return rb;
    }
}