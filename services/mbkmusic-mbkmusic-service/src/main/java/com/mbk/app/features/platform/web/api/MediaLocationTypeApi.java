/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.medialocationtype.*;
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
 * com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(MediaLocationTypeApi.rootEndPoint)
public class MediaLocationTypeApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "MediaLocationTypes";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link MediaLocationTypeService}. */
    private final MediaLocationTypeService mediaLocationTypeService;

    /**
     * Constructor.
     *
     * @param mediaLocationTypeService Service instance of type {@link MediaLocationTypeService}.
     */
    public MediaLocationTypeApi(final MediaLocationTypeService mediaLocationTypeService) {
        this.mediaLocationTypeService = mediaLocationTypeService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity} into the
     * system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaLocationType}.
     */
    @Operation(
            method = "createMediaLocationType",
            summary = "Create a new MediaLocationType.",
            description = "This API is used to create a new MediaLocationType in the system.",
            tags = {MediaLocationTypeApi.API_TAG},
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
                        description = "Successfully created a new MediaLocationType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/mediaLocationTypes")
    public ResponseEntity<MediaLocationType> createMediaLocationType(
            @Valid @RequestBody final CreateMediaLocationTypeRequest payload) {
        // Delegate to the service layer.
        final MediaLocationType newInstance =
                mediaLocationTypeService.createMediaLocationType(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity} in the system.
     *
     * @param mediaLocationTypeId Unique identifier of MediaLocationType in the system, which needs
     *     to be updated.
     * @param payload Request payload containing the details of an existing MediaLocationType, which
     *     needs to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaLocationType}.
     */
    @Operation(
            method = "updateMediaLocationType",
            summary = "Update an existing MediaLocationType.",
            description = "This API is used to update an existing MediaLocationType in the system.",
            tags = {MediaLocationTypeApi.API_TAG},
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
                                "Successfully updated an existing MediaLocationType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/mediaLocationTypes/{mediaLocationTypeId}")
    public ResponseEntity<MediaLocationType> updateMediaLocationType(
            @PathVariable(name = "mediaLocationTypeId") final Integer mediaLocationTypeId,
            @Valid @RequestBody final UpdateMediaLocationTypeRequest payload) {
        // Delegate to the service layer.
        final MediaLocationType updatedInstance =
                mediaLocationTypeService.updateMediaLocationType(mediaLocationTypeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity} in the system.
     *
     * @param mediaLocationTypeId Unique identifier of MediaLocationType in the system, which needs
     *     to be updated.
     * @param payload Request payload containing the details of an existing MediaLocationType, which
     *     needs to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaLocationType}.
     */
    @Operation(
            method = "patchMediaLocationType",
            summary = "Update an existing MediaLocationType.",
            description = "This API is used to update an existing MediaLocationType in the system.",
            tags = {MediaLocationTypeApi.API_TAG},
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
                                "Successfully updated an existing MediaLocationType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/mediaLocationTypes/{mediaLocationTypeId}")
    public ResponseEntity<MediaLocationType> patchMediaLocationType(
            @PathVariable(name = "mediaLocationTypeId") final Integer mediaLocationTypeId,
            @Valid @RequestBody final PatchMediaLocationTypeRequest payload) {
        // Delegate to the service layer.
        final MediaLocationType updatedInstance =
                mediaLocationTypeService.patchMediaLocationType(mediaLocationTypeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity} in the system.
     *
     * @param mediaLocationTypeId Unique identifier of MediaLocationType in the system, whose
     *     details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MediaLocationType}.
     */
    @Operation(
            method = "findMediaLocationType",
            summary = "Find an existing MediaLocationType.",
            description = "This API is used to find an existing MediaLocationType in the system.",
            tags = {MediaLocationTypeApi.API_TAG},
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
                                "Successfully retrieved the details of an existing MediaLocationType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/mediaLocationTypes/{mediaLocationTypeId}")
    public ResponseEntity<MediaLocationType> findMediaLocationType(
            @PathVariable(name = "mediaLocationTypeId") final Integer mediaLocationTypeId) {
        // Delegate to the service layer.
        final MediaLocationType matchingInstance =
                mediaLocationTypeService.findMediaLocationType(mediaLocationTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity} in the system
     * in a paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     MediaLocationType based on the provided pagination settings.
     */
    @Operation(
            method = "findAllMediaLocationTypes",
            summary = "Find all MediaLocationTypes.",
            description = "This API is used to find all MediaLocationTypes in the system.",
            tags = {MediaLocationTypeApi.API_TAG},
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
                                "Successfully retrieved the MediaLocationTypes in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/mediaLocationTypes")
    public ResponseEntity<Page<MediaLocationType>> findAllMediaLocationTypes(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<MediaLocationType> matchingInstances =
                mediaLocationTypeService.findAllMediaLocationTypes(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity} in the system.
     *
     * @param mediaLocationTypeId Unique identifier of MediaLocationType in the system, which needs
     *     to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.MediaLocationTypeEntity} that
     *     was deleted from the system.
     */
    @Operation(
            method = "deleteMediaLocationType",
            summary = "Delete an existing MediaLocationType.",
            description = "This API is used to delete an existing MediaLocationType in the system.",
            tags = {MediaLocationTypeApi.API_TAG},
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
                                "Successfully deleted an existing MediaLocationType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/mediaLocationTypes/{mediaLocationTypeId}")
    public ResponseEntity<Integer> deleteMediaLocationType(
            @PathVariable(name = "mediaLocationTypeId") final Integer mediaLocationTypeId) {
        // Delegate to the service layer.
        final Integer deletedInstance =
                mediaLocationTypeService.deleteMediaLocationType(mediaLocationTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
