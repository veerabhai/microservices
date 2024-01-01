/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.mapper.decorator;

import com.mbk.app.features.platform.data.mapper.RoleMapper;
import com.mbk.app.features.platform.data.mapper.UserMapper;
import com.mbk.app.features.platform.data.model.experience.user.CreateUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.UpdateUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.User;
import com.mbk.app.features.platform.data.model.persistence.UserEntity;
import com.mbk.app.features.platform.data.repository.RoleRepository;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link UserEntity to {@link User and vice-versa.
 *
 * @author Admin
 */
@Slf4j
public abstract class UserMapperDecorator implements UserMapper {

    @Autowired private PasswordEncoder passwordEncoder;

    /** Repository implementation of type {@link RoleRepository}. */
    @Autowired private RoleRepository roleRepository;

    /** Mapper implementation of type {@link UserMapper}. */
    @Autowired private UserMapper userMapper;

    /** Mapper implementation of type {@link RoleMapper}. */
    @Autowired private RoleMapper roleMapper;

    @Override
    public UserEntity transform(final CreateUserRequest source) {
        // 1. Transform the CreateUserRequest to UserEntity object.
        final UserEntity user = userMapper.transform(source);
        if (Objects.nonNull(source.getPassword()) && !source.getPassword().isEmpty()) {
            source.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        if (!CollectionUtils.isEmpty(source.getRoles())) {
            user.setRoles(roleRepository.findAllById(source.getRoles()));
        }
        // Return the transformed object.
        return user;
    }

    @Override
    public User transform(final UserEntity source) {
        // 1. Transform the UserEntity to User object.
        final User user = userMapper.transform(source);
        if (Objects.nonNull(source.getPassword()) && !source.getPassword().isEmpty()) {
            source.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        if (!CollectionUtils.isEmpty(source.getRoles())) {
            user.setRoles(
                    source.getRoles().stream()
                            .map(role -> roleMapper.transform(role))
                            .collect(Collectors.toList()));
        }
        // Return the transformed object.
        return user;
    }

    @Override
    public void transform(final UpdateUserRequest source, final @MappingTarget UserEntity target) {

        // Transform from source to the target.
        userMapper.transform(source, target);

        if (!CollectionUtils.isEmpty(source.getRoles())) {
            target.setRoles(roleRepository.findAllById(source.getRoles()));
        }
    }

    @Override
    public UserEntity transform(final UpdateUserRequest source) {

        // Transform from source to the target.
        final UserEntity user = userMapper.transform(source);

        if (!CollectionUtils.isEmpty(source.getRoles())) {
            user.setRoles(roleRepository.findAllById(source.getRoles()));
        }

        // Return the response.
        return user;
    }
}
