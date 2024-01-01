/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.data.repository;

import com.mbk.app.commons.data.jpa.repository.*;
import com.mbk.app.features.platform.data.model.persistence.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Repository interface to handle the operations pertaining to domain models of type "SongEntity".
 *
 * @author Editor
 */
@Repository
public interface SongRepository extends ExtendedJpaRepository<SongEntity, Integer> {

    /**
     * This method extracts search data depending on search inputs.
     * @param title for title
     * @param singer for name of singer
     * @param composer for name of composer
     * @param recordingYearStartDate for recording year
     * @param recordingYearEndDate for recording year
     * @param taalaDescription for taala
     * @param raagaDescription for raaga
     * @param pageSettings for page
     * @return A page of song instances where each element in the page is an instance of type {@link
     *        SongEntity
     */
    @Query(
            value =
                    "SELECT entity FROM SongEntity entity WHERE (LOWER(entity.title) LIKE LOWER(concat('%', :title, '%'))) AND " +
                            "(LOWER(entity.singer) LIKE LOWER(concat('%', :singer, '%'))) AND " +
                            "(LOWER(entity.composer) LIKE LOWER(concat('%', :composer, '%'))) AND " +
                            "((entity.recordingDate >= :recordingYearStartDate) AND (entity.recordingDate <= :recordingYearEndDate )) AND " +
                            "(LOWER(entity.raagaId.description) LIKE LOWER(concat('%', :raagaDescription, '%'))) AND " +
                            "(LOWER(entity.taalaId.description) LIKE LOWER(concat('%', :taalaDescription, '%')))")
    Page<SongEntity> searchWithAnd(
            final String title,
            final String singer,
            final String composer,
            final Date recordingYearStartDate,
            final Date recordingYearEndDate,
            final String raagaDescription,
            final String taalaDescription,
            final Pageable pageSettings);

    /**
     * This method extracts search data depending on search inputs.
     * @param title for title
     * @param singer for name of singer
     * @param composer for name of composer
     * @param taalaDescription for taala
     * @param raagaDescription for raaga
     * @param pageSettings for page
     * @return A page of song instances where each element in the page is an instance of type {@link
     *        SongEntity
     */
    @Query(
            value =
                    "SELECT entity FROM SongEntity entity WHERE (LOWER(entity.title) LIKE LOWER(concat('%', :title, '%'))) AND " +
                            "(LOWER(entity.singer) LIKE LOWER(concat('%', :singer, '%'))) AND " +
                            "(LOWER(entity.composer) LIKE LOWER(concat('%', :composer, '%'))) AND " +
                            "(LOWER(entity.raagaId.description) LIKE LOWER(concat('%', :raagaDescription, '%'))) AND " +
                            "(LOWER(entity.taalaId.description) LIKE LOWER(concat('%', :taalaDescription, '%')))")
    Page<SongEntity> searchWithAnd(
            final String title,
            final String singer,
            final String composer,
            final String raagaDescription,
            final String taalaDescription,
            final Pageable pageSettings);



    /**
     * This method extracts search data depending on search inputs.
     * @param title for title
     * @param singer for name of singer
     * @param composer for name of composer
     * @param recordingYearStartDate for recording year
     * @param recordingYearEndDate for recording year
     * @param taalaDescription for taala
     * @param raagaDescription for raaga
     * @param pageSettings for page
     * @return A page of song instances where each element in the page is an instance of type {@link
     *        SongEntity
     */
    @Query(
            value = "SELECT entity FROM SongEntity entity WHERE (LOWER(entity.title) LIKE LOWER(concat('%', :title, '%'))) " +
                    "OR (LOWER(entity.singer) LIKE LOWER(concat('%', :singer, '%'))) OR " +
                    "(LOWER(entity.composer) LIKE LOWER(concat('%', :composer, '%'))) OR" +
                    "((entity.recordingDate >= :recordingYearStartDate) AND (entity.recordingDate <= :recordingYearEndDate )) OR " +
                    "(LOWER(entity.raagaId.description) LIKE LOWER(concat(:raagaDescription))) OR " +
                    "(LOWER(entity.taalaId.description) LIKE LOWER(concat(:taalaDescription ))) ")
    Page<SongEntity> searchWithOr(
            final String title,
            final String singer,
            final String composer,
            final Date recordingYearStartDate,
            final Date recordingYearEndDate,
            final String raagaDescription,
            final String taalaDescription,
            final Pageable pageSettings);
    @Query(value = "SELECT entity FROM SongEntity entity WHERE entity.createdBy = :userId  ORDER BY title")
    List<SongEntity> findAllSongsByUserId(final String userId);



    @Query(value = "SELECT entity FROM SongEntity entity where entity.singer !='unknown' and entity.singer !='Unknown' order by entity.singer ASC")
    List<SongEntity> findAllSingers();

          @Query(value =  "SELECT username FROM mbk_auth_schema.user",nativeQuery = true)
          List<String> allUsers();

         @Query(value = "SELECT id FROM mbk_auth_schema.user WHERE username = :username",nativeQuery = true)
              Integer getUserIdBasedOnUsername(String username);
         @Query(value = "SELECT role_id FROM mbk_auth_schema.user_role WHERE user_id = :userId",nativeQuery = true)
            String getRoleIdUsingUserId(Integer userId);

        @Query(value = "SELECT name FROM mbk_auth_schema.role WHERE id = :roleId",nativeQuery = true)
        String getRoleUsingRoleId(String roleId);

    @Query(value = "SELECT * FROM mbk_music.song ORDER BY title", nativeQuery = true)
    List<SongEntity> findAllForAdmin();

}
