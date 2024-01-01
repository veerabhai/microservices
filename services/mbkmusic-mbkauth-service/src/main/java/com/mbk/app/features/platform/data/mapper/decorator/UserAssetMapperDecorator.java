/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.mapper.decorator;

import com.mbk.app.features.platform.data.mapper.UserAssetMapper;
import com.mbk.app.features.platform.data.mapper.UserAssetTypeMapper;
import com.mbk.app.features.platform.data.model.experience.userasset.CreateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UpdateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UserAsset;
import com.mbk.app.features.platform.data.model.persistence.UserAssetEntity;
import com.mbk.app.features.platform.data.repository.UserAssetTypeRepository;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link UserAssetEntity to {@link UserAsset and vice-versa.
 *
 * @author Admin
 */
@Slf4j
public abstract class UserAssetMapperDecorator implements UserAssetMapper {

    /** Repository implementation of type {@link UserAssetTypeRepository}. */
    @Autowired private UserAssetTypeRepository userAssetTypeRepository;

    /** Mapper implementation of type {@link UserAssetTypeMapper}. */
    @Autowired private UserAssetTypeMapper userAssetTypeMapper;

    /** Mapper implementation of type {@link UserAssetMapper}. */
    @Autowired private UserAssetMapper userAssetMapper;

    @Override
    public UserAssetEntity transform(final CreateUserAssetRequest source) {
        // 1. Transform the CreateUserAssetRequest to UserAssetEntity object.
        final UserAssetEntity user_asset = userAssetMapper.transform(source);

        user_asset.setTypeCode(userAssetTypeRepository.findByIdOrThrow(source.getTypeCode()));

        // Return the transformed object.
        return user_asset;
    }

    @Override
    public UserAsset transform(final UserAssetEntity source) {
        // 1. Transform the UserAssetEntity to UserAsset object.
        final UserAsset user_asset = userAssetMapper.transform(source);

        user_asset.setTypeCode(userAssetTypeMapper.transform(source.getTypeCode()));

        // Return the transformed object.
        return user_asset;
    }

    @Override
    public void transform(
            final UpdateUserAssetRequest source, final @MappingTarget UserAssetEntity target) {

        // Transform from source to the target.
        userAssetMapper.transform(source, target);

        if (Objects.nonNull(source.getTypeCode())) {
            target.setTypeCode(userAssetTypeRepository.findByIdOrThrow(source.getTypeCode()));
        }
    }

    @Override
    public UserAssetEntity transform(final UpdateUserAssetRequest source) {

        // Transform from source to the target.
        final UserAssetEntity user_asset = userAssetMapper.transform(source);

        if (Objects.nonNull(source.getTypeCode())) {
            user_asset.setTypeCode(userAssetTypeRepository.findByIdOrThrow(source.getTypeCode()));
        }
        // Return the response.
        return user_asset;
    }
}
