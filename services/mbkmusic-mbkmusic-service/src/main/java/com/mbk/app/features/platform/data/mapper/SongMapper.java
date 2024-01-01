/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.mapper.decorator.*;
import com.mbk.app.features.platform.data.model.experience.song.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link SongEntity to {@link Song and vice-versa.
 *
 * @author Editor
 */
@DecoratedWith(value = SongMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface SongMapper {

    /**
     * This method transforms an instance of type {@link CreateSongRequest} to an instance of type
     * {@link SongEntity}.
     *
     * @param source Instance of type {@link CreateSongRequest} that needs to be transformed to
     *     {@link SongEntity}.
     * @return Instance of type {@link SongEntity}.
     */
    @Mapping(source = "raagaId", target = "raagaId", ignore = true)
    @Mapping(source = "taalaId", target = "taalaId", ignore = true)
   // @Mapping(source = "noteId", target = "noteId", ignore = true)
    @Mapping(source = "songTypeId", target = "songTypeId", ignore = true)
    SongEntity transform(CreateSongRequest source);

    /**
     * This method transforms an instance of type {@link SongEntity} to an instance of type {@link
     * Song}.
     *
     * @param source Instance of type {@link SongEntity} that needs to be transformed to {@link
     *     Song}.
     * @return Instance of type {@link Song}.
     */
    @Mapping(source = "raagaId", target = "raagaId", ignore = true)
    @Mapping(source = "taalaId", target = "taalaId", ignore = true)
   // @Mapping(source = "noteId", target = "noteId", ignore = true)
    @Mapping(source = "songTypeId", target = "songTypeId", ignore = true)
    Song transform(SongEntity source);

    /**
     * This method converts / transforms the provided collection of {@link SongEntity} instances to
     * a collection of instances of type {@link Song}.
     *
     * @param source Instance of type {@link SongEntity} that needs to be transformed to {@link
     *     Song}.
     * @return Collection of instance of type {@link Song}.
     */
    default Collection<Song> transformListTo(Collection<SongEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link SongEntity} instances to a list
     * of instances of type {@link Song}.
     *
     * @param source Instance of type {@link SongEntity} that needs to be transformed to {@link
     *     Song}.
     * @return List of instance of type {@link Song}.
     */
    default List<Song> transformListTo(List<SongEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateSongRequest} to an instance of type
     * {@link SongEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateSongRequest} that needs to be transformed to
     *     {@link SongEntity}.
     * @param target Instance of type {@link SongEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    @Mapping(source = "raagaId", target = "raagaId", ignore = true)
    @Mapping(source = "taalaId", target = "taalaId", ignore = true)
    @Mapping(source = "songTypeId", target = "songTypeId", ignore = true)
    void transform(UpdateSongRequest source, @MappingTarget SongEntity target);

    /**
     * This method transforms an instance of type {@link UpdateSongRequest} to an instance of type
     * {@link SongEntity}.
     *
     * @param source Instance of type {@link UpdateSongRequest} that needs to be transformed to
     *     {@link SongEntity}.
     * @return Instance of type {@link SongEntity}.
     */
    @Mapping(source = "raagaId", target = "raagaId", ignore = true)
    @Mapping(source = "taalaId", target = "taalaId", ignore = true)
    @Mapping(source = "songTypeId", target = "songTypeId", ignore = true)
    SongEntity transform(UpdateSongRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateSongRequest}
     * instances to a collection of instances of type {@link SongEntity}.
     *
     * @param source Instance of type {@link UpdateSongRequest} that needs to be transformed to
     *     {@link SongEntity}.
     * @return Instance of type {@link SongEntity}.
     */
    default Collection<SongEntity> transformList(Collection<UpdateSongRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
