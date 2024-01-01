/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.instrumentation.Instrument;
import com.mbk.app.features.platform.data.mapper.UserStatusMapper;
import com.mbk.app.features.platform.data.model.experience.userstatus.CreateUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.PatchUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.UpdateUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.UserStatus;
import com.mbk.app.features.platform.data.model.persistence.UserStatusEntity;
import com.mbk.app.features.platform.data.repository.UserStatusRepository;
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
 * entities of type {@link UserStatusEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class UserStatusService {
    /** Repository implementation of type {@link UserStatusRepository}. */
    private final UserStatusRepository userStatusRepository;

    /**
     * Mapper implementation of type {@link UserStatusMapper} to transform between different types.
     */
    private final UserStatusMapper userStatusMapper;

    /**
     * Constructor.
     *
     * @param userStatusRepository Repository instance of type {@link UserStatusRepository}.
     * @param userStatusMapper Mapper instance of type {@link UserStatusMapper}.
     */
    public UserStatusService(
            final UserStatusRepository userStatusRepository,
            final UserStatusMapper userStatusMapper) {
        this.userStatusRepository = userStatusRepository;
        this.userStatusMapper = userStatusMapper;
    }

    /**
     * This method attempts to create an instance of type {@link UserStatusEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     UserStatusEntity}.
     * @return An experience model of type {@link UserStatus} that represents the newly created
     *     entity of type {@link UserStatusEntity}.
     */
    @Instrument
    @Transactional
    public UserStatus createUserStatus(@Valid final CreateUserStatusRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final UserStatusEntity userStatusEntity = userStatusMapper.transform(payload);

        // 2. Save the entity.
        UserStatusService.LOGGER.debug("Saving a new instance of type - UserStatusEntity");
        final UserStatusEntity newInstance = userStatusRepository.save(userStatusEntity);

        // 3. Transform the created entity to an experience model and return it.
        return userStatusMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserStatusEntity} using
     * the details from the provided input, which is an instance of type {@link
     * UpdateUserStatusRequest}.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserStatus, which needs
     *     to be updated in the system.
     * @return A instance of type {@link UserStatus} containing the updated details.
     */
    @Instrument
    @Transactional
    public UserStatus updateUserStatus(
            final String userStatusCode, @Valid final UpdateUserStatusRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserStatusEntity matchingInstance =
                userStatusRepository.findByIdOrThrow(userStatusCode);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        userStatusMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        UserStatusService.LOGGER.debug("Saving the updated entity - UserStatusEntity");
        final UserStatusEntity updatedInstance = userStatusRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userStatusMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserStatusEntity} using
     * the details from the provided input, which is an instance of type {@link
     * PatchUserStatusRequest}.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserStatus, which needs
     *     to be updated in the system.
     * @return A instance of type {@link UserStatus} containing the updated details.
     */
    @Instrument
    @Transactional
    public UserStatus patchUserStatus(
            final String userStatusCode, @Valid final PatchUserStatusRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserStatusEntity matchingInstance =
                userStatusRepository.findByIdOrThrow(userStatusCode);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(UserStatusEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        UserStatusService.LOGGER.debug("Saving the updated entity - UserStatusEntity");
        final UserStatusEntity updatedInstance = userStatusRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userStatusMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link UserStatusEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link UserStatus} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public UserStatus findUserStatus(final String userStatusCode) {
        // 1. Find a matching entity and throw an exception if not found.
        final UserStatusEntity matchingInstance =
                userStatusRepository.findByIdOrThrow(userStatusCode);

        // 2. Transform the matching entity to the desired output.
        return userStatusMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type UserStatusEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link UserStatus}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<UserStatus> findAllUserStatuses(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        UserStatusService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<UserStatusEntity> pageData = userStatusRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<UserStatus> dataToReturn =
                    pageData.getContent().stream()
                            .map(userStatusMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link UserStatusEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, which needs to be
     *     deleted.
     * @return Unique identifier of the instance of type UserStatusEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteUserStatus(final String userStatusCode) {
        // 1. Delegate to our repository method to handle the deletion.
        return userStatusRepository.deleteOne(userStatusCode);
    }
}
