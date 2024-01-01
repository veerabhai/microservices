/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.utils.CaseFormatUtils;
import com.mbk.app.commons.web.api.AuthAbstractApi;
import com.mbk.app.commons.web.configuration.properties.ApiDocumentationSettings;
import com.mbk.app.features.platform.data.model.experience.userstatus.CreateUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.PatchUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.UpdateUserStatusRequest;
import com.mbk.app.features.platform.data.model.experience.userstatus.UserStatus;
import com.mbk.app.features.platform.web.service.UserStatusService;
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
 * com.mbk.app.features.platform.data.model.persistence.UserStatusEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(UserStatusApi.rootEndPoint)
public class UserStatusApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "UserStatuses";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkauth";

    /** Service implementation of type {@link UserStatusService}. */
    private final UserStatusService userStatusService;

    /**
     * Constructor.
     *
     * @param userStatusService Service instance of type {@link UserStatusService}.
     */
    public UserStatusApi(final UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserStatusEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.UserStatusEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserStatus}.
     */
    @Operation(
            method = "createUserStatus",
            summary = "Create a new UserStatus.",
            description = "This API is used to create a new UserStatus in the system.",
            tags = {UserStatusApi.API_TAG},
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
                        description = "Successfully created a new UserStatus in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/userStatuses")
    public ResponseEntity<UserStatus> createUserStatus(
            @Valid @RequestBody final CreateUserStatusRequest payload) {
        // Delegate to the service layer.
        final UserStatus newInstance = userStatusService.createUserStatus(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserStatusEntity} in the system.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserStatus, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserStatus}.
     */
    @Operation(
            method = "updateUserStatus",
            summary = "Update an existing UserStatus.",
            description = "This API is used to update an existing UserStatus in the system.",
            tags = {UserStatusApi.API_TAG},
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
                        description = "Successfully updated an existing UserStatus in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/userStatuses/{userStatusCode}")
    public ResponseEntity<UserStatus> updateUserStatus(
            @PathVariable(name = "userStatusCode") final String userStatusCode,
            @Valid @RequestBody final UpdateUserStatusRequest payload) {
        // Delegate to the service layer.
        final UserStatus updatedInstance =
                userStatusService.updateUserStatus(userStatusCode, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserStatusEntity} in the system.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing UserStatus, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserStatus}.
     */
    @Operation(
            method = "patchUserStatus",
            summary = "Update an existing UserStatus.",
            description = "This API is used to update an existing UserStatus in the system.",
            tags = {UserStatusApi.API_TAG},
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
                        description = "Successfully updated an existing UserStatus in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/userStatuses/{userStatusCode}")
    public ResponseEntity<UserStatus> patchUserStatus(
            @PathVariable(name = "userStatusCode") final String userStatusCode,
            @Valid @RequestBody final PatchUserStatusRequest payload) {
        // Delegate to the service layer.
        final UserStatus updatedInstance =
                userStatusService.patchUserStatus(userStatusCode, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.UserStatusEntity} in the system.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     UserStatus}.
     */
    @Operation(
            method = "findUserStatus",
            summary = "Find an existing UserStatus.",
            description = "This API is used to find an existing UserStatus in the system.",
            tags = {UserStatusApi.API_TAG},
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
                                "Successfully retrieved the details of an existing UserStatus in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/userStatuses/{userStatusCode}")
    public ResponseEntity<UserStatus> findUserStatus(
            @PathVariable(name = "userStatusCode") final String userStatusCode) {
        // Delegate to the service layer.
        final UserStatus matchingInstance = userStatusService.findUserStatus(userStatusCode);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserStatusEntity} in the system in a
     * paginated manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     UserStatus based on the provided pagination settings.
     */
    @Operation(
            method = "findAllUserStatuses",
            summary = "Find all UserStatuses.",
            description = "This API is used to find all UserStatuses in the system.",
            tags = {UserStatusApi.API_TAG},
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
                                "Successfully retrieved the UserStatuses in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/userStatuses")
    public ResponseEntity<Page<UserStatus>> findAllUserStatuses(
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
        final Page<UserStatus> matchingInstances =
                userStatusService.findAllUserStatuses(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserStatusEntity} in the system.
     *
     * @param userStatusCode Unique identifier of UserStatus in the system, which needs to be
     *     deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.UserStatusEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteUserStatus",
            summary = "Delete an existing UserStatus.",
            description = "This API is used to delete an existing UserStatus in the system.",
            tags = {UserStatusApi.API_TAG},
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
                        description = "Successfully deleted an existing UserStatus in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/userStatuses/{userStatusCode}")
    public ResponseEntity<String> deleteUserStatus(
            @PathVariable(name = "userStatusCode") final String userStatusCode) {
        // Delegate to the service layer.
        final String deletedInstance = userStatusService.deleteUserStatus(userStatusCode);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
