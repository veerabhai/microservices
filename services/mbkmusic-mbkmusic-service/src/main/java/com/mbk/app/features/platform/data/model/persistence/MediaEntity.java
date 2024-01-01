/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.persistence;

import com.mbk.app.commons.data.jpa.persistence.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Implementation that maps the "media" table in the database to an entity in the ORM world.
 *
 * @author Editor
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "media")
@Entity
public class MediaEntity extends AbstractIdGeneratedEntity<Integer> {

    /** Reference to the song_id. */
    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id", unique = true)
    private SongEntity songId;

    /** Reference to the media_type_id. */
    @OneToOne
    @JoinColumn(name = "media_type_id", referencedColumnName = "id")
    private MediaTypeEntity mediaTypeId;

    /** Reference to the media_loc_type_id. */
    @OneToOne
    @JoinColumn(name = "media_loc_type_id", referencedColumnName = "id")
    private MediaLocationTypeEntity mediaLocTypeId;

    /** Reference to the url. */
    @Column(name = "url")
    private String url;


    /** Reference to the external_media_id. */
    @Column(name = "external_media_id")
    private String externalMediaId;

    /** Reference to the created_at. */
    @Column(name = "created_at")
    private Date createdAt;

    /** Reference to the updated_at. */
    @Column(name = "updated_at")
    private Date updatedAt;


}
