/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.songtype.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link SongTypeEntity to {@link SongType and vice-versa.
 *
 * @author Editor
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface SongTypeMapper {

    /**
     * This method transforms an instance of type {@link CreateSongTypeRequest} to an instance of
     * type {@link SongTypeEntity}.
     *
     * @param source Instance of type {@link CreateSongTypeRequest} that needs to be transformed to
     *     {@link SongTypeEntity}.
     * @return Instance of type {@link SongTypeEntity}.
     */
    SongTypeEntity transform(CreateSongTypeRequest source);

    /**
     * This method transforms an instance of type {@link SongTypeEntity} to an instance of type
     * {@link SongType}.
     *
     * @param source Instance of type {@link SongTypeEntity} that needs to be transformed to {@link
     *     SongType}.
     * @return Instance of type {@link SongType}.
     */
    SongType transform(SongTypeEntity source);

    /**
     * This method converts / transforms the provided collection of {@link SongTypeEntity} instances
     * to a collection of instances of type {@link SongType}.
     *
     * @param source Instance of type {@link SongTypeEntity} that needs to be transformed to {@link
     *     SongType}.
     * @return Collection of instance of type {@link SongType}.
     */
    default Collection<SongType> transformListTo(Collection<SongTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link SongTypeEntity} instances to a
     * list of instances of type {@link SongType}.
     *
     * @param source Instance of type {@link SongTypeEntity} that needs to be transformed to {@link
     *     SongType}.
     * @return List of instance of type {@link SongType}.
     */
    default List<SongType> transformListTo(List<SongTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateSongTypeRequest} to an instance of
     * type {@link SongTypeEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateSongTypeRequest} that needs to be transformed to
     *     {@link SongTypeEntity}.
     * @param target Instance of type {@link SongTypeEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    void transform(UpdateSongTypeRequest source, @MappingTarget SongTypeEntity target);

    /**
     * This method transforms an instance of type {@link UpdateSongTypeRequest} to an instance of
     * type {@link SongTypeEntity}.
     *
     * @param source Instance of type {@link UpdateSongTypeRequest} that needs to be transformed to
     *     {@link SongTypeEntity}.
     * @return Instance of type {@link SongTypeEntity}.
     */
    SongTypeEntity transform(UpdateSongTypeRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateSongTypeRequest}
     * instances to a collection of instances of type {@link SongTypeEntity}.
     *
     * @param source Instance of type {@link UpdateSongTypeRequest} that needs to be transformed to
     *     {@link SongTypeEntity}.
     * @return Instance of type {@link SongTypeEntity}.
     */
    default Collection<SongTypeEntity> transformList(Collection<UpdateSongTypeRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
