/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.mapper;

import com.mbk.app.features.platform.data.model.experience.note.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link NoteEntity to {@link Note and vice-versa.
 *
 * @author Editor
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface NoteMapper {

    /**
     * This method transforms an instance of type {@link CreateNoteRequest} to an instance of type
     * {@link NoteEntity}.
     *
     * @param source Instance of type {@link CreateNoteRequest} that needs to be transformed to
     *     {@link NoteEntity}.
     * @return Instance of type {@link NoteEntity}.
     */
    NoteEntity transform(CreateNoteRequest source);

    /**
     * This method transforms an instance of type {@link NoteEntity} to an instance of type {@link
     * Note}.
     *
     * @param source Instance of type {@link NoteEntity} that needs to be transformed to {@link
     *     Note}.
     * @return Instance of type {@link Note}.
     */
    Note transform(NoteEntity source);

    /**
     * This method converts / transforms the provided collection of {@link NoteEntity} instances to
     * a collection of instances of type {@link Note}.
     *
     * @param source Instance of type {@link NoteEntity} that needs to be transformed to {@link
     *     Note}.
     * @return Collection of instance of type {@link Note}.
     */
    default Collection<Note> transformListTo(Collection<NoteEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }

    /**
     * This method converts / transforms the provided list of {@link NoteEntity} instances to a list
     * of instances of type {@link Note}.
     *
     * @param source Instance of type {@link NoteEntity} that needs to be transformed to {@link
     *     Note}.
     * @return List of instance of type {@link Note}.
     */
    default List<Note> transformListTo(List<NoteEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toList());
    }
    /**
     * This method transforms an instance of type {@link UpdateNoteRequest} to an instance of type
     * {@link NoteEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateNoteRequest} that needs to be transformed to
     *     {@link NoteEntity}.
     * @param target Instance of type {@link NoteEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateNoteRequest source, @MappingTarget NoteEntity target);

    /**
     * This method transforms an instance of type {@link UpdateNoteRequest} to an instance of type
     * {@link NoteEntity}.
     *
     * @param source Instance of type {@link UpdateNoteRequest} that needs to be transformed to
     *     {@link NoteEntity}.
     * @return Instance of type {@link NoteEntity}.
     */
    NoteEntity transform(UpdateNoteRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateNoteRequest}
     * instances to a collection of instances of type {@link NoteEntity}.
     *
     * @param source Instance of type {@link UpdateNoteRequest} that needs to be transformed to
     *     {@link NoteEntity}.
     * @return Instance of type {@link NoteEntity}.
     */
    default Collection<NoteEntity> transformList(Collection<UpdateNoteRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
