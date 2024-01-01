/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.db.service;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;


import com.mbk.app.commons.error.CommonErrors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.mbk.app.security.db.model.persistence.UserPrincipalEntity;
import com.mbk.app.security.db.repository.UserEntityRepository;

/**
 * Abstract implementation that provides the necessary abstractions to load user-specific data.
 * <p>
 * It is the responsibility of the subclass implementations to perform the specifics of connecting to their data-sources
 * and finding the appropriate users.
 *
 * @author Editor
 *
 */


public abstract class AbstractUserDetailsService implements UserDetailsService {
    /** User not found error message. */
    private static final String USER_NOT_FOUND_MESSAGE = "Unable to find user with username - {0}";
    private static final String ADMIN_APPROVE_STATUS_IN_PENDING = "Admin approval is in pending - {0}";
    private static final String ADMIN_REJECTED = "Admin Rejected you request - {0}";
    /** Repository implementation of type {@link UserEntityRepository}. */
    protected final UserEntityRepository userEntityRepository;

    /**
     * Constructor.
     *
     * @param userEntityRepository
     *         Repository implementation of type {@link UserEntityRepository}.
     */
    protected AbstractUserDetailsService(final UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) {
        if (StringUtils.isBlank(username)) {
            // Should not generally occur.
            throw new IllegalArgumentException("Username cannot be null / empty");
        }

        String status = userEntityRepository.findUserStatus(username);
        if(StringUtils.isNotBlank(status) && "NOT_APPROVED".equalsIgnoreCase(status)){
            //  throw new IllegalArgumentException(MessageFormat.format(AbstractUserDetailsService.ADMIN_APPROVE_STATUS_IN_PENDING, username));
           // throw new IllegalArgumentException("NOT_APPROVED");
            throw new IllegalArgumentException(String.valueOf(CommonErrors.PLEASE_WAIT_UNTIL_YOUR_ACCOUNT_IS_APPROVED));
        }

        if(StringUtils.isNotBlank(status) && "REJECTED".equalsIgnoreCase(status)){
            //throw new IllegalArgumentException( MessageFormat.format(AbstractUserDetailsService.ADMIN_REJECTED, username));
           // throw new IllegalArgumentException("rejected");
            throw new IllegalArgumentException(String.valueOf(CommonErrors.ADMIN_REJECTED));
        }

        // Find the user either by username or by email.
        final Optional<UserPrincipalEntity> matchingUser = userEntityRepository.findByUsername(username);
        if (Objects.isNull(matchingUser)) {
            final String error = MessageFormat.format(AbstractUserDetailsService.USER_NOT_FOUND_MESSAGE, username);
            throw new UsernameNotFoundException(error);
        }



        if(matchingUser.isPresent()){
            // Now delegate to the method that transforms this user object to the principal object.
                // Now delegate to the method that transforms this user object to the principal object.
                final UserPrincipalEntity userPrincipalEntity = matchingUser.get();
                return userPrincipalEntity.getUserPrincipal(userPrincipalEntity.formUserDetails(userPrincipalEntity),userPrincipalEntity);

        }
        return null;
    }
}