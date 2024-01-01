/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.medialocationtype.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link MediaLocationTypeEntity to {@link MediaLocationType and vice-versa.
 *
 * @author Editor
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MediaLocationTypeMapper {

    /**
     * This method transforms an instance of type {@link CreateMediaLocationTypeRequest} to an
     * instance of type {@link MediaLocationTypeEntity}.
     *
     * @param source Instance of type {@link CreateMediaLocationTypeRequest} that needs to be
     *     transformed to {@link MediaLocationTypeEntity}.
     * @return Instance of type {@link MediaLocationTypeEntity}.
     */
    MediaLocationTypeEntity transform(CreateMediaLocationTypeRequest source);

    /**
     * This method transforms an instance of type {@link MediaLocationTypeEntity} to an instance of
     * type {@link MediaLocationType}.
     *
     * @param source Instance of type {@link MediaLocationTypeEntity} that needs to be transformed
     *     to {@link MediaLocationType}.
     * @return Instance of type {@link MediaLocationType}.
     */
    MediaLocationType transform(MediaLocationTypeEntity source);

    /**
     * This method converts / transforms the provided collection of {@link MediaLocationTypeEntity}
     * instances to a collection of instances of type {@link MediaLocationType}.
     *
     * @param source Instance of type {@link MediaLocationTypeEntity} that needs to be transformed
     *     to {@link MediaLocationType}.
     * @return Collection of instance of type {@link MediaLocationType}.
     */
    default Collection<MediaLocationType> transformListTo(
            Collection<MediaLocationTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link MediaLocationTypeEntity}
     * instances to a list of instances of type {@link MediaLocationType}.
     *
     * @param source Instance of type {@link MediaLocationTypeEntity} that needs to be transformed
     *     to {@link MediaLocationType}.
     * @return List of instance of type {@link MediaLocationType}.
     */
    default List<MediaLocationType> transformListTo(List<MediaLocationTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateMediaLocationTypeRequest} to an
     * instance of type {@link MediaLocationTypeEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateMediaLocationTypeRequest} that needs to be
     *     transformed to {@link MediaLocationTypeEntity}.
     * @param target Instance of type {@link MediaLocationTypeEntity} that will be updated instead
     *     of creating and returning a new instance.
     */
    void transform(
            UpdateMediaLocationTypeRequest source, @MappingTarget MediaLocationTypeEntity target);

    /**
     * This method transforms an instance of type {@link UpdateMediaLocationTypeRequest} to an
     * instance of type {@link MediaLocationTypeEntity}.
     *
     * @param source Instance of type {@link UpdateMediaLocationTypeRequest} that needs to be
     *     transformed to {@link MediaLocationTypeEntity}.
     * @return Instance of type {@link MediaLocationTypeEntity}.
     */
    MediaLocationTypeEntity transform(UpdateMediaLocationTypeRequest source);

    /**
     * This method converts / transforms the provided collection of {@link
     * UpdateMediaLocationTypeRequest} instances to a collection of instances of type {@link
     * MediaLocationTypeEntity}.
     *
     * @param source Instance of type {@link UpdateMediaLocationTypeRequest} that needs to be
     *     transformed to {@link MediaLocationTypeEntity}.
     * @return Instance of type {@link MediaLocationTypeEntity}.
     */
    default Collection<MediaLocationTypeEntity> transformList(
            Collection<UpdateMediaLocationTypeRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
