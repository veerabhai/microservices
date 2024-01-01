/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.media.*;
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
 * com.mbk.app.features.platform.data.model.persistence.MediaEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(MediaApi.rootEndPoint)
public class MediaApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Medias";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link MediaService}. */
    private final MediaService mediaService;

    /**
     * Constructor.
     *
     * @param mediaService Service instance of type {@link MediaService}.
     */
    public MediaApi(final MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.MediaEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Media}.
     */
    @Operation(
            method = "createMedia",
            summary = "Create a new Media.",
            description = "This API is used to create a new Media in the system.",
            tags = {MediaApi.API_TAG},
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
                        description = "Successfully created a new Media in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/medias")
    public ResponseEntity<Media> createMedia(@Valid @RequestBody final CreateMediaRequest payload) {
        // Delegate to the service layer.
        final Media newInstance = mediaService.createMedia(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaEntity} in the system.
     *
     * @param mediaId Unique identifier of Media in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Media, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Media}.
     */
    @Operation(
            method = "updateMedia",
            summary = "Update an existing Media.",
            description = "This API is used to update an existing Media in the system.",
            tags = {MediaApi.API_TAG},
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
                        description = "Successfully updated an existing Media in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/medias/{mediaId}")
    public ResponseEntity<Media> updateMedia(
            @PathVariable(name = "mediaId") final Integer mediaId,
            @Valid @RequestBody final UpdateMediaRequest payload) {
        // Delegate to the service layer.
        final Media updatedInstance = mediaService.updateMedia(mediaId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaEntity} in the system.
     *
     * @param mediaId Unique identifier of Media in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Media, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Media}.
     */
    @Operation(
            method = "patchMedia",
            summary = "Update an existing Media.",
            description = "This API is used to update an existing Media in the system.",
            tags = {MediaApi.API_TAG},
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
                        description = "Successfully updated an existing Media in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/medias/{mediaId}")
    public ResponseEntity<Media> patchMedia(
            @PathVariable(name = "mediaId") final Integer mediaId,
            @Valid @RequestBody final PatchMediaRequest payload) {
        // Delegate to the service layer.
        final Media updatedInstance = mediaService.patchMedia(mediaId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaEntity} in the system.
     *
     * @param mediaId Unique identifier of Media in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Media}.
     */
    @Operation(
            method = "findMedia",
            summary = "Find an existing Media.",
            description = "This API is used to find an existing Media in the system.",
            tags = {MediaApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Media in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/medias/{mediaId}")
    public ResponseEntity<Media> findMedia(@PathVariable(name = "mediaId") final Integer mediaId) {
        // Delegate to the service layer.
        final Media matchingInstance = mediaService.findMedia(mediaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Media
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllMedias",
            summary = "Find all Medias.",
            description = "This API is used to find all Medias in the system.",
            tags = {MediaApi.API_TAG},
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
                                    "Successfully retrieved the Medias in the system based on the provided pagination settings.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/medias")
    public ResponseEntity<Page<Media>> findAllMedias(
            @RequestParam(name = "sortBy", required = false, defaultValue = "songId.title")
            final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20000") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
            final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Media> matchingInstances = mediaService.findAllMedias(pageSettings);
        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaEntity} in the system.
     *
     * @param mediaId Unique identifier of Media in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.MediaEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteMedia",
            summary = "Delete an existing Media.",
            description = "This API is used to delete an existing Media in the system.",
            tags = {MediaApi.API_TAG},
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
                        description = "Successfully deleted an existing Media in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/medias/{mediaId}")
    public ResponseEntity<Integer> deleteMedia(
            @PathVariable(name = "mediaId") final Integer mediaId) {
        // Delegate to the service layer.
        final Integer deletedInstance = mediaService.deleteMedia(mediaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }


}
