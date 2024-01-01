/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.*;
import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.instrumentation.*;
import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.media.*;
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
 * entities of type {@link MediaEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class MediaService {
    /**
     * Repository implementation of type {@link MediaRepository}.
     */
    private final MediaRepository mediaRepository;

    /**
     * Mapper implementation of type {@link MediaMapper} to transform between different types.
     */
    private final MediaMapper mediaMapper;

    /**
     * Constructor.
     *
     * @param mediaRepository Repository instance of type {@link MediaRepository}.
     * @param mediaMapper     Mapper instance of type {@link MediaMapper}.
     */
    public MediaService(final MediaRepository mediaRepository, final MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    /**
     * This method attempts to create an instance of type {@link MediaEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *                MediaEntity}.
     * @return An experience model of type {@link Media} that represents the newly created entity of
     * type {@link MediaEntity}.
     */
    @Instrument
    @Transactional
    public Media createMedia(@Valid final CreateMediaRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final MediaEntity mediaEntity = mediaMapper.transform(payload);

        // 2. Save the entity.
        MediaService.LOGGER.debug("Saving a new instance of type - MediaEntity");
        final MediaEntity newInstance = mediaRepository.save(mediaEntity);

        // 3. Transform the created entity to an experience model and return it.
        return mediaMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link MediaEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateMediaRequest}.
     *
     * @param mediaId Unique identifier of Media in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Media, which needs to be
     *                updated in the system.
     * @return A instance of type {@link Media} containing the updated details.
     */
    @Instrument
    @Transactional
    public Media updateMedia(final Integer mediaId, @Valid final UpdateMediaRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final MediaEntity matchingInstance = mediaRepository.findByIdOrThrow(mediaId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        mediaMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        MediaService.LOGGER.debug("Saving the updated entity - MediaEntity");
        final MediaEntity updatedInstance = mediaRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return mediaMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link MediaEntity} using the
     * details from the provided input, which is an instance of type {@link PatchMediaRequest}.
     *
     * @param mediaId Unique identifier of Media in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Media, which needs to be
     *                updated in the system.
     * @return A instance of type {@link Media} containing the updated details.
     */
    @Instrument
    @Transactional
    public Media patchMedia(final Integer mediaId, @Valid final PatchMediaRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final MediaEntity matchingInstance = mediaRepository.findByIdOrThrow(mediaId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(MediaEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        MediaService.LOGGER.debug("Saving the updated entity - MediaEntity");
        final MediaEntity updatedInstance = mediaRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return mediaMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link MediaEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param mediaId Unique identifier of Media in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Media} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Media findMedia(final Integer mediaId) {
        // 1. Find a matching entity and throw an exception if not found.
        final MediaEntity matchingInstance = mediaRepository.findByIdOrThrow(mediaId);

        // 2. Transform the matching entity to the desired output.
        return mediaMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type MediaEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     * returned page is an instance of type {@link Media}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Media> findAllMedias(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
       /* MediaService.LOGGER.info(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());*/
        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<MediaEntity> pageData = mediaRepository.findAll(pageSettings);
        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Media> dataToReturn =
                    pageData.getContent().stream().filter(mediaEntity -> {

                                if (Objects.nonNull(mediaEntity.getUrl()))
                                    return true;
                                else
                                    return false;
                            })
                            .map(mediaMapper::transform)
                            .collect(Collectors.toList());
            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link MediaEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param mediaId Unique identifier of Media in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type MediaEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteMedia(final Integer mediaId) {
        // 1. Delegate to our repository method to handle the deletion.
        return mediaRepository.deleteOne(mediaId);
    }


}
