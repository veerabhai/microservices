/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.utils.CaseFormatUtils;
import com.mbk.app.commons.web.api.AuthAbstractApi;
import com.mbk.app.commons.web.configuration.properties.ApiDocumentationSettings;
import com.mbk.app.features.platform.data.model.experience.admin.AdminApproval;
import com.mbk.app.features.platform.data.model.experience.admin.AdminRequest;
import com.mbk.app.features.platform.data.model.experience.user.CreateUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.PatchUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.UpdateUserRequest;
import com.mbk.app.features.platform.data.model.experience.user.User;
import com.mbk.app.features.platform.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.javamail.JavaMailSender;

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

import java.util.List;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.mbk.app.features.platform.data.model.persistence.UserEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(UserApi.rootEndPoint)
public class UserApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Users";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkauth";

    /** Service implementation of type {@link UserService}. */
    private final UserService userService;
    /**
     * Constructor.
     *
     * @param userService Service instance of type {@link UserService}.
     */
    public UserApi(final UserService userService) {
        this.userService = userService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.UserEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link User}.
     */
    @Operation(
            method = "createUser",
            summary = "Create a new User.",
            description = "This API is used to create a new User in the system.",
            tags = {UserApi.API_TAG},
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
                        description = "Successfully created a new User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    //@PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody final CreateUserRequest payload) {
        //@RequestParam (value = "reason",required = false) final String reason
        // Delegate to the service layer.
        final User newInstance = userService.createUser(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserEntity} in the system.
     *
     * @param userId Unique identifier of User in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing User, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link User}.
     */
    @Operation(
            method = "updateUser",
            summary = "Update an existing User.",
            description = "This API is used to update an existing User in the system.",
            tags = {UserApi.API_TAG},
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
                        description = "Successfully updated an existing User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable(name = "userId") final Integer userId,
            @Valid @RequestBody final UpdateUserRequest payload) {
        // Delegate to the service layer.
        final User updatedInstance = userService.updateUser(userId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserEntity} in the system.
     *
     * @param userId Unique identifier of User in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing User, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link User}.
     */
    @Operation(
            method = "patchUser",
            summary = "Update an existing User.",
            description = "This API is used to update an existing User in the system.",
            tags = {UserApi.API_TAG},
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
                        description = "Successfully updated an existing User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/users/{userId}")
    public ResponseEntity<User> patchUser(
            @PathVariable(name = "userId") final Integer userId,
            @Valid @RequestBody final PatchUserRequest payload) {
        // Delegate to the service layer.
        final User updatedInstance = userService.patchUser(userId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.UserEntity} in the system.
     *
     * @param userId Unique identifier of User in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link User}.
     */
    @Operation(
            method = "findUser",
            summary = "Find an existing User.",
            description = "This API is used to find an existing User in the system.",
            tags = {UserApi.API_TAG},
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
                                "Successfully retrieved the details of an existing User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<User> findUser(@PathVariable(name = "userId") final Integer userId) {
        // Delegate to the service layer.
        final User matchingInstance = userService.findUser(userId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserEntity} in the system in a paginated
     * manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type User
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllUsers",
            summary = "Find all Users.",
            description = "This API is used to find all Users in the system.",
            tags = {UserApi.API_TAG},
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
                                "Successfully retrieved the Users in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/users")
    public ResponseEntity<Page<User>> findAllUsers(
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
        final Page<User> matchingInstances = userService.findAllUsers(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.UserEntity} in the system.
     *
     * @param userId Unique identifier of User in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.UserEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteUser",
            summary = "Delete an existing User.",
            description = "This API is used to delete an existing User in the system.",
            tags = {UserApi.API_TAG},
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
                        description = "Successfully deleted an existing User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<Integer> deleteUser(@PathVariable(name = "userId") final Integer userId) {
        // Delegate to the service layer.
        final Integer deletedInstance = userService.deleteUser(userId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/users/userApproval")
    public List<AdminRequest> adminRequestList(){
        List<AdminRequest> adminRequestList = userService.adminRequestList();
        return adminRequestList;
    }

    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/users/userApprovalStatus")
    public String adminApproval(@RequestBody List<AdminApproval> adminApproval){

        String status =  userService.adminApprovalStatus(adminApproval);
        return status;
    }


    @PostMapping(value = "/users/passwordtoken")
    public String getPasswordToken (@RequestParam String userName){

        String status =  userService.getPasswordToken(userName);
        return "sent successfully";
    }

    @PostMapping(value = "/user/passwordvalidate")
    public String validatePasswordToken (@RequestParam String userName,@RequestParam Long passwordToken,@RequestParam String newPassword,@RequestParam String confirmPassword){

        String status =  userService.validatePasswordToken(userName,passwordToken,newPassword,confirmPassword);


        return "updated successfully";
    }

}


