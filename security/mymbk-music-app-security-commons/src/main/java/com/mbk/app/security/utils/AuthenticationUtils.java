/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.utils;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

import com.mbk.app.commons.error.CommonErrors;
import com.mbk.app.commons.utils.Adapter;
import com.mbk.app.security.userdetails.UserPrincipal;
import com.mbk.app.commons.exception.ServiceException;


/**
 * Utility class that provides helper methods to extract user principal from the Authentication object, etc.
 *
 * @author Editor
 */
@Slf4j
public final class AuthenticationUtils {
    /**
     * Private constructor.
     */
    private AuthenticationUtils() {
        throw new IllegalStateException("Cannot create instances of this class");
    }

    /**
     * This method returns the current logged in user (principal) of type {@link UserPrincipal} from the {@link
     * Authentication} object available in the {@link org.springframework.security.core.context.SecurityContext}.
     *
     * @return User principal of type {@link UserPrincipal} if the authentication object holds one, else returns null
     */
    public static UserPrincipal getPrincipal() {
        return AuthenticationUtils.getPrincipal(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * This method returns the principal identifier (i.e. unique identifier of the current logged in user) by extracting
     * the user principal from the {@link Authentication} object available in the {@link
     * org.springframework.security.core.context.SecurityContext}.
     *
     * @return Unique identifier of the current logged in user / principal.
     */
    public static String getPrincipalId() {
        final UserPrincipal userPrincipal = AuthenticationUtils.getPrincipal();
        if (Objects.isNull(userPrincipal)) {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // Get the principal object from the authentication object.
            final Object authenticationPrincipal = Objects.nonNull(authentication) && Objects.nonNull(authentication.getPrincipal())
                    ? authentication.getPrincipal()
                    : null;
            return String.valueOf(authenticationPrincipal);
        }
        return String.valueOf(userPrincipal.getId());
    }

    /**
     * This method returns the principal of type {@link UserPrincipal} if the provided authentication object holds the
     * principal of this type
     *
     * @param authentication
     *         Authentication object of type {@link Authentication} and holds the details of the authenticated user. If
     *         there is no authenticated user, this will point to anonymous user.
     *
     * @return User principal of type {@link UserPrincipal} if the authentication object holds one, else returns null
     */
    public static UserPrincipal getPrincipal(final Authentication authentication) {
        return getPrincipal(authentication, UserPrincipal.class);
    }

    /**
     * This method returns the principal contained within the authentication object and adapts to the specified type if
     * possible. If the adaptation fails, this method returns null.
     *
     * @param authentication
     *         Authentication object of type {@link Authentication} and holds the details of the authenticated user. If
     *         there is no authenticated user, this will point to anonymous user.
     * @param userPrincipalType
     *         Target type of the user principal that needs to be adapted to
     * @param <T>
     *         Target type
     *
     * @return User principal adapted to the target type. If the adaptation fails, this method returns null
     */
    public static <T> T getPrincipal(final Authentication authentication, final Class<T> userPrincipalType) {
        // Get the principal object from the authentication object.
        final Object authenticationPrincipal = Objects.nonNull(authentication) && Objects.nonNull(authentication.getPrincipal())
                ? authentication.getPrincipal()
                : null;

        // Cast the principal object to the specified type if possible, else return null.
        T principal = null;
        if (Objects.nonNull(authenticationPrincipal)) {
            principal = Adapter.adaptTo(authenticationPrincipal, userPrincipalType);
        }
        return principal;
    }

    /**
     * This method returns the tenant identifier of the current logged in user. If there is no tenant identifier (which
     * shouldn't be the case), this method returns an empty {@link Optional}.
     *
     * @return An {@link Optional} instance containing the tenant identifier of the current logged in user.
     */
//TODO
    public static String getPrincipalTenantIdOrThrow() {
        return Strings.EMPTY;
    }

    /**
     * This method returns the current logged in user (principal) of type {@link UserPrincipal} from the {@link
     * Authentication} object available in the {@link org.springframework.security.core.context.SecurityContext}.
     *
     * @return User principal of type {@link UserPrincipal} if the authentication object holds one, else returns null
     */
    public static UserPrincipal getPrincipalOrThrow() {
        final UserPrincipal currentUser = AuthenticationUtils.getPrincipal();
        if (Objects.isNull(currentUser)) {
            AuthenticationUtils.LOGGER.error("User is not authenticated");
            throw ServiceException.instance(CommonErrors.USER_NOT_AUTHENTICATED);
        }

        return currentUser;
    }
}