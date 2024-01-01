/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.taala.*;
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
 * com.mbk.app.features.platform.data.model.persistence.TaalaEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(TaalaApi.rootEndPoint)
public class TaalaApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Taalas";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link TaalaService}. */
    private final TaalaService taalaService;

    /**
     * Constructor.
     *
     * @param taalaService Service instance of type {@link TaalaService}.
     */
    public TaalaApi(final TaalaService taalaService) {
        this.taalaService = taalaService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.TaalaEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.TaalaEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Taala}.
     */
    @Operation(
            method = "createTaala",
            summary = "Create a new Taala.",
            description = "This API is used to create a new Taala in the system.",
            tags = {TaalaApi.API_TAG},
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
                        description = "Successfully created a new Taala in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/taalas")
    public ResponseEntity<Taala> createTaala(@Valid @RequestBody final CreateTaalaRequest payload) {
        // Delegate to the service layer.
        final Taala newInstance = taalaService.createTaala(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.TaalaEntity} in the system.
     *
     * @param taalaId Unique identifier of Taala in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Taala, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Taala}.
     */
    @Operation(
            method = "updateTaala",
            summary = "Update an existing Taala.",
            description = "This API is used to update an existing Taala in the system.",
            tags = {TaalaApi.API_TAG},
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
                        description = "Successfully updated an existing Taala in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/taalas/{taalaId}")
    public ResponseEntity<Taala> updateTaala(
            @PathVariable(name = "taalaId") final String taalaId,
            @Valid @RequestBody final UpdateTaalaRequest payload) {
        // Delegate to the service layer.
        final Taala updatedInstance = taalaService.updateTaala(taalaId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.TaalaEntity} in the system.
     *
     * @param taalaId Unique identifier of Taala in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Taala, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Taala}.
     */
    @Operation(
            method = "patchTaala",
            summary = "Update an existing Taala.",
            description = "This API is used to update an existing Taala in the system.",
            tags = {TaalaApi.API_TAG},
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
                        description = "Successfully updated an existing Taala in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/taalas/{taalaId}")
    public ResponseEntity<Taala> patchTaala(
            @PathVariable(name = "taalaId") final String taalaId,
            @Valid @RequestBody final PatchTaalaRequest payload) {
        // Delegate to the service layer.
        final Taala updatedInstance = taalaService.patchTaala(taalaId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.TaalaEntity} in the system.
     *
     * @param taalaId Unique identifier of Taala in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Taala}.
     */
    @Operation(
            method = "findTaala",
            summary = "Find an existing Taala.",
            description = "This API is used to find an existing Taala in the system.",
            tags = {TaalaApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Taala in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/taalas/{taalaId}")
    public ResponseEntity<Taala> findTaala(@PathVariable(name = "taalaId") final String taalaId) {
        // Delegate to the service layer.
        final Taala matchingInstance = taalaService.findTaala(taalaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.TaalaEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Taala
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllTaalas",
            summary = "Find all Taalas.",
            description = "This API is used to find all Taalas in the system.",
            tags = {TaalaApi.API_TAG},
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
                                "Successfully retrieved the Taalas in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/taalas")
    public ResponseEntity<Page<Taala>> findAllTaalas(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20000") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Taala> matchingInstances = taalaService.findAllTaalas(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.TaalaEntity} in the system.
     *
     * @param taalaId Unique identifier of Taala in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.TaalaEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteTaala",
            summary = "Delete an existing Taala.",
            description = "This API is used to delete an existing Taala in the system.",
            tags = {TaalaApi.API_TAG},
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
                        description = "Successfully deleted an existing Taala in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/taalas/{taalaId}")
    public ResponseEntity<String> deleteTaala(
            @PathVariable(name = "taalaId") final String taalaId) {
        // Delegate to the service layer.
        final String deletedInstance = taalaService.deleteTaala(taalaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
