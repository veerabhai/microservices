/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.utils.CaseFormatUtils;
import com.mbk.app.commons.web.api.AuthAbstractApi;
import com.mbk.app.commons.web.configuration.properties.ApiDocumentationSettings;
import com.mbk.app.features.platform.data.model.experience.userassettype.CreateUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.PatchUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.UpdateUserAssetTypeRequest;
import com.mbk.app.features.platform.data.model.experience.userassettype.UserAssetType;
import com.mbk.app.features.platform.web.service.UserAssetTypeService;
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
 * com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(UserAssetTypeApi.rootEndPoint)
public class UserAssetTypeApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "UserAssetTypes";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkauth";

    /** Service implementation of type {@link UserAssetTypeService}. */
    private final UserAssetTypeService userAssetTypeService;

    /**
     * Constructor.
     *
     * @param userAssetTypeService Service instance of type {@link UserAssetTypeService}.
     */
    public UserAssetTypeApi(final UserAssetTypeService userAssetTypeService) {
        this.userAssetTypeService = userAssetTypeService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAssetType}.
     */
    @Operation(
            method = "createUserAssetType",
            summary = "Create a new UserAssetType.",
            description = "This API is used to create a new UserAssetType in the system.",
            tags = {UserAssetTypeApi.API_TAG},
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
                        description = "Successfully created a new UserAssetType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/userAssetTypes")
    public ResponseEntity<UserAssetType> createUserAssetType(
            @Valid @RequestBody final CreateUserAssetTypeRequest payload) {
        // Delegate to the service layer.
        final UserAssetType newInstance = userAssetTypeService.createUserAssetType(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity} in the system.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserAssetType, which
     *     needs to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAssetType}.
     */
    @Operation(
            method = "updateUserAssetType",
            summary = "Update an existing UserAssetType.",
            description = "This API is used to update an existing UserAssetType in the system.",
            tags = {UserAssetTypeApi.API_TAG},
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
                                "Successfully updated an existing UserAssetType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/userAssetTypes/{userAssetTypeCode}")
    public ResponseEntity<UserAssetType> updateUserAssetType(
            @PathVariable(name = "userAssetTypeCode") final String userAssetTypeCode,
            @Valid @RequestBody final UpdateUserAssetTypeRequest payload) {
        // Delegate to the service layer.
        final UserAssetType updatedInstance =
                userAssetTypeService.updateUserAssetType(userAssetTypeCode, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity} in the system.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserAssetType, which
     *     needs to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAssetType}.
     */
    @Operation(
            method = "patchUserAssetType",
            summary = "Update an existing UserAssetType.",
            description = "This API is used to update an existing UserAssetType in the system.",
            tags = {UserAssetTypeApi.API_TAG},
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
                                "Successfully updated an existing UserAssetType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/userAssetTypes/{userAssetTypeCode}")
    public ResponseEntity<UserAssetType> patchUserAssetType(
            @PathVariable(name = "userAssetTypeCode") final String userAssetTypeCode,
            @Valid @RequestBody final PatchUserAssetTypeRequest payload) {
        // Delegate to the service layer.
        final UserAssetType updatedInstance =
                userAssetTypeService.patchUserAssetType(userAssetTypeCode, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity} in the system.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, whose details have
     *     to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserAssetType}.
     */
    @Operation(
            method = "findUserAssetType",
            summary = "Find an existing UserAssetType.",
            description = "This API is used to find an existing UserAssetType in the system.",
            tags = {UserAssetTypeApi.API_TAG},
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
                                "Successfully retrieved the details of an existing UserAssetType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/userAssetTypes/{userAssetTypeCode}")
    public ResponseEntity<UserAssetType> findUserAssetType(
            @PathVariable(name = "userAssetTypeCode") final String userAssetTypeCode) {
        // Delegate to the service layer.
        final UserAssetType matchingInstance =
                userAssetTypeService.findUserAssetType(userAssetTypeCode);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     UserAssetType based on the provided pagination settings.
     */
    @Operation(
            method = "findAllUserAssetTypes",
            summary = "Find all UserAssetTypes.",
            description = "This API is used to find all UserAssetTypes in the system.",
            tags = {UserAssetTypeApi.API_TAG},
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
                                "Successfully retrieved the UserAssetTypes in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/userAssetTypes")
    public ResponseEntity<Page<UserAssetType>> findAllUserAssetTypes(
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
        final Page<UserAssetType> matchingInstances =
                userAssetTypeService.findAllUserAssetTypes(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity} in the system.
     *
     * @param userAssetTypeCode Unique identifier of UserAssetType in the system, which needs to be
     *     deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.UserAssetTypeEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteUserAssetType",
            summary = "Delete an existing UserAssetType.",
            description = "This API is used to delete an existing UserAssetType in the system.",
            tags = {UserAssetTypeApi.API_TAG},
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
                                "Successfully deleted an existing UserAssetType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/userAssetTypes/{userAssetTypeCode}")
    public ResponseEntity<String> deleteUserAssetType(
            @PathVariable(name = "userAssetTypeCode") final String userAssetTypeCode) {
        // Delegate to the service layer.
        final String deletedInstance = userAssetTypeService.deleteUserAssetType(userAssetTypeCode);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
