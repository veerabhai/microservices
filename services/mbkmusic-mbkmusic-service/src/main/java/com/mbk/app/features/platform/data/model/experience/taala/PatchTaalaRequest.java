/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.experience.taala;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Editor
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class PatchTaalaRequest {
    /** Reference to the description. */
    private String description;

    /** Reference to the created_at. */
    private Date createdAt;

    /** Reference to the updated_at. */
    private Date updatedAt;
}
