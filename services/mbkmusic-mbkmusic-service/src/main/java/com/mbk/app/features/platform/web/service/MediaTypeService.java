/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.*;
import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.instrumentation.*;
import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.mediatype.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import com.mbk.app.features.platform.data.repository.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;
import org.springframework.validation.annotation.*;

import javax.validation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link MediaTypeEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class MediaTypeService {
    /** Repository implementation of type {@link MediaTypeRepository}. */
    private final MediaTypeRepository mediaTypeRepository;

    /**
     * Mapper implementation of type {@link MediaTypeMapper} to transform between different types.
     */
    private final MediaTypeMapper mediaTypeMapper;

    /**
     * Constructor.
     *
     * @param mediaTypeRepository Repository instance of type {@link MediaTypeRepository}.
     * @param mediaTypeMapper Mapper instance of type {@link MediaTypeMapper}.
     */
    public MediaTypeService(
            final MediaTypeRepository mediaTypeRepository, final MediaTypeMapper mediaTypeMapper) {
        this.mediaTypeRepository = mediaTypeRepository;
        this.mediaTypeMapper = mediaTypeMapper;
    }

    /**
     * This method attempts to create an instance of type {@link MediaTypeEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     MediaTypeEntity}.
     * @return An experience model of type {@link MediaType} that represents the newly created
     *     entity of type {@link MediaTypeEntity}.
     */
    @Instrument
    @Transactional
    public MediaType createMediaType(@Valid final CreateMediaTypeRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final MediaTypeEntity mediaTypeEntity = mediaTypeMapper.transform(payload);

        // 2. Save the entity.
        MediaTypeService.LOGGER.debug("Saving a new instance of type - MediaTypeEntity");
        final MediaTypeEntity newInstance = mediaTypeRepository.save(mediaTypeEntity);

        // 3. Transform the created entity to an experience model and return it.
        return mediaTypeMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link MediaTypeEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateMediaTypeRequest}.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MediaType, which needs
     *     to be updated in the system.
     * @return A instance of type {@link MediaType} containing the updated details.
     */
    @Instrument
    @Transactional
    public MediaType updateMediaType(
            final Integer mediaTypeId, @Valid final UpdateMediaTypeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final MediaTypeEntity matchingInstance = mediaTypeRepository.findByIdOrThrow(mediaTypeId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        mediaTypeMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        MediaTypeService.LOGGER.debug("Saving the updated entity - MediaTypeEntity");
        final MediaTypeEntity updatedInstance = mediaTypeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return mediaTypeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link MediaTypeEntity} using the
     * details from the provided input, which is an instance of type {@link PatchMediaTypeRequest}.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MediaType, which needs
     *     to be updated in the system.
     * @return A instance of type {@link MediaType} containing the updated details.
     */
    @Instrument
    @Transactional
    public MediaType patchMediaType(
            final Integer mediaTypeId, @Valid final PatchMediaTypeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final MediaTypeEntity matchingInstance = mediaTypeRepository.findByIdOrThrow(mediaTypeId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(MediaTypeEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        MediaTypeService.LOGGER.debug("Saving the updated entity - MediaTypeEntity");
        final MediaTypeEntity updatedInstance = mediaTypeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return mediaTypeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link MediaTypeEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link MediaType} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public MediaType findMediaType(final Integer mediaTypeId) {
        // 1. Find a matching entity and throw an exception if not found.
        final MediaTypeEntity matchingInstance = mediaTypeRepository.findByIdOrThrow(mediaTypeId);

        // 2. Transform the matching entity to the desired output.
        return mediaTypeMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type MediaTypeEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link MediaType}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<MediaType> findAllMediaTypes(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        MediaTypeService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<MediaTypeEntity> pageData = mediaTypeRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<MediaType> dataToReturn =
                    pageData.getContent().stream()
                            .map(mediaTypeMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link MediaTypeEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type MediaTypeEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteMediaType(final Integer mediaTypeId) {
        // 1. Delegate to our repository method to handle the deletion.
        return mediaTypeRepository.deleteOne(mediaTypeId);
    }
}
