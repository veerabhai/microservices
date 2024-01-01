/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.*;
import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.instrumentation.*;
import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.thumbnail.*;
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
 * entities of type {@link ThumbnailEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class ThumbnailService {
    /** Repository implementation of type {@link ThumbnailRepository}. */
    private final ThumbnailRepository thumbnailRepository;

    /**
     * Mapper implementation of type {@link ThumbnailMapper} to transform between different types.
     */
    private final ThumbnailMapper thumbnailMapper;

    /**
     * Constructor.
     *
     * @param thumbnailRepository Repository instance of type {@link ThumbnailRepository}.
     * @param thumbnailMapper Mapper instance of type {@link ThumbnailMapper}.
     */
    public ThumbnailService(
            final ThumbnailRepository thumbnailRepository, final ThumbnailMapper thumbnailMapper) {
        this.thumbnailRepository = thumbnailRepository;
        this.thumbnailMapper = thumbnailMapper;
    }

    /**
     * This method attempts to create an instance of type {@link ThumbnailEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     ThumbnailEntity}.
     * @return An experience model of type {@link Thumbnail} that represents the newly created
     *     entity of type {@link ThumbnailEntity}.
     */
    @Instrument
    @Transactional
    public Thumbnail createThumbnail(@Valid final CreateThumbnailRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final ThumbnailEntity thumbnailEntity = thumbnailMapper.transform(payload);

        // 2. Save the entity.
        ThumbnailService.LOGGER.debug("Saving a new instance of type - ThumbnailEntity");
        final ThumbnailEntity newInstance = thumbnailRepository.save(thumbnailEntity);

        // 3. Transform the created entity to an experience model and return it.
        return thumbnailMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link ThumbnailEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateThumbnailRequest}.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Thumbnail, which needs
     *     to be updated in the system.
     * @return A instance of type {@link Thumbnail} containing the updated details.
     */
    @Instrument
    @Transactional
    public Thumbnail updateThumbnail(
            final Integer thumbnailId, @Valid final UpdateThumbnailRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final ThumbnailEntity matchingInstance = thumbnailRepository.findByIdOrThrow(thumbnailId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        thumbnailMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        ThumbnailService.LOGGER.debug("Saving the updated entity - ThumbnailEntity");
        final ThumbnailEntity updatedInstance = thumbnailRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return thumbnailMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link ThumbnailEntity} using the
     * details from the provided input, which is an instance of type {@link PatchThumbnailRequest}.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Thumbnail, which needs
     *     to be updated in the system.
     * @return A instance of type {@link Thumbnail} containing the updated details.
     */
    @Instrument
    @Transactional
    public Thumbnail patchThumbnail(
            final Integer thumbnailId, @Valid final PatchThumbnailRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final ThumbnailEntity matchingInstance = thumbnailRepository.findByIdOrThrow(thumbnailId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(ThumbnailEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        ThumbnailService.LOGGER.debug("Saving the updated entity - ThumbnailEntity");
        final ThumbnailEntity updatedInstance = thumbnailRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return thumbnailMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link ThumbnailEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Thumbnail} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Thumbnail findThumbnail(final Integer thumbnailId) {
        // 1. Find a matching entity and throw an exception if not found.
        final ThumbnailEntity matchingInstance = thumbnailRepository.findByIdOrThrow(thumbnailId);

        // 2. Transform the matching entity to the desired output.
        return thumbnailMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type ThumbnailEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Thumbnail}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Thumbnail> findAllThumbnails(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        ThumbnailService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<ThumbnailEntity> pageData = thumbnailRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Thumbnail> dataToReturn =
                    pageData.getContent().stream()
                            .map(thumbnailMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link ThumbnailEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type ThumbnailEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteThumbnail(final Integer thumbnailId) {
        // 1. Delegate to our repository method to handle the deletion.
        return thumbnailRepository.deleteOne(thumbnailId);
    }
}
