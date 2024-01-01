/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.userassettype.CreateUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.UpdateUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.UserAssetType;
import com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link UserAssetTypeEntity to {@link UserAssetType and vice-versa.
 *
 * @author Admin
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserAssetTypeMapper {

    /**
     * This method transforms an instance of type {@link CreateUserAssetTypeRequest} to an instance
     * of type {@link UserAssetTypeEntity}.
     *
     * @param source Instance of type {@link CreateUserAssetTypeRequest} that needs to be
     *     transformed to {@link UserAssetTypeEntity}.
     * @return Instance of type {@link UserAssetTypeEntity}.
     */
    UserAssetTypeEntity transform(CreateUserAssetTypeRequest source);

    /**
     * This method transforms an instance of type {@link UserAssetTypeEntity} to an instance of type
     * {@link UserAssetType}.
     *
     * @param source Instance of type {@link UserAssetTypeEntity} that needs to be transformed to
     *     {@link UserAssetType}.
     * @return Instance of type {@link UserAssetType}.
     */
    UserAssetType transform(UserAssetTypeEntity source);

    /**
     * This method converts / transforms the provided collection of {@link UserAssetTypeEntity}
     * instances to a collection of instances of type {@link UserAssetType}.
     *
     * @param source Instance of type {@link UserAssetTypeEntity} that needs to be transformed to
     *     {@link UserAssetType}.
     * @return Collection of instance of type {@link UserAssetType}.
     */
    default Collection<UserAssetType> transformListTo(Collection<UserAssetTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link UserAssetTypeEntity} instances
     * to a list of instances of type {@link UserAssetType}.
     *
     * @param source Instance of type {@link UserAssetTypeEntity} that needs to be transformed to
     *     {@link UserAssetType}.
     * @return List of instance of type {@link UserAssetType}.
     */
    default List<UserAssetType> transformListTo(List<UserAssetTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateUserAssetTypeRequest} to an instance
     * of type {@link UserAssetTypeEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateUserAssetTypeRequest} that needs to be
     *     transformed to {@link UserAssetTypeEntity}.
     * @param target Instance of type {@link UserAssetTypeEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    void transform(UpdateUserAssetTypeRequest source, @MappingTarget UserAssetTypeEntity target);

    /**
     * This method transforms an instance of type {@link UpdateUserAssetTypeRequest} to an instance
     * of type {@link UserAssetTypeEntity}.
     *
     * @param source Instance of type {@link UpdateUserAssetTypeRequest} that needs to be
     *     transformed to {@link UserAssetTypeEntity}.
     * @return Instance of type {@link UserAssetTypeEntity}.
     */
    UserAssetTypeEntity transform(UpdateUserAssetTypeRequest source);

    /**
     * This method converts / transforms the provided collection of {@link
     * UpdateUserAssetTypeRequest} instances to a collection of instances of type {@link
     * UserAssetTypeEntity}.
     *
     * @param source Instance of type {@link UpdateUserAssetTypeRequest} that needs to be
     *     transformed to {@link UserAssetTypeEntity}.
     * @return Instance of type {@link UserAssetTypeEntity}.
     */
    default Collection<UserAssetTypeEntity> transformList(
            Collection<UpdateUserAssetTypeRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
