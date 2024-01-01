/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.persistence;

import com.mbk.app.commons.data.jpa.persistence.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Implementation that maps the "thumbnail" table in the database to an entity in the ORM world.
 *
 * @author Editor
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "thumbnail")
@Entity
public class ThumbnailEntity extends AbstractIdGeneratedEntity<Integer> {

    /** Reference to the media_id. */
    @ManyToOne
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private MediaEntity mediaId;

    /** Reference to the resolution_type. */
    @Column(name = "resolution_type")
    private String resolutionType;

    /** Reference to the height. */
    @Column(name = "height")
    private Integer height;

    /** Reference to the width. */
    @Column(name = "width")
    private Integer width;

    /** Reference to the url. */
    @Column(name = "url")
    private String url;

    /** Reference to the created_at. */
    @Column(name = "created_at")
    private Date createdAt;

    /** Reference to the updated_at. */
    @Column(name = "updated_at")
    private Date updatedAt;
}
