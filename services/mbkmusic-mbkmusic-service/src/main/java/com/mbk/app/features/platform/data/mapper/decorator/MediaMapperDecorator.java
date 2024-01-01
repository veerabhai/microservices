/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper.decorator;

import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.media.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import com.mbk.app.features.platform.data.repository.*;
import lombok.extern.slf4j.*;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link MediaEntity to {@link Media and vice-versa.
 *
 * @author Editor
 */
@Slf4j
public abstract class MediaMapperDecorator implements MediaMapper {

    /** Repository implementation of type {@link MediaTypeRepository}. */
    @Autowired private MediaTypeRepository mediaTypeRepository;

    /** Repository implementation of type {@link SongRepository}. */
    @Autowired private SongRepository songRepository;

    /** Repository implementation of type {@link MediaLocationTypeRepository}. */
    @Autowired private MediaLocationTypeRepository mediaLocationTypeRepository;

    /** Mapper implementation of type {@link SongMapper}. */
    @Autowired private SongMapper songMapper;

    /** Mapper implementation of type {@link MediaTypeMapper}. */
    @Autowired private MediaTypeMapper mediaTypeMapper;

    /** Mapper implementation of type {@link MediaLocationTypeMapper}. */
    @Autowired private MediaLocationTypeMapper mediaLocationTypeMapper;

    /** Mapper implementation of type {@link MediaMapper}. */
    @Autowired private MediaMapper mediaMapper;

    @Override
    public MediaEntity transform(final CreateMediaRequest source) {
        // 1. Transform the CreateMediaRequest to MediaEntity object.
        final MediaEntity media = mediaMapper.transform(source);

        media.setMediaLocTypeId(
                mediaLocationTypeRepository.findByIdOrThrow(source.getMediaLocTypeId()));
        media.setSongId(songRepository.findByIdOrThrow(source.getSongId()));
        media.setMediaTypeId(mediaTypeRepository.findByIdOrThrow(source.getMediaTypeId()));

        // Return the transformed object.
        return media;
    }

    @Override
    public Media transform(final MediaEntity source) {
        // 1. Transform the MediaEntity to Media object.
        final Media media = mediaMapper.transform(source);

        media.setMediaLocTypeId(mediaLocationTypeMapper.transform(source.getMediaLocTypeId()));
        media.setSongId(songMapper.transform(source.getSongId()));
        media.setMediaTypeId(mediaTypeMapper.transform(source.getMediaTypeId()));

        // Return the transformed object.
        return media;
    }

    @Override
    public void transform(
            final UpdateMediaRequest source, final @MappingTarget MediaEntity target) {

        // Transform from source to the target.
        mediaMapper.transform(source, target);

        if (Objects.nonNull(source.getMediaLocTypeId())) {
            target.setMediaLocTypeId(
                    mediaLocationTypeRepository.findByIdOrThrow(source.getMediaLocTypeId()));
        }
        if (Objects.nonNull(source.getSongId())) {
            target.setSongId(songRepository.findByIdOrThrow(source.getSongId()));
        }
        if (Objects.nonNull(source.getMediaTypeId())) {
            target.setMediaTypeId(mediaTypeRepository.findByIdOrThrow(source.getMediaTypeId()));
        }
    }

    @Override
    public MediaEntity transform(final UpdateMediaRequest source) {

        // Transform from source to the target.
        final MediaEntity media = mediaMapper.transform(source);

        if (Objects.nonNull(source.getMediaLocTypeId())) {
            media.setMediaLocTypeId(
                    mediaLocationTypeRepository.findByIdOrThrow(source.getMediaLocTypeId()));
        }
        if (Objects.nonNull(source.getSongId())) {
            media.setSongId(songRepository.findByIdOrThrow(source.getSongId()));
        }
        if (Objects.nonNull(source.getMediaTypeId())) {
            media.setMediaTypeId(mediaTypeRepository.findByIdOrThrow(source.getMediaTypeId()));
        }
        // Return the response.
        return media;
    }
}
