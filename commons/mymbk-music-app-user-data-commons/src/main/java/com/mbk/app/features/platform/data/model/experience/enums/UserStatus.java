/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.features.platform.data.model.experience.enums;

import java.util.Objects;

/**
 * Enumerated data type that captures the different statuses that an user can be in.
 *
 * @author Editor
 */
public enum UserStatus {
    NEW,
    ACTIVE,
    INACTIVE,
    PENDING,
    DELETED;

    /**
     * This method checks if the type of this enum instance matches any of the provided types.
     *
     * @param typesToCompare
     *         Array of enum types to compare.
     *
     * @return True if the type of this enum instance is the same as one of the provided types.
     */
    public boolean isAny(final UserStatus... typesToCompare) {
        boolean verdict = false;
        if (Objects.nonNull(typesToCompare) && typesToCompare.length > 0) {
            for (final UserStatus status : typesToCompare) {
                if (this.equals(status)) {
                    verdict = true;
                    break;
                }
            }
        }
        return verdict;
    }
}
