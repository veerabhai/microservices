/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.db.model.experience;

import com.mbk.app.commons.properties.AbstractPropertiesProvider;

/**
 * An abstract class for the User Profile, having the id of the User.
 *
 * @author Editor
 */
public abstract class AbstractUserProfile<T> extends AbstractPropertiesProvider {
    private T id;
}