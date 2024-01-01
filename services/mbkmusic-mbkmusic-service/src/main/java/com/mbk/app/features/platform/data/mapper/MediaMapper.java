/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.mapper.decorator.*;
import com.mbk.app.features.platform.data.model.experience.media.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link MediaEntity to {@link Media and vice-versa.
 *
 * @author Editor
 */
@DecoratedWith(value = MediaMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MediaMapper {

    /**
     * This method transforms an instance of type {@link CreateMediaRequest} to an instance of type
     * {@link MediaEntity}.
     *
     * @param source Instance of type {@link CreateMediaRequest} that needs to be transformed to
     *     {@link MediaEntity}.
     * @return Instance of type {@link MediaEntity}.
     */
    @Mapping(source = "mediaLocTypeId", target = "mediaLocTypeId", ignore = true)
    @Mapping(source = "mediaTypeId", target = "mediaTypeId", ignore = true)
    @Mapping(source = "songId", target = "songId", ignore = true)
    MediaEntity transform(CreateMediaRequest source);

    /**
     * This method transforms an instance of type {@link MediaEntity} to an instance of type {@link
     * Media}.
     *
     * @param source Instance of type {@link MediaEntity} that needs to be transformed to {@link
     *     Media}.
     * @return Instance of type {@link Media}.
     */
    @Mapping(source = "mediaLocTypeId", target = "mediaLocTypeId", ignore = true)
    @Mapping(source = "mediaTypeId", target = "mediaTypeId", ignore = true)
    @Mapping(source = "songId", target = "songId", ignore = true)
    Media transform(MediaEntity source);

    /**
     * This method converts / transforms the provided collection of {@link MediaEntity} instances to
     * a collection of instances of type {@link Media}.
     *
     * @param source Instance of type {@link MediaEntity} that needs to be transformed to {@link
     *     Media}.
     * @return Collection of instance of type {@link Media}.
     */
    default Collection<Media> transformListTo(Collection<MediaEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link MediaEntity} instances to a
     * list of instances of type {@link Media}.
     *
     * @param source Instance of type {@link MediaEntity} that needs to be transformed to {@link
     *     Media}.
     * @return List of instance of type {@link Media}.
     */
    default List<Media> transformListTo(List<MediaEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateMediaRequest} to an instance of type
     * {@link MediaEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateMediaRequest} that needs to be transformed to
     *     {@link MediaEntity}.
     * @param target Instance of type {@link MediaEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    @Mapping(source = "mediaLocTypeId", target = "mediaLocTypeId", ignore = true)
    @Mapping(source = "mediaTypeId", target = "mediaTypeId", ignore = true)
    @Mapping(source = "songId", target = "songId", ignore = true)
    void transform(UpdateMediaRequest source, @MappingTarget MediaEntity target);

    /**
     * This method transforms an instance of type {@link UpdateMediaRequest} to an instance of type
     * {@link MediaEntity}.
     *
     * @param source Instance of type {@link UpdateMediaRequest} that needs to be transformed to
     *     {@link MediaEntity}.
     * @return Instance of type {@link MediaEntity}.
     */
    @Mapping(source = "mediaLocTypeId", target = "mediaLocTypeId", ignore = true)
    @Mapping(source = "mediaTypeId", target = "mediaTypeId", ignore = true)
    @Mapping(source = "songId", target = "songId", ignore = true)
    MediaEntity transform(UpdateMediaRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateMediaRequest}
     * instances to a collection of instances of type {@link MediaEntity}.
     *
     * @param source Instance of type {@link UpdateMediaRequest} that needs to be transformed to
     *     {@link MediaEntity}.
     * @return Instance of type {@link MediaEntity}.
     */
    default Collection<MediaEntity> transformList(Collection<UpdateMediaRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
