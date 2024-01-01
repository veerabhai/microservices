/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.instrumentation.Instrument;
import com.mbk.app.features.platform.data.mapper.UserAssetTypeMapper;
import com.mbk.app.features.platform.data.model.experience.userassettype.CreateUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.PatchUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.UpdateUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.UserAssetType;
import com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity;
import com.mbk.app.features.platform.data.repository.UserAssetTypeRepository;
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
 * entities of type {@link UserAssetTypeEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class UserAssetTypeService {
    /** Repository implementation of type {@link UserAssetTypeRepository}. */
    private final UserAssetTypeRepository userAssetTypeRepository;

    /**
     * Mapper implementation of type {@link UserAssetTypeMapper} to transform between different
     * types.
     */
    private final UserAssetTypeMapper userAssetTypeMapper;

    /**
     * Constructor.
     *
     * @param userAssetTypeRepository Repository instance of type {@link UserAssetTypeRepository}.
     * @param userAssetTypeMapper Mapper instance of type {@link UserAssetTypeMapper}.
     */
    public UserAssetTypeService(
            final UserAssetTypeRepository userAssetTypeRepository,
            final UserAssetTypeMapper userAssetTypeMapper) {
        this.userAssetTypeRepository = userAssetTypeRepository;
        this.userAssetTypeMapper = userAssetTypeMapper;
    }

    /**
     * This method attempts to create an instance of type {@link UserAssetTypeEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     UserAssetTypeEntity}.
     * @return An experience model of type {@link UserAssetType} that represents the newly created
     *     entity of type {@link UserAssetTypeEntity}.
     */
    @Instrument
    @Transactional
    public UserAssetType createUserAssetType(@Valid final CreateUserAssetTypeRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final UserAssetTypeEntity userAssetTypeEntity = userAssetTypeMapper.transform(payload);

        // 2. Save the entity.
        UserAssetTypeService.LOGGER.debug("Saving a new instance of type - UserAssetTypeEntity");
        final UserAssetTypeEntity newInstance = userAssetTypeRepository.save(userAssetTypeEntity);

        // 3. Transform the created entity to an experience model and return it.
        return userAssetTypeMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserAssetTypeEntity} using
     * the details from the provided input, which is an instance of type {@link
     * UpdateUserAssetTypeRequest}.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserAssetType, which
     *     needs to be updated in the system.
     * @return A instance of type {@link UserAssetType} containing the updated details.
     */
    @Instrument
    @Transactional
    public UserAssetType updateUserAssetType(
            final String userAssetTypeCode, @Valid final UpdateUserAssetTypeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserAssetTypeEntity matchingInstance =
                userAssetTypeRepository.findByIdOrThrow(userAssetTypeCode);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        userAssetTypeMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        UserAssetTypeService.LOGGER.debug("Saving the updated entity - UserAssetTypeEntity");
        final UserAssetTypeEntity updatedInstance = userAssetTypeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userAssetTypeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserAssetTypeEntity} using
     * the details from the provided input, which is an instance of type {@link
     * PatchUserAssetTypeRequest}.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserAssetType, which
     *     needs to be updated in the system.
     * @return A instance of type {@link UserAssetType} containing the updated details.
     */
    @Instrument
    @Transactional
    public UserAssetType patchUserAssetType(
            final String userAssetTypeCode, @Valid final PatchUserAssetTypeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserAssetTypeEntity matchingInstance =
                userAssetTypeRepository.findByIdOrThrow(userAssetTypeCode);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(UserAssetTypeEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        UserAssetTypeService.LOGGER.debug("Saving the updated entity - UserAssetTypeEntity");
        final UserAssetTypeEntity updatedInstance = userAssetTypeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userAssetTypeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link UserAssetTypeEntity} whose unique identifier matches
     * the provided identifier.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, whose details have
     *     to be retrieved.
     * @return Matching entity of type {@link UserAssetType} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public UserAssetType findUserAssetType(final String userAssetTypeCode) {
        // 1. Find a matching entity and throw an exception if not found.
        final UserAssetTypeEntity matchingInstance =
                userAssetTypeRepository.findByIdOrThrow(userAssetTypeCode);

        // 2. Transform the matching entity to the desired output.
        return userAssetTypeMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type UserAssetTypeEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link UserAssetType}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<UserAssetType> findAllUserAssetTypes(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        UserAssetTypeService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<UserAssetTypeEntity> pageData = userAssetTypeRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<UserAssetType> dataToReturn =
                    pageData.getContent().stream()
                            .map(userAssetTypeMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link UserAssetTypeEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, which needs to be
     *     deleted.
     * @return Unique identifier of the instance of type UserAssetTypeEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteUserAssetType(final String userAssetTypeCode) {
        // 1. Delegate to our repository method to handle the deletion.
        return userAssetTypeRepository.deleteOne(userAssetTypeCode);
    }
}
