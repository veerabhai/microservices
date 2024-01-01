/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.persistence;

import com.mbk.app.commons.data.jpa.persistence.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Implementation that maps the "song" table in the database to an entity in the ORM world.
 *
 * @author Editor
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "song")
@Entity
public class SongEntity extends AbstractIdGeneratedEntity<Integer> {

    /** Reference to the title. */
    @Column(name = "title")
    private String title;

    /** Reference to the description. */
    @Column(name = "description", length = 77056)
    private String description;

    /** Reference to the singer. */
    @Column(name = "singer")
    private String singer;

    /** Reference to the song_type_id. */
    @OneToOne
    @JoinColumn(name = "song_type_id", referencedColumnName = "id")
    private SongTypeEntity songTypeId;

    /** Reference to the composer. */
    @Column(name = "composer")
    private String composer;

    /** Reference to the recording_date. */
    @Column(name = "recording_date")
    @Temporal(TemporalType.DATE)
    private Date recordingDate;

    /** Reference to the raaga_id. */
    @OneToOne
    @JoinColumn(name = "raaga_id", referencedColumnName = "id")
    private RaagaEntity raagaId;

    /** Reference to the taala_id. */
    @OneToOne
    @JoinColumn(name = "taala_id", referencedColumnName = "id")
    private TaalaEntity taalaId;

    /** Reference to the mridangam_accompaniments. */
    @Column(name = "mridangam_accompaniments")
    private String mridangamAccompaniments;

    /** Reference to the violion_accompaniments. */
    @Column(name = "violion_accompaniments")
    private String violinAccompaniments;

    /** Reference to the morsing_accompaniments. */
    @Column(name = "morsing_accompaniments")
    private String morsingAccompaniments;

    /** Reference to the ghatam_accompaniments. */
    @Column(name = "ghatam_accompaniments")
    private String ghatamAccompaniments;

    /** Reference to the kanjeera_accompaniments. */
    @Column(name = "kanjeera_accompaniments")
    private String kanjeeraAccompaniments;

    /** Reference to the flute_accompaniments. */
    @Column(name = "flute_accompaniments")
    private String fluteAccompaniments;

    /** Reference to the lyrics. */
    @Column(name = "lyrics", length = 77056)
    private String lyrics;

    /** Reference to the created_by. */
    @Column(name = "created_by")
    private String createdBy;

    /** Reference to the updated_by. */
    @Column(name = "updated_by")
    private String updatedBy;

    /** Reference to the created_at. */
    @Column(name = "created_at")
    private Date createdAt;

    /** Reference to the updated_at. */
    @Column(name = "updated_at")
    private Date updatedAt;



}
