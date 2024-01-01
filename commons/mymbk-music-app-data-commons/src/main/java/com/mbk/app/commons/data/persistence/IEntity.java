/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.data.persistence;

import java.io.Serializable;

/**
 * Base contract for an entity definition.
 *
 * @param <ID>
 *         Type of the primary key.
 *
 * @author Editor
 */
public interface IEntity<ID extends Serializable> extends Serializable {
    /**
     * This method returns a unique identifier (typically value for the primary key) of the corresponding entity.
     *
     * @return Unique identifier of the identity.
     */
    ID getId();

    /**
     * This method returns the display name of the entity.
     *
     * @return Display name of the entity.
     */
    default String displayName() {
        final String className = getClass().getSimpleName();
        final int entitySuffixPosition = className.indexOf("Entity");
        if (entitySuffixPosition > 0) {
            return className.substring(0, entitySuffixPosition);
        }
        return className;
    }
}