/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.db.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbk.app.security.db.model.experience.UserProfile;
import com.mbk.app.security.userdetails.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

/**
 * A REST controller that exposes APIs to retrieve the user profile details.
 *
 * @author Editor
 */
@Api(value = "users", tags = {"Users"})
@RequestMapping(value = "/users")
@RestController
public class UserProfileApi {

    /**
     * This method returns the user details of the principal i.e. current logged in user.
     *
     * @param userPrincipal
     *         User Principal who represents the current logged in user.
     *
     * @return Response Entity containing the user principal details.
     */
    @ApiOperation(value = "Get user details for the current logged in user.",
                  nickname = "getMyProfile",
                  tags = {"Users"},
                  authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the details of the user."),
            @ApiResponse(code = 403, message = "You do not have permissions to perform this operation.")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserProfile> getMyProfile(
            @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal,
            @Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) final String bearerAccessToken) {
        return ResponseEntity.ok(UserProfile.from(userPrincipal,bearerAccessToken));
    }
}