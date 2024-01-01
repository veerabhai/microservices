/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.mapper.decorator.UserAssetMapperDecorator;
import com.mbk.app.features.platform.data.model.experience.userasset.CreateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UpdateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UserAsset;
import com.mbk.app.features.platform.data.model.persistence.UserAssetEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link UserAssetEntity to {@link UserAsset and vice-versa.
 *
 * @author Admin
 */
@DecoratedWith(value = UserAssetMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserAssetMapper {

    /**
     * This method transforms an instance of type {@link CreateUserAssetRequest} to an instance of
     * type {@link UserAssetEntity}.
     *
     * @param source Instance of type {@link CreateUserAssetRequest} that needs to be transformed to
     *     {@link UserAssetEntity}.
     * @return Instance of type {@link UserAssetEntity}.
     */
    @Mapping(source = "typeCode", target = "typeCode", ignore = true)
    UserAssetEntity transform(CreateUserAssetRequest source);

    /**
     * This method transforms an instance of type {@link UserAssetEntity} to an instance of type
     * {@link UserAsset}.
     *
     * @param source Instance of type {@link UserAssetEntity} that needs to be transformed to {@link
     *     UserAsset}.
     * @return Instance of type {@link UserAsset}.
     */
    @Mapping(source = "typeCode", target = "typeCode", ignore = true)
    UserAsset transform(UserAssetEntity source);

    /**
     * This method converts / transforms the provided collection of {@link UserAssetEntity}
     * instances to a collection of instances of type {@link UserAsset}.
     *
     * @param source Instance of type {@link UserAssetEntity} that needs to be transformed to {@link
     *     UserAsset}.
     * @return Collection of instance of type {@link UserAsset}.
     */
    default Collection<UserAsset> transformListTo(Collection<UserAssetEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link UserAssetEntity} instances to a
     * list of instances of type {@link UserAsset}.
     *
     * @param source Instance of type {@link UserAssetEntity} that needs to be transformed to {@link
     *     UserAsset}.
     * @return List of instance of type {@link UserAsset}.
     */
    default List<UserAsset> transformListTo(List<UserAssetEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateUserAssetRequest} to an instance of
     * type {@link UserAssetEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateUserAssetRequest} that needs to be transformed to
     *     {@link UserAssetEntity}.
     * @param target Instance of type {@link UserAssetEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "typeCode", target = "typeCode", ignore = true)
    void transform(UpdateUserAssetRequest source, @MappingTarget UserAssetEntity target);

    /**
     * This method transforms an instance of type {@link UpdateUserAssetRequest} to an instance of
     * type {@link UserAssetEntity}.
     *
     * @param source Instance of type {@link UpdateUserAssetRequest} that needs to be transformed to
     *     {@link UserAssetEntity}.
     * @return Instance of type {@link UserAssetEntity}.
     */
    @Mapping(source = "typeCode", target = "typeCode", ignore = true)
    UserAssetEntity transform(UpdateUserAssetRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateUserAssetRequest}
     * instances to a collection of instances of type {@link UserAssetEntity}.
     *
     * @param source Instance of type {@link UpdateUserAssetRequest} that needs to be transformed to
     *     {@link UserAssetEntity}.
     * @return Instance of type {@link UserAssetEntity}.
     */
    default Collection<UserAssetEntity> transformList(Collection<UpdateUserAssetRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
