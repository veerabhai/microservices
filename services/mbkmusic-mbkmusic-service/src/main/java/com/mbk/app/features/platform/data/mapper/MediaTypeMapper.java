/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.mediatype.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link MediaTypeEntity to {@link MediaType and vice-versa.
 *
 * @author Editor
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MediaTypeMapper {

    /**
     * This method transforms an instance of type {@link CreateMediaTypeRequest} to an instance of
     * type {@link MediaTypeEntity}.
     *
     * @param source Instance of type {@link CreateMediaTypeRequest} that needs to be transformed to
     *     {@link MediaTypeEntity}.
     * @return Instance of type {@link MediaTypeEntity}.
     */
    MediaTypeEntity transform(CreateMediaTypeRequest source);

    /**
     * This method transforms an instance of type {@link MediaTypeEntity} to an instance of type
     * {@link MediaType}.
     *
     * @param source Instance of type {@link MediaTypeEntity} that needs to be transformed to {@link
     *     MediaType}.
     * @return Instance of type {@link MediaType}.
     */
    MediaType transform(MediaTypeEntity source);

    /**
     * This method converts / transforms the provided collection of {@link MediaTypeEntity}
     * instances to a collection of instances of type {@link MediaType}.
     *
     * @param source Instance of type {@link MediaTypeEntity} that needs to be transformed to {@link
     *     MediaType}.
     * @return Collection of instance of type {@link MediaType}.
     */
    default Collection<MediaType> transformListTo(Collection<MediaTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link MediaTypeEntity} instances to a
     * list of instances of type {@link MediaType}.
     *
     * @param source Instance of type {@link MediaTypeEntity} that needs to be transformed to {@link
     *     MediaType}.
     * @return List of instance of type {@link MediaType}.
     */
    default List<MediaType> transformListTo(List<MediaTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateMediaTypeRequest} to an instance of
     * type {@link MediaTypeEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateMediaTypeRequest} that needs to be transformed to
     *     {@link MediaTypeEntity}.
     * @param target Instance of type {@link MediaTypeEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    void transform(UpdateMediaTypeRequest source, @MappingTarget MediaTypeEntity target);

    /**
     * This method transforms an instance of type {@link UpdateMediaTypeRequest} to an instance of
     * type {@link MediaTypeEntity}.
     *
     * @param source Instance of type {@link UpdateMediaTypeRequest} that needs to be transformed to
     *     {@link MediaTypeEntity}.
     * @return Instance of type {@link MediaTypeEntity}.
     */
    MediaTypeEntity transform(UpdateMediaTypeRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateMediaTypeRequest}
     * instances to a collection of instances of type {@link MediaTypeEntity}.
     *
     * @param source Instance of type {@link UpdateMediaTypeRequest} that needs to be transformed to
     *     {@link MediaTypeEntity}.
     * @return Instance of type {@link MediaTypeEntity}.
     */
    default Collection<MediaTypeEntity> transformList(Collection<UpdateMediaTypeRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
