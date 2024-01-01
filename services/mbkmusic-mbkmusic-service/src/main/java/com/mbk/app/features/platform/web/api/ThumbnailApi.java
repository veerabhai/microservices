/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.thumbnail.*;
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
 * com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(ThumbnailApi.rootEndPoint)
public class ThumbnailApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Thumbnails";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link ThumbnailService}. */
    private final ThumbnailService thumbnailService;

    /**
     * Constructor.
     *
     * @param thumbnailService Service instance of type {@link ThumbnailService}.
     */
    public ThumbnailApi(final ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Thumbnail}.
     */
    @Operation(
            method = "createThumbnail",
            summary = "Create a new Thumbnail.",
            description = "This API is used to create a new Thumbnail in the system.",
            tags = {ThumbnailApi.API_TAG},
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
                        description = "Successfully created a new Thumbnail in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/thumbnails")
    public ResponseEntity<Thumbnail> createThumbnail(
            @Valid @RequestBody final CreateThumbnailRequest payload) {
        // Delegate to the service layer.
        final Thumbnail newInstance = thumbnailService.createThumbnail(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity} in the system.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Thumbnail, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Thumbnail}.
     */
    @Operation(
            method = "updateThumbnail",
            summary = "Update an existing Thumbnail.",
            description = "This API is used to update an existing Thumbnail in the system.",
            tags = {ThumbnailApi.API_TAG},
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
                        description = "Successfully updated an existing Thumbnail in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/thumbnails/{thumbnailId}")
    public ResponseEntity<Thumbnail> updateThumbnail(
            @PathVariable(name = "thumbnailId") final Integer thumbnailId,
            @Valid @RequestBody final UpdateThumbnailRequest payload) {
        // Delegate to the service layer.
        final Thumbnail updatedInstance = thumbnailService.updateThumbnail(thumbnailId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity} in the system.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Thumbnail, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Thumbnail}.
     */
    @Operation(
            method = "patchThumbnail",
            summary = "Update an existing Thumbnail.",
            description = "This API is used to update an existing Thumbnail in the system.",
            tags = {ThumbnailApi.API_TAG},
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
                        description = "Successfully updated an existing Thumbnail in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/thumbnails/{thumbnailId}")
    public ResponseEntity<Thumbnail> patchThumbnail(
            @PathVariable(name = "thumbnailId") final Integer thumbnailId,
            @Valid @RequestBody final PatchThumbnailRequest payload) {
        // Delegate to the service layer.
        final Thumbnail updatedInstance = thumbnailService.patchThumbnail(thumbnailId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity} in the system.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Thumbnail}.
     */
    @Operation(
            method = "findThumbnail",
            summary = "Find an existing Thumbnail.",
            description = "This API is used to find an existing Thumbnail in the system.",
            tags = {ThumbnailApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Thumbnail in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/thumbnails/{thumbnailId}")
    public ResponseEntity<Thumbnail> findThumbnail(
            @PathVariable(name = "thumbnailId") final Integer thumbnailId) {
        // Delegate to the service layer.
        final Thumbnail matchingInstance = thumbnailService.findThumbnail(thumbnailId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Thumbnail based on the provided pagination settings.
     */
    @Operation(
            method = "findAllThumbnails",
            summary = "Find all Thumbnails.",
            description = "This API is used to find all Thumbnails in the system.",
            tags = {ThumbnailApi.API_TAG},
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
                                "Successfully retrieved the Thumbnails in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/thumbnails")
    public ResponseEntity<Page<Thumbnail>> findAllThumbnails(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Thumbnail> matchingInstances = thumbnailService.findAllThumbnails(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity} in the system.
     *
     * @param thumbnailId Unique identifier of Thumbnail in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.ThumbnailEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteThumbnail",
            summary = "Delete an existing Thumbnail.",
            description = "This API is used to delete an existing Thumbnail in the system.",
            tags = {ThumbnailApi.API_TAG},
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
                        description = "Successfully deleted an existing Thumbnail in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/thumbnails/{thumbnailId}")
    public ResponseEntity<Integer> deleteThumbnail(
            @PathVariable(name = "thumbnailId") final Integer thumbnailId) {
        // Delegate to the service layer.
        final Integer deletedInstance = thumbnailService.deleteThumbnail(thumbnailId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
