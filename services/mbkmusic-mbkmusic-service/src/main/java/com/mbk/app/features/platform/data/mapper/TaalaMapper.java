/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.taala.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link TaalaEntity to {@link Taala and vice-versa.
 *
 * @author Editor
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TaalaMapper {

    /**
     * This method transforms an instance of type {@link CreateTaalaRequest} to an instance of type
     * {@link TaalaEntity}.
     *
     * @param source Instance of type {@link CreateTaalaRequest} that needs to be transformed to
     *     {@link TaalaEntity}.
     * @return Instance of type {@link TaalaEntity}.
     */
    TaalaEntity transform(CreateTaalaRequest source);

    /**
     * This method transforms an instance of type {@link TaalaEntity} to an instance of type {@link
     * Taala}.
     *
     * @param source Instance of type {@link TaalaEntity} that needs to be transformed to {@link
     *     Taala}.
     * @return Instance of type {@link Taala}.
     */
    Taala transform(TaalaEntity source);

    /**
     * This method converts / transforms the provided collection of {@link TaalaEntity} instances to
     * a collection of instances of type {@link Taala}.
     *
     * @param source Instance of type {@link TaalaEntity} that needs to be transformed to {@link
     *     Taala}.
     * @return Collection of instance of type {@link Taala}.
     */
    default Collection<Taala> transformListTo(Collection<TaalaEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link TaalaEntity} instances to a
     * list of instances of type {@link Taala}.
     *
     * @param source Instance of type {@link TaalaEntity} that needs to be transformed to {@link
     *     Taala}.
     * @return List of instance of type {@link Taala}.
     */
    default List<Taala> transformListTo(List<TaalaEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateTaalaRequest} to an instance of type
     * {@link TaalaEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateTaalaRequest} that needs to be transformed to
     *     {@link TaalaEntity}.
     * @param target Instance of type {@link TaalaEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateTaalaRequest source, @MappingTarget TaalaEntity target);

    /**
     * This method transforms an instance of type {@link UpdateTaalaRequest} to an instance of type
     * {@link TaalaEntity}.
     *
     * @param source Instance of type {@link UpdateTaalaRequest} that needs to be transformed to
     *     {@link TaalaEntity}.
     * @return Instance of type {@link TaalaEntity}.
     */
    TaalaEntity transform(UpdateTaalaRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateTaalaRequest}
     * instances to a collection of instances of type {@link TaalaEntity}.
     *
     * @param source Instance of type {@link UpdateTaalaRequest} that needs to be transformed to
     *     {@link TaalaEntity}.
     * @return Instance of type {@link TaalaEntity}.
     */
    default Collection<TaalaEntity> transformList(Collection<UpdateTaalaRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
