/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.raaga.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link RaagaEntity to {@link Raaga and vice-versa.
 *
 * @author Editor
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RaagaMapper {

    /**
     * This method transforms an instance of type {@link CreateRaagaRequest} to an instance of type
     * {@link RaagaEntity}.
     *
     * @param source Instance of type {@link CreateRaagaRequest} that needs to be transformed to
     *     {@link RaagaEntity}.
     * @return Instance of type {@link RaagaEntity}.
     */
    RaagaEntity transform(CreateRaagaRequest source);

    /**
     * This method transforms an instance of type {@link RaagaEntity} to an instance of type {@link
     * Raaga}.
     *
     * @param source Instance of type {@link RaagaEntity} that needs to be transformed to {@link
     *     Raaga}.
     * @return Instance of type {@link Raaga}.
     */
    Raaga transform(RaagaEntity source);

    /**
     * This method converts / transforms the provided collection of {@link RaagaEntity} instances to
     * a collection of instances of type {@link Raaga}.
     *
     * @param source Instance of type {@link RaagaEntity} that needs to be transformed to {@link
     *     Raaga}.
     * @return Collection of instance of type {@link Raaga}.
     */
    default Collection<Raaga> transformListTo(Collection<RaagaEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link RaagaEntity} instances to a
     * list of instances of type {@link Raaga}.
     *
     * @param source Instance of type {@link RaagaEntity} that needs to be transformed to {@link
     *     Raaga}.
     * @return List of instance of type {@link Raaga}.
     */
    default List<Raaga> transformListTo(List<RaagaEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateRaagaRequest} to an instance of type
     * {@link RaagaEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateRaagaRequest} that needs to be transformed to
     *     {@link RaagaEntity}.
     * @param target Instance of type {@link RaagaEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateRaagaRequest source, @MappingTarget RaagaEntity target);

    /**
     * This method transforms an instance of type {@link UpdateRaagaRequest} to an instance of type
     * {@link RaagaEntity}.
     *
     * @param source Instance of type {@link UpdateRaagaRequest} that needs to be transformed to
     *     {@link RaagaEntity}.
     * @return Instance of type {@link RaagaEntity}.
     */
    RaagaEntity transform(UpdateRaagaRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateRaagaRequest}
     * instances to a collection of instances of type {@link RaagaEntity}.
     *
     * @param source Instance of type {@link UpdateRaagaRequest} that needs to be transformed to
     *     {@link RaagaEntity}.
     * @return Instance of type {@link RaagaEntity}.
     */
    default Collection<RaagaEntity> transformList(Collection<UpdateRaagaRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
