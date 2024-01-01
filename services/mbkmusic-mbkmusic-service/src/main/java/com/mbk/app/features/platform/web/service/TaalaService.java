/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.*;
import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.exception.*;
import com.mbk.app.commons.instrumentation.*;
import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.taala.*;
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
 * entities of type {@link TaalaEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class TaalaService {
    /** Repository implementation of type {@link TaalaRepository}. */
    private final TaalaRepository taalaRepository;

    /** Mapper implementation of type {@link TaalaMapper} to transform between different types. */
    private final TaalaMapper taalaMapper;

    /**
     * Constructor.
     *
     * @param taalaRepository Repository instance of type {@link TaalaRepository}.
     * @param taalaMapper Mapper instance of type {@link TaalaMapper}.
     */
    public TaalaService(final TaalaRepository taalaRepository, final TaalaMapper taalaMapper) {
        this.taalaRepository = taalaRepository;
        this.taalaMapper = taalaMapper;
    }

    /**
     * This method attempts to create an instance of type {@link TaalaEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     TaalaEntity}.
     * @return An experience model of type {@link Taala} that represents the newly created entity of
     *     type {@link TaalaEntity}.
     */
    @Instrument
    @Transactional
    public Taala createTaala(@Valid final CreateTaalaRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final TaalaEntity taalaEntity = taalaMapper.transform(payload);
        final TaalaEntity matching = taalaRepository.findTaalaByDescription(payload.getDescription());
        if(matching !=null){
            throw ServiceException.instance("Aadi","Taala already exist!");
        }

        // 2. Save the entity.
        TaalaService.LOGGER.debug("Saving a new instance of type - TaalaEntity");
        final TaalaEntity newInstance = taalaRepository.save(taalaEntity);
        // 3. Transform the created entity to an experience model and return it.
        return taalaMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link TaalaEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateTaalaRequest}.
     *
     * @param taalaId Unique identifier of Taala in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Taala, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Taala} containing the updated details.
     */
    @Instrument
    @Transactional
    public Taala updateTaala(final String taalaId, @Valid final UpdateTaalaRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final TaalaEntity matchingInstance = taalaRepository.findByIdOrThrow(taalaId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        taalaMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        TaalaService.LOGGER.debug("Saving the updated entity - TaalaEntity");
        final TaalaEntity updatedInstance = taalaRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return taalaMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link TaalaEntity} using the
     * details from the provided input, which is an instance of type {@link PatchTaalaRequest}.
     *
     * @param taalaId Unique identifier of Taala in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Taala, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Taala} containing the updated details.
     */
    @Instrument
    @Transactional
    public Taala patchTaala(final String taalaId, @Valid final PatchTaalaRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final TaalaEntity matchingInstance = taalaRepository.findByIdOrThrow(taalaId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(TaalaEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        TaalaService.LOGGER.debug("Saving the updated entity - TaalaEntity");
        final TaalaEntity updatedInstance = taalaRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return taalaMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link TaalaEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param taalaId Unique identifier of Taala in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Taala} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Taala findTaala(final String taalaId) {
        // 1. Find a matching entity and throw an exception if not found.
        final TaalaEntity matchingInstance = taalaRepository.findByIdOrThrow(taalaId);

        // 2. Transform the matching entity to the desired output.
        return taalaMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type TaalaEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Taala}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Taala> findAllTaalas(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        TaalaService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<TaalaEntity> pageData = taalaRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Taala> dataToReturn =
                    pageData.getContent().stream()
                            .map(taalaMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link TaalaEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param taalaId Unique identifier of Taala in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type TaalaEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteTaala(final String taalaId) {
        // 1. Delegate to our repository method to handle the deletion.
        return taalaRepository.deleteOne(taalaId);
    }
}
