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

/**
 * Base contract to indicate that the entity implementing this contract supports soft delete functionality.
 *
 * @author Editor
 */
public interface ISoftDeletable {
    /**
     * This method returns a boolean indicating if this entity is deleted or not.
     *
     * @return True if the entity is deleted, false otherwise.
     */
    boolean isDeleted();

    /**
     * This method sets the deleted flag on the entity to the provided value.
     *
     * @param deleted
     *         Flag that indicates the deleted state of the entity. A value of false indicates that the entity is not
     *         deleted while a value of true indicates that it is deleted.
     */
    void setDeleted(boolean deleted);

    /**
     * This method returns the timestamp (in epoch format) indicating when the entity was deleted.
     *
     * @return Deleted timestamp (in epoch format) indicating when the entity was deleted.
     */
    Long getDeletedTimestamp();

    /**
     * This method sets the deleted timestamp (in epoch format).
     *
     * @param deletedTimestamp
     *         Deleted timestamp (in epoch format).
     */
    void setDeletedTimestamp(Long deletedTimestamp);
}