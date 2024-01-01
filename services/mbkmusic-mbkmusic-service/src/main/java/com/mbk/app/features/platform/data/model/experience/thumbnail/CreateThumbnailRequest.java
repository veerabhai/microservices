/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.experience.thumbnail;

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
public class CreateThumbnailRequest {
    /** Reference to the media_id. */
    private Integer mediaId;

    /** Reference to the resolution_type. */
    private String resolutionType;

    /** Reference to the height. */
    private Integer height;

    /** Reference to the width. */
    private Integer width;

    /** Reference to the url. */
    private String url;

    /** Reference to the created_at. */
    private Date createdAt;

    /** Reference to the updated_at. */
    private Date updatedAt;
}
