/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.data.jpa.persistence;

import com.mbk.app.commons.data.persistence.IEntity;

import java.io.Serializable;

/**
 * Abstract implementation of an entity where the value of the primary key.
 *
 * @author Editor
 */
public abstract class AbstractPrimaryKeyEntity<ID extends Serializable> implements IEntity<ID> {

}