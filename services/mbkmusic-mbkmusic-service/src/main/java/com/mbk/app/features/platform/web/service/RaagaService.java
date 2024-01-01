/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.*;
import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.exception.*;
import com.mbk.app.commons.instrumentation.*;
import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.raaga.*;
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
 * entities of type {@link RaagaEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class RaagaService {
    /** Repository implementation of type {@link RaagaRepository}. */
    private final RaagaRepository raagaRepository;

    /** Mapper implementation of type {@link RaagaMapper} to transform between different types. */
    private final RaagaMapper raagaMapper;

    /**
     * Constructor.
     *
     * @param raagaRepository Repository instance of type {@link RaagaRepository}.
     * @param raagaMapper Mapper instance of type {@link RaagaMapper}.
     */
    public RaagaService(final RaagaRepository raagaRepository, final RaagaMapper raagaMapper) {
        this.raagaRepository = raagaRepository;
        this.raagaMapper = raagaMapper;
    }

    /**
     * This method attempts to create an instance of type {@link RaagaEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     RaagaEntity}.
     * @return An experience model of type {@link Raaga} that represents the newly created entity of
     *     type {@link RaagaEntity}.
     */
    @Instrument
    @Transactional
    public Raaga createRaaga(@Valid final CreateRaagaRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final RaagaEntity raagaEntity = raagaMapper.transform(payload);
        final RaagaEntity matching = raagaRepository.findByDescription(payload.getDescription());
        if(matching !=null){
            throw ServiceException.instance("Abhogi","Raaga already exist!");
        }
        // 2. Save the entity.
        RaagaService.LOGGER.debug("Saving a new instance of type - RaagaEntity");
        final RaagaEntity newInstance = raagaRepository.save(raagaEntity);
        // 3. Transform the created entity to an experience model and return it.
        return raagaMapper.transform(newInstance);
    }
    /**
     * This method attempts to update an existing instance of type {@link RaagaEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateRaagaRequest}.
     *
     * @param raagaId Unique identifier of Raaga in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Raaga, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Raaga} containing the updated details.
     */
    @Instrument
    @Transactional
    public Raaga updateRaaga(final String raagaId, @Valid final UpdateRaagaRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final RaagaEntity matchingInstance = raagaRepository.findByIdOrThrow(raagaId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        raagaMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        RaagaService.LOGGER.debug("Saving the updated entity - RaagaEntity");
        final RaagaEntity updatedInstance = raagaRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return raagaMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link RaagaEntity} using the
     * details from the provided input, which is an instance of type {@link PatchRaagaRequest}.
     *
     * @param raagaId Unique identifier of Raaga in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Raaga, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Raaga} containing the updated details.
     */
    @Instrument
    @Transactional
    public Raaga patchRaaga(final String raagaId, @Valid final PatchRaagaRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final RaagaEntity matchingInstance = raagaRepository.findByIdOrThrow(raagaId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(RaagaEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        RaagaService.LOGGER.debug("Saving the updated entity - RaagaEntity");
        final RaagaEntity updatedInstance = raagaRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return raagaMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link RaagaEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param raagaId Unique identifier of Raaga in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Raaga} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Raaga findRaaga(final String raagaId) {
        // 1. Find a matching entity and throw an exception if not found.
        final RaagaEntity matchingInstance = raagaRepository.findByIdOrThrow(raagaId);

        // 2. Transform the matching entity to the desired output.
        return raagaMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type RaagaEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Raaga}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Raaga> findAllRaagas(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        RaagaService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<RaagaEntity> pageData = raagaRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Raaga> dataToReturn =
                    pageData.getContent().stream()
                            .map(raagaMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link RaagaEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param raagaId Unique identifier of Raaga in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type RaagaEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteRaaga(final String raagaId) {
        // 1. Delegate to our repository method to handle the deletion.
        return raagaRepository.deleteOne(raagaId);
    }
}
