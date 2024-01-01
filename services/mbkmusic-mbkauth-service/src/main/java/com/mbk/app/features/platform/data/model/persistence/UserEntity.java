/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.model.persistence;


import com.mbk.app.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "user" table in the database to an entity in the ORM world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity extends AbstractIdGeneratedEntity<Integer> {

    /** User name of the user. */
    @Column(name = "username", nullable = false)
    private String username;

    /** Password of the user. */
    @Column(name = "password")
    private String password;

    /** Reference to the roles. */
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<RoleEntity> roles;

    /** Reference to the password_expiry_timestamp. */
    @Column(name = "password_expiry_timestamp")
    private Long passwordExpiryTimestamp;
    @Column(name = "reset_password_token",length = 46)
    private  String resetPasswordToken;
}
