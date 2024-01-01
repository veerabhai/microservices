/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.model.experience.user;

import java.util.Collection;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Admin
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class CreateUserRequest {
    /** User name of the user. */
    @NotBlank(message = "user.username.not.blank.message")
    private String username;

    /** Password of the user. */
    private String password;

    private  String reason;

    /** Reference to the roles. */
    private Collection<String> roles;

    /** Reference to the password_expiry_timestamp. */
    private Long passwordExpiryTimestamp;
}
