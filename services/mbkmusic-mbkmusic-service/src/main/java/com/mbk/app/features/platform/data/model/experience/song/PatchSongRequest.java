/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.model.experience.song;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mbk.app.features.platform.data.model.experience.raaga.*;
import com.mbk.app.features.platform.data.model.experience.songtype.*;
import com.mbk.app.features.platform.data.model.experience.taala.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class PatchSongRequest {
    /** Reference to the title. */
    private String title;

    /** Reference to the description. */
    private String description;

    /** Reference to the singer. */
    private String singer;

    /** Reference to the song_type_id. */
    private SongType songTypeId;

    /** Reference to the composer. */
    private String composer;

    /** Reference to the recording_date. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date recordingDate;

    /** Reference to the raaga_id. */
    private Raaga raagaId;

    /** Reference to the taala_id. */
    private Taala taalaId;

    /** Reference to the mridangam_accompaniments. */
    private String mridangamAccompaniments;

    /** Reference to the violion_accompaniments. */
    private String violionAccompaniments;

    /** Reference to the morsing_accompaniments. */
    private String morsingAccompaniments;

    /** Reference to the ghatam_accompaniments. */
    private String ghatamAccompaniments;

    /** Reference to the kanjeera_accompaniments. */
    private String kanjeeraAccompaniments;

    /** Reference to the flute_accompaniments. */
    private String fluteAccompaniments;

    /** Reference to the lyrics. */
    private String lyrics;

    /** Reference to the created_by. */
    private String createdBy;

    /** Reference to the updated_by. */
    private String updatedBy;

    /** Reference to the created_at. */
    private Date createdAt;

    /** Reference to the updated_at. */
    private Date updatedAt;


}
