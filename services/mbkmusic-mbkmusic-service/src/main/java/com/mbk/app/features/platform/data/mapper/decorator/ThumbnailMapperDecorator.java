/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper.decorator;

import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.thumbnail.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import com.mbk.app.features.platform.data.repository.*;
import lombok.extern.slf4j.*;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link ThumbnailEntity to {@link Thumbnail and vice-versa.
 *
 * @author Editor
 */
@Slf4j
public abstract class ThumbnailMapperDecorator implements ThumbnailMapper {

    /** Repository implementation of type {@link MediaRepository}. */
    @Autowired private MediaRepository mediaRepository;

    /** Mapper implementation of type {@link ThumbnailMapper}. */
    @Autowired private ThumbnailMapper thumbnailMapper;

    /** Mapper implementation of type {@link MediaMapper}. */
    @Autowired private MediaMapper mediaMapper;

    @Override
    public ThumbnailEntity transform(final CreateThumbnailRequest source) {
        // 1. Transform the CreateThumbnailRequest to ThumbnailEntity object.
        final ThumbnailEntity thumbnail = thumbnailMapper.transform(source);

        thumbnail.setMediaId(mediaRepository.findByIdOrThrow(source.getMediaId()));

        // Return the transformed object.
        return thumbnail;
    }

    @Override
    public Thumbnail transform(final ThumbnailEntity source) {
        // 1. Transform the ThumbnailEntity to Thumbnail object.
        final Thumbnail thumbnail = thumbnailMapper.transform(source);

        thumbnail.setMediaId(mediaMapper.transform(source.getMediaId()));

        // Return the transformed object.
        return thumbnail;
    }

    @Override
    public void transform(
            final UpdateThumbnailRequest source, final @MappingTarget ThumbnailEntity target) {

        // Transform from source to the target.
        thumbnailMapper.transform(source, target);

        if (Objects.nonNull(source.getMediaId())) {
            target.setMediaId(mediaRepository.findByIdOrThrow(source.getMediaId()));
        }
    }

    @Override
    public ThumbnailEntity transform(final UpdateThumbnailRequest source) {

        // Transform from source to the target.
        final ThumbnailEntity thumbnail = thumbnailMapper.transform(source);

        if (Objects.nonNull(source.getMediaId())) {
            thumbnail.setMediaId(mediaRepository.findByIdOrThrow(source.getMediaId()));
        }
        // Return the response.
        return thumbnail;
    }
}
