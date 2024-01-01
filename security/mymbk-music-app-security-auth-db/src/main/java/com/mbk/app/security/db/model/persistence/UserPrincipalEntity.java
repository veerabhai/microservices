/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.security.db.model.persistence;

import com.mbk.app.commons.SpecialCharacter;
import com.mbk.app.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import com.mbk.app.features.platform.data.model.persistence.UserAssetEntity;
import com.mbk.app.security.userdetails.UserPrincipal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation that maps the "user" table in the database to an entity in the ORM world.
 *
 * @author Editor
 */
@ToString(of = {"id"})
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
@Getter
@Setter
@Table(name = "user")
@Entity
public class UserPrincipalEntity extends AbstractIdGeneratedEntity<Integer> {

    /** GrantedAuthority format as required by Spring Security. */
    private static final String GRANTED_AUTHORITY_FORMAT = "ROLE_{0}";

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
    private Collection<UserRoleEntity> roles;

    /** Unique identifier of an asset(profile picture), which is associated to this user. */
    @OneToOne(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    private UserAssetEntity asset;

    /**
     * This method returns a collection of authority names (i.e. ROLE_[role name]), which are the
     * roles assigned to this user and prefixed by 'ROLE_'.
     *
     * @return Collection of authority names.
     */
    public Collection<String> getAuthorityNamesForAssignedRoles() {
        return Optional.ofNullable(roles).orElse(Collections.emptySet()).stream()
                .map(
                        role ->
                                MessageFormat.format(
                                        UserPrincipalEntity.GRANTED_AUTHORITY_FORMAT,
                                        role.getName()))
                .collect(Collectors.toSet());
    }

    /**
     * This method returns a collection of role names that are assigned to this user.
     *
     * @return Collection of role names.
     */
    public Collection<String> getAssignedRoleNames() {
        return Optional.ofNullable(roles).orElse(Collections.emptySet()).stream()
                .map(UserRoleEntity::getName)
                .collect(Collectors.toSet());
    }

    /**
     * This method attempts to transform the provided user entity (of type {@link
     * UserPrincipalEntity}) to an instance of type {@link
     * org.springframework.security.core.userdetails.UserDetails}.
     *
     * <p>The default implementation returns an instance of {@link UserPrincipal}. Subclasses can
     * override to provide their own implementations of {@link
     * org.springframework.security.core.userdetails.UserDetails}.
     *
     * @return User details instance for the provided user.
     */
    public UserDetails toUserPrincipal() {
        // Build the base user details using the above roles as the authority list.
        // Get all the roles associated to the concerned user.
        final Collection<String> roleNames = getAuthorityNamesForAssignedRoles();

        final UserDetails baseUserDetails =
                User.builder()
                        .username(getUsername())
                        .password(getPassword())
                        .authorities(roleNames.toArray(new String[0]))
                        .build();
        // Delegate to the below method to create and return an instance of UserPrincipal.
        return toUserPrincipal(baseUserDetails);
    }

    /**
     * This method attempts to transform the provided user entity (of type {@link
     * UserPrincipalEntity}) to an instance of type {@link UserDetails}.
     *
     * <p>The default implementation returns an instance of {@link UserPrincipal}. Subclasses can
     * override to provide their own implementations of {@link
     * org.springframework.security.core.userdetails.UserDetails}.
     *
     * @param userDetails User details instance of type {@link
     *     org.springframework.security.core.userdetails.UserDetails}.
     * @return User details instance for the provided user.
     */
    public UserDetails toUserPrincipal(final UserDetails userDetails) {
        UserPrincipal userPrincipal =
                new UserPrincipal(userDetails, getId(), getUsername(), getPassword());

        return userPrincipal;
    }

    /**
     * This method attempts to transform the provided user entity (of type {@link
     * UserPrincipalEntity}) to an instance of type {@link UserDetails}.
     *
     * <p>The default implementation returns an instance of {@link UserPrincipal}. Subclasses can
     * override to provide their own implementations of {@link UserDetails}.
     *
     * @param userPrincipalEntity User details instance of type {@link UserPrincipalEntity}.
     * @return User details instance for the provided user.
     */
    public UserDetails formUserDetails(final UserPrincipalEntity userPrincipalEntity) {
        final UserDetails userDetails =
                User.builder()
                        .username(userPrincipalEntity.getUsername())
                        .password(userPrincipalEntity.getPassword())
                        .authorities(
                                String.join(
                                        SpecialCharacter.COMMA.getValue(),
                                        userPrincipalEntity.getAuthorityNamesForAssignedRoles()))
                        .build();

        return userDetails;
    }

    /**
     * This method attempts to transform the provided user entity (of type {@link
     * UserPrincipalEntity}) to an instance of type {@link UserPrincipal}.
     *
     * <p>The default implementation returns an instance of {@link UserPrincipal}. Subclasses can
     * override to provide their own implementations of {@link UserDetails}.
     *
     * @param userDetails User details instance of type {@link UserDetails}.
     * @param userPrincipalEntity User principal entity instance of type {@link
     *     UserPrincipalEntity}.
     * @return User details instance for the provided user.
     */
    @SneakyThrows
    public UserPrincipal getUserPrincipal(
            final UserDetails userDetails, final UserPrincipalEntity userPrincipalEntity) {
        UserPrincipal userPrincipal =
                new UserPrincipal(
                        userDetails,
                        userPrincipalEntity.getId(),
                        userPrincipalEntity.getUsername(),
                        userPrincipalEntity.getPassword());

        return userPrincipal;
    }
}
