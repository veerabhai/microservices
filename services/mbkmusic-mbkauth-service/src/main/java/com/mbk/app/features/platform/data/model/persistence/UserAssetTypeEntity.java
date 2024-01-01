/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.model.persistence;

import com.mbk.app.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "user_asset_type" table in the database to an entity in the ORM
 * world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_asset_type")
@Entity
public class UserAssetTypeEntity extends AbstractUUIDGeneratedEntity {

    @Column(name ="code", nullable=false, length  = 64)
    private String code;


    /** Reference to the value. */
    @Column(name = "value", nullable = false, length = 64)
    private String value;
}
