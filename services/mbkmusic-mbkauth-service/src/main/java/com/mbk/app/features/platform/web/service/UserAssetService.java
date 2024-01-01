/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.instrumentation.Instrument;
import com.mbk.app.features.platform.data.mapper.UserAssetMapper;
import com.mbk.app.features.platform.data.model.experience.userasset.CreateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.PatchUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UpdateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UserAsset;
import com.mbk.app.features.platform.data.model.persistence.UserAssetEntity;
import com.mbk.app.features.platform.data.repository.UserAssetRepository;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link UserAssetEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class UserAssetService {
    /** Repository implementation of type {@link UserAssetRepository}. */
    private final UserAssetRepository userAssetRepository;

    /**
     * Mapper implementation of type {@link UserAssetMapper} to transform between different types.
     */
    private final UserAssetMapper userAssetMapper;

    /**
     * Constructor.
     *
     * @param userAssetRepository Repository instance of type {@link UserAssetRepository}.
     * @param userAssetMapper Mapper instance of type {@link UserAssetMapper}.
     */
    public UserAssetService(
            final UserAssetRepository userAssetRepository, final UserAssetMapper userAssetMapper) {
        this.userAssetRepository = userAssetRepository;
        this.userAssetMapper = userAssetMapper;
    }

    /**
     * This method attempts to create an instance of type {@link UserAssetEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     UserAssetEntity}.
     * @return An experience model of type {@link UserAsset} that represents the newly created
     *     entity of type {@link UserAssetEntity}.
     */
    @Instrument
    @Transactional
    public UserAsset createUserAsset(@Valid final CreateUserAssetRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final UserAssetEntity userAssetEntity = userAssetMapper.transform(payload);

        // 2. Save the entity.
        UserAssetService.LOGGER.debug("Saving a new instance of type - UserAssetEntity");
        final UserAssetEntity newInstance = userAssetRepository.save(userAssetEntity);

        // 3. Transform the created entity to an experience model and return it.
        return userAssetMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserAssetEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateUserAssetRequest}.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing UserAsset, which needs
     *     to be updated in the system.
     * @return A instance of type {@link UserAsset} containing the updated details.
     */
    @Instrument
    @Transactional
    public UserAsset updateUserAsset(
            final String userAssetId, @Valid final UpdateUserAssetRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserAssetEntity matchingInstance = userAssetRepository.findByIdOrThrow(userAssetId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        userAssetMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        UserAssetService.LOGGER.debug("Saving the updated entity - UserAssetEntity");
        final UserAssetEntity updatedInstance = userAssetRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userAssetMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserAssetEntity} using the
     * details from the provided input, which is an instance of type {@link PatchUserAssetRequest}.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing UserAsset, which needs
     *     to be updated in the system.
     * @return A instance of type {@link UserAsset} containing the updated details.
     */
    @Instrument
    @Transactional
    public UserAsset patchUserAsset(
            final String userAssetId, @Valid final PatchUserAssetRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserAssetEntity matchingInstance = userAssetRepository.findByIdOrThrow(userAssetId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(UserAssetEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        UserAssetService.LOGGER.debug("Saving the updated entity - UserAssetEntity");
        final UserAssetEntity updatedInstance = userAssetRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userAssetMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link UserAssetEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link UserAsset} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public UserAsset findUserAsset(final String userAssetId) {
        // 1. Find a matching entity and throw an exception if not found.
        final UserAssetEntity matchingInstance = userAssetRepository.findByIdOrThrow(userAssetId);

        // 2. Transform the matching entity to the desired output.
        return userAssetMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type UserAssetEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link UserAsset}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<UserAsset> findAllUserAssets(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        UserAssetService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<UserAssetEntity> pageData = userAssetRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<UserAsset> dataToReturn =
                    pageData.getContent().stream()
                            .map(userAssetMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link UserAssetEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type UserAssetEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteUserAsset(final String userAssetId) {
        // 1. Delegate to our repository method to handle the deletion.
        return userAssetRepository.deleteOne(userAssetId);
    }
}
