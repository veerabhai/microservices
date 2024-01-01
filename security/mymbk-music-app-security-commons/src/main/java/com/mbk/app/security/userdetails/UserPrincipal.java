/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.security.userdetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Default implementation that wraps the basic user details as modeled in {@link
 * com.mbk.app.security.userdetails.IExtendedUserDetails} contract.
 *
 * <p>This implementation follows the "Composition" pattern over "Inheritance" and subsequently
 * applies additional decorations.
 *
 * @author Editor
 */
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
@ToString(of = {"id"})
@Getter
@Setter
public class UserPrincipal extends AbstractUserPrincipal<Integer> {
    /** Unique identifier of the user. */
    private Integer id;
    /** User name of the user. */
    private String username;

    /** Password of the user. */
    private String password;

    /**
     * Constructor.
     *
     * @param id Unique identifier of the user.
     * @param username User name of the user.
     * @param password Password of the user.
     * @param user User instance of type {@link User}.
     */
    public UserPrincipal(
            final UserDetails user,
            final Integer id,
            final String username,
            final String password) {
        this.user = user;
        this.id = id;

        this.username = username;

        this.password = password;
    }
}
