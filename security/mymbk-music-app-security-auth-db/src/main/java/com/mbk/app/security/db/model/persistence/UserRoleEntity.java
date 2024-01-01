/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.db.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mbk.app.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "role" table in the database to an entity in the ORM world.
 *
 * @author Editor
 */
@ToString(of = {"name"},callSuper = true)
@EqualsAndHashCode(of = {"name"},callSuper = true)
@Getter
@Setter
@Table(name = "role")
@Entity
public class UserRoleEntity extends AbstractUUIDGeneratedEntity {
    /** Name of the role. */
    @Column(name = "name", unique = true, length = 64, nullable = false)
    private String name;
}