/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.experience.userstatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Editor
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class CreateUserStatusRequest {
    /** Reference to the value. */
    @NotBlank(message = "user.status.value.not.blank.message")
    @Size(max = 64, message = "user.status.value.size.message")
    private String value;
}
