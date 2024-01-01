/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper.decorator;

import com.mbk.app.features.platform.data.mapper.*;
import com.mbk.app.features.platform.data.model.experience.song.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import com.mbk.app.features.platform.data.repository.*;
import lombok.extern.slf4j.*;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link SongEntity to {@link Song and vice-versa.
 *
 * @author Editor
 */
@Slf4j
public abstract class SongMapperDecorator implements SongMapper {

    /** Repository implementation of type {@link NoteRepository}. */


    /** Repository implementation of type {@link RaagaRepository}. */
    @Autowired private RaagaRepository raagaRepository;

    /** Repository implementation of type {@link TaalaRepository}. */
    @Autowired private TaalaRepository taalaRepository;

    /** Repository implementation of type {@link SongTypeRepository}. */
    @Autowired private SongTypeRepository songTypeRepository;

    /** Mapper implementation of type {@link SongMapper}. */
    @Autowired private SongMapper songMapper;

    /** Mapper implementation of type {@link TaalaMapper}. */
    @Autowired private TaalaMapper taalaMapper;


    /** Mapper implementation of type {@link SongTypeMapper}. */
    @Autowired private SongTypeMapper songTypeMapper;

    /** Mapper implementation of type {@link RaagaMapper}. */
    @Autowired private RaagaMapper raagaMapper;

    @Override
    public SongEntity transform(final CreateSongRequest source) {
        // 1. Transform the CreateSongRequest to SongEntity object.
        final SongEntity song = songMapper.transform(source);
        if (!source.getRaagaId().isEmpty()) {
            song.setRaagaId(raagaRepository.findByIdOrThrow(source.getRaagaId()));
        }

        if (!source.getTaalaId().isEmpty()){
            song.setTaalaId(taalaRepository.findByIdOrThrow(source.getTaalaId()));
        }

        if(!source.getSongTypeId().isEmpty()){
            song.setSongTypeId(songTypeRepository.findByIdOrThrow(source.getSongTypeId()));
        }
       // song.setTaalaId(taalaRepository.findByIdOrThrow(source.getTaalaId()));
        //song.setSongTypeId(songTypeRepository.findByIdOrThrow(source.getSongTypeId()));
       // song.setRaagaId(raagaRepository.findByIdOrThrow(source.getRaagaId()));
      //  song.setCreatedBy(AuthenticationUtils.getPrincipalId());
        // Return the transformed object.
        return song;
    }

    @Override
    public Song transform(final SongEntity source) {
        // 1. Transform the SongEntity to Song object.
        final Song song = songMapper.transform(source);

        song.setTaalaId(taalaMapper.transform(source.getTaalaId()));
        song.setSongTypeId(songTypeMapper.transform(source.getSongTypeId()));
        song.setRaagaId(raagaMapper.transform(source.getRaagaId()));

        // Return the transformed object.
        return song;
    }

    @Override
    public void transform(final UpdateSongRequest source, final @MappingTarget SongEntity target) {

        // Transform from source to the target.
        songMapper.transform(source, target);


        if(!source.getRaagaId().isEmpty())
        if (Objects.nonNull(source.getTaalaId())) {
            target.setTaalaId(taalaRepository.findByIdOrThrow(source.getTaalaId()));
        }
        if(!source.getSongTypeId().isEmpty())
        if (Objects.nonNull(source.getSongTypeId())) {
            target.setSongTypeId(songTypeRepository.findByIdOrThrow(source.getSongTypeId()));
        }
        if(!source.getRaagaId().isEmpty())
        if (Objects.nonNull(source.getRaagaId())) {
            target.setRaagaId(raagaRepository.findByIdOrThrow(source.getRaagaId()));
        }
    }

    @Override
    public SongEntity transform(final UpdateSongRequest source) {

        // Transform from source to the target.
        final SongEntity song = songMapper.transform(source);

        if(!source.getTaalaId().isEmpty())
        if (Objects.nonNull(source.getTaalaId())) {
            song.setTaalaId(taalaRepository.findByIdOrThrow(source.getTaalaId()));
        }
        if(!source.getSongTypeId().isEmpty())
        if (Objects.nonNull(source.getSongTypeId())) {
            song.setSongTypeId(songTypeRepository.findByIdOrThrow(source.getSongTypeId()));
        }
        if(!source.getRaagaId().isEmpty())
        if (Objects.nonNull(source.getRaagaId())) {
            song.setRaagaId(raagaRepository.findByIdOrThrow(source.getRaagaId()));
        }
        // Return the response.
        return song;
    }
}
