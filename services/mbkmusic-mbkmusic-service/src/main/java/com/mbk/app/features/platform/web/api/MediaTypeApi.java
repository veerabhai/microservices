/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.mediatype.MediaType;
import com.mbk.app.features.platform.data.model.experience.mediatype.*;
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
 * com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(MediaTypeApi.rootEndPoint)
public class MediaTypeApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "MediaTypes";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link MediaTypeService}. */
    private final MediaTypeService mediaTypeService;

    /**
     * Constructor.
     *
     * @param mediaTypeService Service instance of type {@link MediaTypeService}.
     */
    public MediaTypeApi(final MediaTypeService mediaTypeService) {
        this.mediaTypeService = mediaTypeService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaType}.
     */
    @Operation(
            method = "createMediaType",
            summary = "Create a new MediaType.",
            description = "This API is used to create a new MediaType in the system.",
            tags = {MediaTypeApi.API_TAG},
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
                        description = "Successfully created a new MediaType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/mediaTypes")
    public ResponseEntity<MediaType> createMediaType(
            @Valid @RequestBody final CreateMediaTypeRequest payload) {
        // Delegate to the service layer.
        final MediaType newInstance = mediaTypeService.createMediaType(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity} in the system.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MediaType, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaType}.
     */
    @Operation(
            method = "updateMediaType",
            summary = "Update an existing MediaType.",
            description = "This API is used to update an existing MediaType in the system.",
            tags = {MediaTypeApi.API_TAG},
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
                        description = "Successfully updated an existing MediaType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/mediaTypes/{mediaTypeId}")
    public ResponseEntity<MediaType> updateMediaType(
            @PathVariable(name = "mediaTypeId") final Integer mediaTypeId,
            @Valid @RequestBody final UpdateMediaTypeRequest payload) {
        // Delegate to the service layer.
        final MediaType updatedInstance = mediaTypeService.updateMediaType(mediaTypeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity} in the system.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MediaType, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaType}.
     */
    @Operation(
            method = "patchMediaType",
            summary = "Update an existing MediaType.",
            description = "This API is used to update an existing MediaType in the system.",
            tags = {MediaTypeApi.API_TAG},
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
                        description = "Successfully updated an existing MediaType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/mediaTypes/{mediaTypeId}")
    public ResponseEntity<MediaType> patchMediaType(
            @PathVariable(name = "mediaTypeId") final Integer mediaTypeId,
            @Valid @RequestBody final PatchMediaTypeRequest payload) {
        // Delegate to the service layer.
        final MediaType updatedInstance = mediaTypeService.patchMediaType(mediaTypeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity} in the system.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaType}.
     */
    @Operation(
            method = "findMediaType",
            summary = "Find an existing MediaType.",
            description = "This API is used to find an existing MediaType in the system.",
            tags = {MediaTypeApi.API_TAG},
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
                                "Successfully retrieved the details of an existing MediaType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/mediaTypes/{mediaTypeId}")
    public ResponseEntity<MediaType> findMediaType(
            @PathVariable(name = "mediaTypeId") final Integer mediaTypeId) {
        // Delegate to the service layer.
        final MediaType matchingInstance = mediaTypeService.findMediaType(mediaTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     MediaType based on the provided pagination settings.
     */
    @Operation(
            method = "findAllMediaTypes",
            summary = "Find all MediaTypes.",
            description = "This API is used to find all MediaTypes in the system.",
            tags = {MediaTypeApi.API_TAG},
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
                                "Successfully retrieved the MediaTypes in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/mediaTypes")
    public ResponseEntity<Page<MediaType>> findAllMediaTypes(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<MediaType> matchingInstances = mediaTypeService.findAllMediaTypes(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity} in the system.
     *
     * @param mediaTypeId Unique identifier of MediaType in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.MediaTypeEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteMediaType",
            summary = "Delete an existing MediaType.",
            description = "This API is used to delete an existing MediaType in the system.",
            tags = {MediaTypeApi.API_TAG},
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
                        description = "Successfully deleted an existing MediaType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/mediaTypes/{mediaTypeId}")
    public ResponseEntity<Integer> deleteMediaType(
            @PathVariable(name = "mediaTypeId") final Integer mediaTypeId) {
        // Delegate to the service layer.
        final Integer deletedInstance = mediaTypeService.deleteMediaType(mediaTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
