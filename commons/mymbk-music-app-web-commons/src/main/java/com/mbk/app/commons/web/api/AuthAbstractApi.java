/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.api;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import com.mbk.app.commons.exception.ServiceException;
import com.mbk.app.commons.web.api.error.ApiErrors;
import com.mbk.app.commons.web.utils.HttpUtils;
import com.mbk.app.security.utils.AuthenticationUtils;

/**
 * Auth Abstract Api class that exposes the common functionality and reusable methods of all the APIs to get the User Principal.
 *
 * @author Editor
 */
@Slf4j
public abstract class AuthAbstractApi extends AbstractApi {
    /**
     * This method returns the principal contained within the authentication object and adapts to the specified type if
     * possible. If the adaptation fails, this method returns null.
     *
     * @param authentication
     *         Authentication object of type {@link Authentication} and holds the details of the authenticated user. If
     *         there is no authenticated user, this will point to anonymous user.
     * @param userPrincipalType
     *         Target type of the user principal that needs to be adapted to
     * @param <T>
     *         Target type
     *
     * @return User principal adapted to the target type. If the adaptation fails, this method returns null
     */
    protected <T> T getPrincipal(final Authentication authentication, final Class<T> userPrincipalType) {
        return AuthenticationUtils.getPrincipal(authentication, userPrincipalType);
    }
}