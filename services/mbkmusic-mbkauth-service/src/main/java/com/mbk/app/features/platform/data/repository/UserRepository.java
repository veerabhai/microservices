/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.repository;

import com.mbk.app.commons.data.jpa.repository.ExtendedJpaRepository;
import com.mbk.app.features.platform.data.model.experience.role.Role;
import com.mbk.app.features.platform.data.model.experience.user.User;
import com.mbk.app.features.platform.data.model.persistence.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Repository interface to handle the operations pertaining to domain models of type "UserEntity".
 *
 * @author Admin
 */
@Repository
public interface UserRepository extends ExtendedJpaRepository<UserEntity, Integer> {
    @Query(value = "SELECT entity FROM UserEntity entity WHERE entity.username = :username ")
    UserEntity findByUsername(final String username);

    @Transactional
    @Modifying
    @Query(value =  "UPDATE mbk_auth_schema.\"user\"\n" +
            "\tSET password=:newPassword WHERE id=:userId",nativeQuery = true)
    void updatePassword(Integer userId, String newPassword);
}
