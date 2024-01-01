/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.model.persistence;

import com.mbk.app.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "user_asset" table in the database to an entity in the ORM world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_asset")
@Entity
public class UserAssetEntity extends AbstractUUIDGeneratedEntity {

    /** Reference to the name. */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /** Reference to the path. */
    @Column(name = "path", nullable = false, length = 512)
    private String path;

    /** Reference to the type_code. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "type_code", referencedColumnName = "code", nullable = false)
    private UserAssetTypeEntity typeCode;
}
