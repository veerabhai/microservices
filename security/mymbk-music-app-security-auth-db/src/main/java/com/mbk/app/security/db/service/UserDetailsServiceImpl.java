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

import org.springframework.stereotype.Service;

import com.mbk.app.security.db.repository.UserEntityRepository;


/**
 * Default implementation for {@link org.springframework.security.core.userdetails.UserDetailsService}.
 *
 * @author Editor
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl extends AbstractUserDetailsService {

    /**
     * Constructor.
     *
     * @param userEntityRepository
     *         Repository implementation of type {@link UserEntityRepository}.
     */
    public UserDetailsServiceImpl(final UserEntityRepository userEntityRepository) {
        super(userEntityRepository);
    }
}