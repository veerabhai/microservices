/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.note.*;
import com.mbk.app.features.platform.web.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.mbk.app.features.platform.data.model.persistence.NoteEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(NoteApi.rootEndPoint)
public class NoteApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Notes";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link NoteService}. */
    private final NoteService noteService;

    /**
     * Constructor.
     *
     * @param noteService Service instance of type {@link NoteService}.
     */
    public NoteApi(final NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.NoteEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.NoteEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Note}.
     */
    @Operation(
            method = "createNote",
            summary = "Create a new Note.",
            description = "This API is used to create a new Note in the system.",
            tags = {NoteApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successfully created a new Note in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/notes")
    public ResponseEntity<Note> createNote(@Valid @RequestBody final CreateNoteRequest payload) {
        // Delegate to the service layer.
        final Note newInstance = noteService.createNote(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.NoteEntity} in the system.
     *
     * @param noteId Unique identifier of Note in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Note, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Note}.
     */
    @Operation(
            method = "updateNote",
            summary = "Update an existing Note.",
            description = "This API is used to update an existing Note in the system.",
            tags = {NoteApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Note in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/notes/{noteId}")
    public ResponseEntity<Note> updateNote(
            @PathVariable(name = "noteId") final Integer noteId,
            @Valid @RequestBody final UpdateNoteRequest payload) {
        // Delegate to the service layer.
        final Note updatedInstance = noteService.updateNote(noteId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.NoteEntity} in the system.
     *
     * @param noteId Unique identifier of Note in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Note, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Note}.
     */
    @Operation(
            method = "patchNote",
            summary = "Update an existing Note.",
            description = "This API is used to update an existing Note in the system.",
            tags = {NoteApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Note in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/notes/{noteId}")
    public ResponseEntity<Note> patchNote(
            @PathVariable(name = "noteId") final Integer noteId,
            @Valid @RequestBody final PatchNoteRequest payload) {
        // Delegate to the service layer.
        final Note updatedInstance = noteService.patchNote(noteId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.NoteEntity} in the system.
     *
     * @param noteId Unique identifier of Note in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Note}.
     */
    @Operation(
            method = "findNote",
            summary = "Find an existing Note.",
            description = "This API is used to find an existing Note in the system.",
            tags = {NoteApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the details of an existing Note in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/notes/{noteId}")
    public ResponseEntity<Note> findNote(@PathVariable(name = "noteId") final Integer noteId) {
        // Delegate to the service layer.
        final Note matchingInstance = noteService.findNote(noteId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.NoteEntity} in the system in a paginated
     * manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Note
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllNotes",
            summary = "Find all Notes.",
            description = "This API is used to find all Notes in the system.",
            tags = {NoteApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the Notes in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/notes")
    public ResponseEntity<Page<Note>> findAllNotes(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Note> matchingInstances = noteService.findAllNotes(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.NoteEntity} in the system.
     *
     * @param noteId Unique identifier of Note in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.NoteEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteNote",
            summary = "Delete an existing Note.",
            description = "This API is used to delete an existing Note in the system.",
            tags = {NoteApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully deleted an existing Note in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/notes/{noteId}")
    public ResponseEntity<Integer> deleteNote(@PathVariable(name = "noteId") final Integer noteId) {
        // Delegate to the service layer.
        final Integer deletedInstance = noteService.deleteNote(noteId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
