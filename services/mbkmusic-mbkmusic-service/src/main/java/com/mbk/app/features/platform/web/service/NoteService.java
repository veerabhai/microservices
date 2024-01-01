/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.*;
import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.instrumentation.*;
import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.note.*;
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
 * entities of type {@link NoteEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class NoteService {
    /** Repository implementation of type {@link NoteRepository}. */
    private final NoteRepository noteRepository;

    /** Mapper implementation of type {@link NoteMapper} to transform between different types. */
    private final NoteMapper noteMapper;

    /**
     * Constructor.
     *
     * @param noteRepository Repository instance of type {@link NoteRepository}.
     * @param noteMapper Mapper instance of type {@link NoteMapper}.
     */
    public NoteService(final NoteRepository noteRepository, final NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    /**
     * This method attempts to create an instance of type {@link NoteEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     NoteEntity}.
     * @return An experience model of type {@link Note} that represents the newly created entity of
     *     type {@link NoteEntity}.
     */
    @Instrument
    @Transactional
    public Note createNote(@Valid final CreateNoteRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final NoteEntity noteEntity = noteMapper.transform(payload);

        // 2. Save the entity.
        NoteService.LOGGER.debug("Saving a new instance of type - NoteEntity");
        final NoteEntity newInstance = noteRepository.save(noteEntity);

        // 3. Transform the created entity to an experience model and return it.
        return noteMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link NoteEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateNoteRequest}.
     *
     * @param noteId Unique identifier of Note in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Note, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Note} containing the updated details.
     */
    @Instrument
    @Transactional
    public Note updateNote(final Integer noteId, @Valid final UpdateNoteRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final NoteEntity matchingInstance = noteRepository.findByIdOrThrow(noteId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        noteMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        NoteService.LOGGER.debug("Saving the updated entity - NoteEntity");
        final NoteEntity updatedInstance = noteRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return noteMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link NoteEntity} using the
     * details from the provided input, which is an instance of type {@link PatchNoteRequest}.
     *
     * @param noteId Unique identifier of Note in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Note, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Note} containing the updated details.
     */
    @Instrument
    @Transactional
    public Note patchNote(final Integer noteId, @Valid final PatchNoteRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final NoteEntity matchingInstance = noteRepository.findByIdOrThrow(noteId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(NoteEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        NoteService.LOGGER.debug("Saving the updated entity - NoteEntity");
        final NoteEntity updatedInstance = noteRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return noteMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link NoteEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param noteId Unique identifier of Note in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Note} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Note findNote(final Integer noteId) {
        // 1. Find a matching entity and throw an exception if not found.
        final NoteEntity matchingInstance = noteRepository.findByIdOrThrow(noteId);

        // 2. Transform the matching entity to the desired output.
        return noteMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type NoteEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Note}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Note> findAllNotes(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        NoteService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<NoteEntity> pageData = noteRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Note> dataToReturn =
                    pageData.getContent().stream()
                            .map(noteMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link NoteEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param noteId Unique identifier of Note in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type NoteEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteNote(final Integer noteId) {
        // 1. Delegate to our repository method to handle the deletion.
        return noteRepository.deleteOne(noteId);
    }
}
