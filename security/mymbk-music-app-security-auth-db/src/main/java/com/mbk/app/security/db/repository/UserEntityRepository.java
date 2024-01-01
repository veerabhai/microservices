/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mbk.app.security.db.model.persistence.UserPrincipalEntity;

import java.util.Optional;

/**
 * Repository contract for {@link UserPrincipalEntity} entity and provides CRUD functionality for the same.
 *
 * @author Editor
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserPrincipalEntity, Integer> {

    /**
     * This method attempts to find a user based on the provided {@code userName} parameter.
     *
     * @param userName
     *         User name to find.
     *
     * @return Matching user of type {@link UserPrincipalEntity}, else returns null.
     */
    Optional<UserPrincipalEntity> findByUsername(String userName);

    @Query(value = "select Approval_Status from mbk_auth_schema.USERS_ADMIN_APPROVAL where mbk_auth_schema.USERS_ADMIN_APPROVAL.username=:name", nativeQuery = true)
    String findUserStatus(String name);
}