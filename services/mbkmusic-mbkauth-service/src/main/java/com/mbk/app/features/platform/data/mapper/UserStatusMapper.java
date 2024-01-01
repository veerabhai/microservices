/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.userstatus.CreateUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.UpdateUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.UserStatus;
import com.mbk.app.features.platform.data.model.persistence.UserStatusEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link UserStatusEntity to {@link UserStatus and vice-versa.
 *
 * @author Admin
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserStatusMapper {

    /**
     * This method transforms an instance of type {@link CreateUserStatusRequest} to an instance of
     * type {@link UserStatusEntity}.
     *
     * @param source Instance of type {@link CreateUserStatusRequest} that needs to be transformed
     *     to {@link UserStatusEntity}.
     * @return Instance of type {@link UserStatusEntity}.
     */
    UserStatusEntity transform(CreateUserStatusRequest source);

    /**
     * This method transforms an instance of type {@link UserStatusEntity} to an instance of type
     * {@link UserStatus}.
     *
     * @param source Instance of type {@link UserStatusEntity} that needs to be transformed to
     *     {@link UserStatus}.
     * @return Instance of type {@link UserStatus}.
     */
    UserStatus transform(UserStatusEntity source);

    /**
     * This method converts / transforms the provided collection of {@link UserStatusEntity}
     * instances to a collection of instances of type {@link UserStatus}.
     *
     * @param source Instance of type {@link UserStatusEntity} that needs to be transformed to
     *     {@link UserStatus}.
     * @return Collection of instance of type {@link UserStatus}.
     */
    default Collection<UserStatus> transformListTo(Collection<UserStatusEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link UserStatusEntity} instances to
     * a list of instances of type {@link UserStatus}.
     *
     * @param source Instance of type {@link UserStatusEntity} that needs to be transformed to
     *     {@link UserStatus}.
     * @return List of instance of type {@link UserStatus}.
     */
    default List<UserStatus> transformListTo(List<UserStatusEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateUserStatusRequest} to an instance of
     * type {@link UserStatusEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateUserStatusRequest} that needs to be transformed
     *     to {@link UserStatusEntity}.
     * @param target Instance of type {@link UserStatusEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    void transform(UpdateUserStatusRequest source, @MappingTarget UserStatusEntity target);

    /**
     * This method transforms an instance of type {@link UpdateUserStatusRequest} to an instance of
     * type {@link UserStatusEntity}.
     *
     * @param source Instance of type {@link UpdateUserStatusRequest} that needs to be transformed
     *     to {@link UserStatusEntity}.
     * @return Instance of type {@link UserStatusEntity}.
     */
    UserStatusEntity transform(UpdateUserStatusRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateUserStatusRequest}
     * instances to a collection of instances of type {@link UserStatusEntity}.
     *
     * @param source Instance of type {@link UpdateUserStatusRequest} that needs to be transformed
     *     to {@link UserStatusEntity}.
     * @return Instance of type {@link UserStatusEntity}.
     */
    default Collection<UserStatusEntity> transformList(Collection<UpdateUserStatusRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
