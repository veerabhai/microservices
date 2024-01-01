/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.experience.media;

import lombok.*;
import lombok.experimental.*;

import javax.validation.constraints.*;
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
public class UpdateMediaRequest {
    /** Reference to the id. */
    @NotNull(message = "media.id.not.null.message")
    private Integer id;

    /** Reference to the song_id. */
    private Integer songId;

    /** Reference to the media_type_id. */
    private Integer mediaTypeId;

    /** Reference to the media_loc_type_id. */
    private Integer mediaLocTypeId;

    /** Reference to the url. */
    private String url;

    /** Reference to the external_media_id. */
    private String externalMediaId;

    /** Reference to the created_at. */
    private Date createdAt;

    /** Reference to the updated_at. */
    private Date updatedAt;
}
