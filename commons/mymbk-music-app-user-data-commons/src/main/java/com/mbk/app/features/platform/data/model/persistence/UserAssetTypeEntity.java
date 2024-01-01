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
 * Implementation that maps the "user_asset_type" table in the database to an entity in the ORM world.
 *
 * @author Editor
 */
@EqualsAndHashCode(of = {"code"}, callSuper = true)
@ToString(of = {"code"}, callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_asset_type")
@Entity
public class UserAssetTypeEntity extends AbstractLookupEntity<String> {
    /** Unique code representing the type of an asset which belongs to the user (e.g. PROFILE_PICTURE). */
    @Column(name = "code", length = 32)
    @Id
    private String code;

    /** Display value of an asset type (e.g. Profile Picture). */
    @Column(name = "value", nullable = false, length = 64)
    private String value;
}