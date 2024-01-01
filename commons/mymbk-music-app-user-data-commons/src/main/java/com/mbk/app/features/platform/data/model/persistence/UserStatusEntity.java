/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.features.platform.data.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.mbk.app.commons.data.jpa.persistence.AbstractLookupEntity;

/**
 * Implementation that maps the "user_status" table in the database to an entity in the ORM world.
 *
 * @author Editor
 */
@EqualsAndHashCode(of = {"code"}, callSuper = true)
@ToString(of = {"code"}, callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_status")
@Entity
public class UserStatusEntity extends AbstractLookupEntity<String> {
    /** Unique code representing the status that an user can be in (e.g. NEW, ACTIVE, INACTIVE and DELETED). */
    @Column(name = "code", length = 16)
    @Id
    private String code;

    /** Display value of the agent status (e.g. New, Active, InActive and Deleted). */
    @Column(name = "value", nullable = false, length = 32)
    private String value;
}