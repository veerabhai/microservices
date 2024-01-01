/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.*;
import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.instrumentation.*;
import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.songtype.*;
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
 * entities of type {@link SongTypeEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class SongTypeService {
    /** Repository implementation of type {@link SongTypeRepository}. */
    private final SongTypeRepository songTypeRepository;

    /**
     * Mapper implementation of type {@link SongTypeMapper} to transform between different types.
     */
    private final SongTypeMapper songTypeMapper;

    /**
     * Constructor.
     *
     * @param songTypeRepository Repository instance of type {@link SongTypeRepository}.
     * @param songTypeMapper Mapper instance of type {@link SongTypeMapper}.
     */
    public SongTypeService(
            final SongTypeRepository songTypeRepository, final SongTypeMapper songTypeMapper) {
        this.songTypeRepository = songTypeRepository;
        this.songTypeMapper = songTypeMapper;
    }

    /**
     * This method attempts to create an instance of type {@link SongTypeEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     SongTypeEntity}.
     * @return An experience model of type {@link SongType} that represents the newly created entity
     *     of type {@link SongTypeEntity}.
     */
    @Instrument
    @Transactional
    public SongType createSongType(@Valid final CreateSongTypeRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final SongTypeEntity songTypeEntity = songTypeMapper.transform(payload);

        // 2. Save the entity.
        SongTypeService.LOGGER.debug("Saving a new instance of type - SongTypeEntity");
        final SongTypeEntity newInstance = songTypeRepository.save(songTypeEntity);

        // 3. Transform the created entity to an experience model and return it.
        return songTypeMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link SongTypeEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateSongTypeRequest}.
     *
     * @param songTypeId Unique identifier of SongType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing SongType, which needs to
     *     be updated in the system.
     * @return A instance of type {@link SongType} containing the updated details.
     */
    @Instrument
    @Transactional
    public SongType updateSongType(
            final String songTypeId, @Valid final UpdateSongTypeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final SongTypeEntity matchingInstance = songTypeRepository.findByIdOrThrow(songTypeId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        songTypeMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        SongTypeService.LOGGER.debug("Saving the updated entity - SongTypeEntity");
        final SongTypeEntity updatedInstance = songTypeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return songTypeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link SongTypeEntity} using the
     * details from the provided input, which is an instance of type {@link PatchSongTypeRequest}.
     *
     * @param songTypeId Unique identifier of SongType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing SongType, which needs to
     *     be updated in the system.
     * @return A instance of type {@link SongType} containing the updated details.
     */
    @Instrument
    @Transactional
    public SongType patchSongType(
            final String songTypeId, @Valid final PatchSongTypeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final SongTypeEntity matchingInstance = songTypeRepository.findByIdOrThrow(songTypeId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(SongTypeEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        SongTypeService.LOGGER.debug("Saving the updated entity - SongTypeEntity");
        final SongTypeEntity updatedInstance = songTypeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return songTypeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link SongTypeEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param songTypeId Unique identifier of SongType in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link SongType} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public SongType findSongType(final String songTypeId) {
        // 1. Find a matching entity and throw an exception if not found.
        final SongTypeEntity matchingInstance = songTypeRepository.findByIdOrThrow(songTypeId);

        // 2. Transform the matching entity to the desired output.
        return songTypeMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type SongTypeEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link SongType}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<SongType> findAllSongTypes(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        SongTypeService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<SongTypeEntity> pageData = songTypeRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<SongType> dataToReturn =
                    pageData.getContent().stream()
                            .map(songTypeMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link SongTypeEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param songTypeId Unique identifier of SongType in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type SongTypeEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteSongType(final String songTypeId) {
        // 1. Delegate to our repository method to handle the deletion.
        return songTypeRepository.deleteOne(songTypeId);
    }
}
