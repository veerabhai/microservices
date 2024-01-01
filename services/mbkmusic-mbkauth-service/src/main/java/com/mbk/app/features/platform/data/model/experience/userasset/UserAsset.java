/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */

package com.mbk.app.features.platform.data.model.experience.userasset;

import com.mbk.app.features.platform.data.model.experience.userassettype.UserAssetType;
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
public class UserAsset {
    /** Reference to the id. */
    private String id;

    /** Reference to the name. */
    private String name;

    /** Reference to the path. */
    private String path;

    /** Reference to the type_code. */
    private UserAssetType typeCode;
}
