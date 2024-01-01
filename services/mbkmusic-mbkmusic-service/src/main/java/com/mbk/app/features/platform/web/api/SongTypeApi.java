/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.songtype.*;
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
 * com.mbk.app.features.platform.data.model.persistence.SongTypeEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(SongTypeApi.rootEndPoint)
public class SongTypeApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "SongTypes";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link SongTypeService}. */
    private final SongTypeService songTypeService;

    /**
     * Constructor.
     *
     * @param songTypeService Service instance of type {@link SongTypeService}.
     */
    public SongTypeApi(final SongTypeService songTypeService) {
        this.songTypeService = songTypeService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongTypeEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.SongTypeEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     SongType}.
     */
    @Operation(
            method = "createSongType",
            summary = "Create a new SongType.",
            description = "This API is used to create a new SongType in the system.",
            tags = {SongTypeApi.API_TAG},
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
                        description = "Successfully created a new SongType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/songTypes")
    public ResponseEntity<SongType> createSongType(
            @Valid @RequestBody final CreateSongTypeRequest payload) {
        // Delegate to the service layer.
        final SongType newInstance = songTypeService.createSongType(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongTypeEntity} in the system.
     *
     * @param songTypeId Unique identifier of SongType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing SongType, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     SongType}.
     */
    @Operation(
            method = "updateSongType",
            summary = "Update an existing SongType.",
            description = "This API is used to update an existing SongType in the system.",
            tags = {SongTypeApi.API_TAG},
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
                        description = "Successfully updated an existing SongType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/songTypes/{songTypeId}")
    public ResponseEntity<SongType> updateSongType(
            @PathVariable(name = "songTypeId") final String songTypeId,
            @Valid @RequestBody final UpdateSongTypeRequest payload) {
        // Delegate to the service layer.
        final SongType updatedInstance = songTypeService.updateSongType(songTypeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongTypeEntity} in the system.
     *
     * @param songTypeId Unique identifier of SongType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing SongType, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     SongType}.
     */
    @Operation(
            method = "patchSongType",
            summary = "Update an existing SongType.",
            description = "This API is used to update an existing SongType in the system.",
            tags = {SongTypeApi.API_TAG},
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
                        description = "Successfully updated an existing SongType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/songTypes/{songTypeId}")
    public ResponseEntity<SongType> patchSongType(
            @PathVariable(name = "songTypeId") final String songTypeId,
            @Valid @RequestBody final PatchSongTypeRequest payload) {
        // Delegate to the service layer.
        final SongType updatedInstance = songTypeService.patchSongType(songTypeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.SongTypeEntity} in the system.
     *
     * @param songTypeId Unique identifier of SongType in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     SongType}.
     */
    @Operation(
            method = "findSongType",
            summary = "Find an existing SongType.",
            description = "This API is used to find an existing SongType in the system.",
            tags = {SongTypeApi.API_TAG},
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
                                "Successfully retrieved the details of an existing SongType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/songTypes/{songTypeId}")
    public ResponseEntity<SongType> findSongType(
            @PathVariable(name = "songTypeId") final String songTypeId) {
        // Delegate to the service layer.
        final SongType matchingInstance = songTypeService.findSongType(songTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongTypeEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     SongType based on the provided pagination settings.
     */
    @Operation(
            method = "findAllSongTypes",
            summary = "Find all SongTypes.",
            description = "This API is used to find all SongTypes in the system.",
            tags = {SongTypeApi.API_TAG},
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
                                "Successfully retrieved the SongTypes in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/songTypes")
    public ResponseEntity<Page<SongType>> findAllSongTypes(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<SongType> matchingInstances = songTypeService.findAllSongTypes(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongTypeEntity} in the system.
     *
     * @param songTypeId Unique identifier of SongType in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.SongTypeEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteSongType",
            summary = "Delete an existing SongType.",
            description = "This API is used to delete an existing SongType in the system.",
            tags = {SongTypeApi.API_TAG},
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
                        description = "Successfully deleted an existing SongType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/songTypes/{songTypeId}")
    public ResponseEntity<String> deleteSongType(
            @PathVariable(name = "songTypeId") final String songTypeId) {
        // Delegate to the service layer.
        final String deletedInstance = songTypeService.deleteSongType(songTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
