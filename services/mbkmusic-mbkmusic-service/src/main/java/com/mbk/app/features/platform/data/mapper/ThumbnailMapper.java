/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.mapper.decorator.*;
import com.mbk.app.features.platform.data.model.experience.thumbnail.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link ThumbnailEntity to {@link Thumbnail and vice-versa.
 *
 * @author Editor
 */
@DecoratedWith(value = ThumbnailMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ThumbnailMapper {

    /**
     * This method transforms an instance of type {@link CreateThumbnailRequest} to an instance of
     * type {@link ThumbnailEntity}.
     *
     * @param source Instance of type {@link CreateThumbnailRequest} that needs to be transformed to
     *     {@link ThumbnailEntity}.
     * @return Instance of type {@link ThumbnailEntity}.
     */
    @Mapping(source = "mediaId", target = "mediaId", ignore = true)
    ThumbnailEntity transform(CreateThumbnailRequest source);

    /**
     * This method transforms an instance of type {@link ThumbnailEntity} to an instance of type
     * {@link Thumbnail}.
     *
     * @param source Instance of type {@link ThumbnailEntity} that needs to be transformed to {@link
     *     Thumbnail}.
     * @return Instance of type {@link Thumbnail}.
     */
    @Mapping(source = "mediaId", target = "mediaId", ignore = true)
    Thumbnail transform(ThumbnailEntity source);

    /**
     * This method converts / transforms the provided collection of {@link ThumbnailEntity}
     * instances to a collection of instances of type {@link Thumbnail}.
     *
     * @param source Instance of type {@link ThumbnailEntity} that needs to be transformed to {@link
     *     Thumbnail}.
     * @return Collection of instance of type {@link Thumbnail}.
     */
    default Collection<Thumbnail> transformListTo(Collection<ThumbnailEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link ThumbnailEntity} instances to a
     * list of instances of type {@link Thumbnail}.
     *
     * @param source Instance of type {@link ThumbnailEntity} that needs to be transformed to {@link
     *     Thumbnail}.
     * @return List of instance of type {@link Thumbnail}.
     */
    default List<Thumbnail> transformListTo(List<ThumbnailEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateThumbnailRequest} to an instance of
     * type {@link ThumbnailEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateThumbnailRequest} that needs to be transformed to
     *     {@link ThumbnailEntity}.
     * @param target Instance of type {@link ThumbnailEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "mediaId", target = "mediaId", ignore = true)
    void transform(UpdateThumbnailRequest source, @MappingTarget ThumbnailEntity target);

    /**
     * This method transforms an instance of type {@link UpdateThumbnailRequest} to an instance of
     * type {@link ThumbnailEntity}.
     *
     * @param source Instance of type {@link UpdateThumbnailRequest} that needs to be transformed to
     *     {@link ThumbnailEntity}.
     * @return Instance of type {@link ThumbnailEntity}.
     */
    @Mapping(source = "mediaId", target = "mediaId", ignore = true)
    ThumbnailEntity transform(UpdateThumbnailRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateThumbnailRequest}
     * instances to a collection of instances of type {@link ThumbnailEntity}.
     *
     * @param source Instance of type {@link UpdateThumbnailRequest} that needs to be transformed to
     *     {@link ThumbnailEntity}.
     * @return Instance of type {@link ThumbnailEntity}.
     */
    default Collection<ThumbnailEntity> transformList(Collection<UpdateThumbnailRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
