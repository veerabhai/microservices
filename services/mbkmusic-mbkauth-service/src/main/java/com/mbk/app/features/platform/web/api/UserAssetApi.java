/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.utils.CaseFormatUtils;
import com.mbk.app.commons.web.api.AuthAbstractApi;
import com.mbk.app.commons.web.configuration.properties.ApiDocumentationSettings;
import com.mbk.app.features.platform.data.model.experience.userasset.CreateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.PatchUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UpdateUserAssetRequest;
import com.mbk.app.features.platform.data.model.experience.userasset.UserAsset;
import com.mbk.app.features.platform.web.service.UserAssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.mbk.app.features.platform.data.model.persistence.UserAssetEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(UserAssetApi.rootEndPoint)
public class UserAssetApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "UserAssets";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkauth";

    /** Service implementation of type {@link UserAssetService}. */
    private final UserAssetService userAssetService;

    /**
     * Constructor.
     *
     * @param userAssetService Service instance of type {@link UserAssetService}.
     */
    public UserAssetApi(final UserAssetService userAssetService) {
        this.userAssetService = userAssetService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.UserAssetEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAsset}.
     */
    @Operation(
            method = "createUserAsset",
            summary = "Create a new UserAsset.",
            description = "This API is used to create a new UserAsset in the system.",
            tags = {UserAssetApi.API_TAG},
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
                        description = "Successfully created a new UserAsset in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/userAssets")
    public ResponseEntity<UserAsset> createUserAsset(
            @Valid @RequestBody final CreateUserAssetRequest payload) {
        // Delegate to the service layer.
        final UserAsset newInstance = userAssetService.createUserAsset(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetEntity} in the system.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing UserAsset, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAsset}.
     */
    @Operation(
            method = "updateUserAsset",
            summary = "Update an existing UserAsset.",
            description = "This API is used to update an existing UserAsset in the system.",
            tags = {UserAssetApi.API_TAG},
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
                        description = "Successfully updated an existing UserAsset in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/userAssets/{userAssetId}")
    public ResponseEntity<UserAsset> updateUserAsset(
            @PathVariable(name = "userAssetId") final String userAssetId,
            @Valid @RequestBody final UpdateUserAssetRequest payload) {
        // Delegate to the service layer.
        final UserAsset updatedInstance = userAssetService.updateUserAsset(userAssetId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetEntity} in the system.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing UserAsset, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAsset}.
     */
    @Operation(
            method = "patchUserAsset",
            summary = "Update an existing UserAsset.",
            description = "This API is used to update an existing UserAsset in the system.",
            tags = {UserAssetApi.API_TAG},
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
                        description = "Successfully updated an existing UserAsset in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/userAssets/{userAssetId}")
    public ResponseEntity<UserAsset> patchUserAsset(
            @PathVariable(name = "userAssetId") final String userAssetId,
            @Valid @RequestBody final PatchUserAssetRequest payload) {
        // Delegate to the service layer.
        final UserAsset updatedInstance = userAssetService.patchUserAsset(userAssetId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetEntity} in the system.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAsset}.
     */
    @Operation(
            method = "findUserAsset",
            summary = "Find an existing UserAsset.",
            description = "This API is used to find an existing UserAsset in the system.",
            tags = {UserAssetApi.API_TAG},
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
                                "Successfully retrieved the details of an existing UserAsset in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/userAssets/{userAssetId}")
    public ResponseEntity<UserAsset> findUserAsset(
            @PathVariable(name = "userAssetId") final String userAssetId) {
        // Delegate to the service layer.
        final UserAsset matchingInstance = userAssetService.findUserAsset(userAssetId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     UserAsset based on the provided pagination settings.
     */
    @Operation(
            method = "findAllUserAssets",
            summary = "Find all UserAssets.",
            description = "This API is used to find all UserAssets in the system.",
            tags = {UserAssetApi.API_TAG},
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
                                "Successfully retrieved the UserAssets in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/userAssets")
    public ResponseEntity<Page<UserAsset>> findAllUserAssets(
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
                    final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
                    final String direction) {
        // Delegate to the service layer.
        final String toLowerCamelCaseSortBy = CaseFormatUtils.toLowerCamelCase(sortBy);
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(
                        page, size, toLowerCamelCaseSortBy, direction);
        final Page<UserAsset> matchingInstances = userAssetService.findAllUserAssets(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetEntity} in the system.
     *
     * @param userAssetId Unique identifier of UserAsset in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.UserAssetEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteUserAsset",
            summary = "Delete an existing UserAsset.",
            description = "This API is used to delete an existing UserAsset in the system.",
            tags = {UserAssetApi.API_TAG},
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
                        description = "Successfully deleted an existing UserAsset in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/userAssets/{userAssetId}")
    public ResponseEntity<String> deleteUserAsset(
            @PathVariable(name = "userAssetId") final String userAssetId) {
        // Delegate to the service layer.
        final String deletedInstance = userAssetService.deleteUserAsset(userAssetId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
