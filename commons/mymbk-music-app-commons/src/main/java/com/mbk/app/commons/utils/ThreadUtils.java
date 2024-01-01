/*
 * Copyright (c) 2020 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of REPLACE_.
 *
 * REPLACE_CUSTOMER_NAME and associated code cannot be copied
 * and/or distributed without a written permission of MBK App COPY,
 * and/or its subsidiaries.
 */

package com.mbk.app.commons.utils;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class that provides utility methods catering to threads (e.g. sleep, etc.).
 *
 * @author Editor
 */
@Slf4j
public final class ThreadUtils {
    /**
     * Private constructor.
     */
    private ThreadUtils() {
        throw new IllegalStateException("Cannot create instances of this class");
    }

    /**
     * This method makes the current thread sleep for the {@code timeout} duration and the units are expressed via
     * {@code timeUnit}.
     *
     * @param timeUnit
     *         Time unit.
     * @param timeout
     *         Duration of the sleep.
     */
    public static void sleep(final TimeUnit timeUnit, final long timeout) {
        try {
            timeUnit.sleep(timeout);
        } catch (final InterruptedException e) {
            ThreadUtils.LOGGER.error(e.getMessage(), e);
            // Restore the interrupted state.
            Thread.currentThread().interrupt();
        }
    }
}