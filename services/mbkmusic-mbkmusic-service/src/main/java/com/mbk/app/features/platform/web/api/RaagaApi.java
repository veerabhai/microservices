/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.features.platform.data.model.experience.raaga.*;
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
 * com.mbk.app.features.platform.data.model.persistence.RaagaEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(RaagaApi.rootEndPoint)
public class RaagaApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Raagas";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link RaagaService}. */
    private final RaagaService raagaService;

    /**
     * Constructor.
     *
     * @param raagaService Service instance of type {@link RaagaService}.
     */
    public RaagaApi(final RaagaService raagaService) {
        this.raagaService = raagaService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.RaagaEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.RaagaEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Raaga}.
     */
    @Operation(
            method = "createRaaga",
            summary = "Create a new Raaga.",
            description = "This API is used to create a new Raaga in the system.",
            tags = {RaagaApi.API_TAG},
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
                        description = "Successfully created a new Raaga in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/raagas")
    public ResponseEntity<Raaga> createRaaga(@Valid @RequestBody final CreateRaagaRequest payload) {
        // Delegate to the service layer.
        final Raaga newInstance = raagaService.createRaaga(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.RaagaEntity} in the system.
     *
     * @param raagaId Unique identifier of Raaga in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Raaga, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Raaga}.
     */
    @Operation(
            method = "updateRaaga",
            summary = "Update an existing Raaga.",
            description = "This API is used to update an existing Raaga in the system.",
            tags = {RaagaApi.API_TAG},
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
                        description = "Successfully updated an existing Raaga in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/raagas/{raagaId}")
    public ResponseEntity<Raaga> updateRaaga(
            @PathVariable(name = "raagaId") final String raagaId,
            @Valid @RequestBody final UpdateRaagaRequest payload) {
        // Delegate to the service layer.
        final Raaga updatedInstance = raagaService.updateRaaga(raagaId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.RaagaEntity} in the system.
     *
     * @param raagaId Unique identifier of Raaga in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Raaga, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Raaga}.
     */
    @Operation(
            method = "patchRaaga",
            summary = "Update an existing Raaga.",
            description = "This API is used to update an existing Raaga in the system.",
            tags = {RaagaApi.API_TAG},
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
                        description = "Successfully updated an existing Raaga in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/raagas/{raagaId}")
    public ResponseEntity<Raaga> patchRaaga(
            @PathVariable(name = "raagaId") final String raagaId,
            @Valid @RequestBody final PatchRaagaRequest payload) {
        // Delegate to the service layer.
        final Raaga updatedInstance = raagaService.patchRaaga(raagaId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.RaagaEntity} in the system.
     *
     * @param raagaId Unique identifier of Raaga in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Raaga}.
     */
    @Operation(
            method = "findRaaga",
            summary = "Find an existing Raaga.",
            description = "This API is used to find an existing Raaga in the system.",
            tags = {RaagaApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Raaga in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/raagas/{raagaId}")
    public ResponseEntity<Raaga> findRaaga(@PathVariable(name = "raagaId") final String raagaId) {
        // Delegate to the service layer.
        final Raaga matchingInstance = raagaService.findRaaga(raagaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.RaagaEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Raaga
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllRaagas",
            summary = "Find all Raagas.",
            description = "This API is used to find all Raagas in the system.",
            tags = {RaagaApi.API_TAG},
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
                                "Successfully retrieved the Raagas in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/raagas")
    public ResponseEntity<Page<Raaga>> findAllRaagas(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20000") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Raaga> matchingInstances = raagaService.findAllRaagas(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.RaagaEntity} in the system.
     *
     * @param raagaId Unique identifier of Raaga in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.RaagaEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteRaaga",
            summary = "Delete an existing Raaga.",
            description = "This API is used to delete an existing Raaga in the system.",
            tags = {RaagaApi.API_TAG},
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
                        description = "Successfully deleted an existing Raaga in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/raagas/{raagaId}")
    public ResponseEntity<String> deleteRaaga(
            @PathVariable(name = "raagaId") final String raagaId) {
        // Delegate to the service layer.
        final String deletedInstance = raagaService.deleteRaaga(raagaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
