/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.security.db.model.experience;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mbk.app.commons.Name;
import com.mbk.app.security.db.model.persistence.UserPrincipalEntity;
import com.mbk.app.security.userdetails.UserPrincipal;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * A data transfer object that returns the details of the user.
 *
 * @author {runtimeModel.authorName}
 */
@ToString(of = {"userName"})
@EqualsAndHashCode(of = {"id"})
@Builder
@Data
public class UserProfile extends AbstractUserProfile<Integer> {
    /** GrantedAuthority format as required by Spring Security. */
    private static final String GRANTED_AUTHORITY_FORMAT = "ROLE_{0}";

    /** Unique identifier of the user. */
    private Integer id;

    /** User name of the user. */
    private String username;

    /** Reference to the roles. */
    private Collection<String> roles;

    /**
     * This method transforms the provided user entity object of type {@link UserPrincipalEntity} to
     * an instance of type {@link UserProfile}.
     *
     * @param user User entity of type {@link UserPrincipalEntity} that needs to be transformed to
     *     {@link UserProfile}.
     * @return Instance of type {@link UserProfile}.
     */
    @JsonIgnore
    public static UserProfile from(final UserPrincipalEntity user) throws IOException {
        UserProfile userProfile =
                UserProfile.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .roles(user.getAssignedRoleNames())
                        .build();

        // 1. If the source is having the profile pic then attach its content also.
        if (Objects.nonNull(user.getAsset().getName())
                && StringUtils.isNotBlank(user.getAsset().getName())) {
            // 3. Get the file path and return file.
            final String pictureStorageDirectory = user.getAsset().getPath();
            final File userPictureFile =
                    Paths.get(pictureStorageDirectory)
                            .resolve(user.getAsset().getPath())
                            .normalize()
                            .toAbsolutePath()
                            .toFile();
            if (userPictureFile.exists()) {
                userProfile
                        .get()
                        .put(
                                Name.PROFILE_PIC.key(),
                                (Base64.encodeBase64String(
                                        FileUtils.readFileToByteArray(userPictureFile))));
            }
            return userProfile;
        }
        return userProfile;
    }

    /**
     * This method transforms the provided user principal object of type {@link UserPrincipal} to an
     * instance of type {@link UserProfile}.
     *
     * @param user User principal of type {@link UserPrincipal} that needs to be transformed to
     *     {@link UserProfile}.
     * @return Instance of type {@link UserProfile}.
     */
    @JsonIgnore
    public static UserProfile from(final UserPrincipal user, final String token) {
        UserProfile userProfile =
                UserProfile.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .roles(user.getAssignedRoles())
                        .build();

        userProfile.get().put(Name.PROFILE_PIC.key(), user.get(Name.PROFILE_PIC.key()));

        // 1. Validating the token.
        if (token.startsWith(Name.BEARER.key())) {
            userProfile.get().put(Name.ACCESS_TOKEN.key(), token.substring(7));
        } else {
            userProfile.get().put(Name.ACCESS_TOKEN.key(), token);
        }

        return userProfile;
    }
}
