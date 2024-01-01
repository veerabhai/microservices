/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbk.app.commons.data.utils.PageUtils;
import com.mbk.app.commons.instrumentation.Instrument;
import com.mbk.app.configuration.StorageService;
import com.mbk.app.features.platform.data.mapper.MediaMapper;
import com.mbk.app.features.platform.data.mapper.MediaTypeMapper;
import com.mbk.app.features.platform.data.mapper.SongMapper;
import com.mbk.app.features.platform.data.model.experience.media.CreateMediaRequest;
import com.mbk.app.features.platform.data.model.experience.media.UpdateMediaRequest;
import com.mbk.app.features.platform.data.model.experience.mediatype.CreateMediaTypeRequest;
import com.mbk.app.features.platform.data.model.experience.mediatype.MediaType;
import com.mbk.app.features.platform.data.model.experience.mediatype.UpdateMediaTypeRequest;
import com.mbk.app.features.platform.data.model.experience.song.CreateSongRequest;
import com.mbk.app.features.platform.data.model.experience.song.PatchSongRequest;
import com.mbk.app.features.platform.data.model.experience.song.Song;
import com.mbk.app.features.platform.data.model.experience.song.UpdateSongRequest;
import com.mbk.app.features.platform.data.model.persistence.*;
import com.mbk.app.features.platform.data.repository.MediaRepository;
import com.mbk.app.features.platform.data.repository.MediaTypeRepository;
import com.mbk.app.features.platform.data.repository.SongRepository;

import com.mbk.app.security.utils.AuthenticationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link SongEntity}.
 *
 * @author Editor
 */
@Slf4j
@Validated
@Service
public class SongService {
    /**
     * Repository implementation of type {@link SongRepository}.
     */
    private final SongRepository songRepository;

    /**
     * Mapper implementation of type {@link SongMapper} to transform between different types.
     */
    private final SongMapper songMapper;
    private final MediaMapper mediaMapper;
    private final MediaTypeMapper mediaTypeMapper;
    private final MediaTypeRepository mediaTypeRepository;
    private final MediaRepository mediaRepository;
    //@Autowired
   // private final UserEntity userEntity;
    //@Autowired
//    private  final UserService userService;
//    private final UserRepository userRepository;
    @Autowired
    private StorageService storageService;


    /**
     * Constructor.
     *
     * @param songRepository      Repository instance of type {@link SongRepository}.
     * @param songMapper          Mapper instance of type {@link SongMapper}.
     * @param mediaMapper
     * @param mediaTypeMapper
     * @param mediaTypeRepository
     * @param mediaRepository
     */
    public SongService(final SongRepository songRepository, final SongMapper songMapper, MediaMapper mediaMapper, MediaTypeMapper mediaTypeMapper, MediaTypeRepository mediaTypeRepository, MediaRepository mediaRepository) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.mediaMapper = mediaMapper;
        this.mediaTypeMapper = mediaTypeMapper;
        this.mediaTypeRepository = mediaTypeRepository;
        this.mediaRepository = mediaRepository;
//        this.userService = userService;
//
//        this.userRepository = userRepository;
    }

    /**
     * This method attempts to create an instance of type {@link SongEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *                SongEntity}.
     * @return An experience model of type {@link Song} that represents the newly created entity of
     * type {@link SongEntity}.
     */


    @Instrument
    @Transactional
    public Song createSong(@Valid final CreateSongRequest payload, final String mediadescription, final String url) {
        // 1. Transform the experience model to a persistence model.
        payload.setCreatedBy(AuthenticationUtils.getPrincipalId());
        payload.setUpdatedBy(AuthenticationUtils.getPrincipalId());
        payload.setCreatedAt(new Date(System.currentTimeMillis()));
        payload.setUpdatedAt(null);
        final SongEntity songEntity = songMapper.transform(payload);
        // String userId
        // 2. Save the entity.
        //songEntity.setCreatedBy(AuthenticationUtils.getPrincipalOrThrow().getUsername());
        final SongEntity newInstance = songRepository.save(songEntity);
        SongService.LOGGER.debug("created by{}", AuthenticationUtils.getPrincipalId());
        CreateMediaTypeRequest createMediaTypeRequest = new CreateMediaTypeRequest();
        createMediaTypeRequest.setCreatedAt(newInstance.getCreatedAt());
        createMediaTypeRequest.setUpdatedAt(newInstance.getUpdatedAt());
        createMediaTypeRequest.setDescription(mediadescription);
        final MediaTypeEntity mediaTypeEntity = mediaTypeMapper.transform(createMediaTypeRequest);
        // mediaTypeEntity.setId(10);
        final MediaTypeEntity oldInstance = mediaTypeRepository.save(mediaTypeEntity);
        CreateMediaRequest createMediaRequest = new CreateMediaRequest();
        createMediaRequest.setCreatedAt(newInstance.getCreatedAt());
        createMediaRequest.setSongId(newInstance.getId());
        createMediaRequest.setMediaTypeId(oldInstance.getId());
        createMediaRequest.setMediaLocTypeId(1);
        //createMediaRequest.setMediaLocTypeId(2);
        createMediaRequest.setUrl(url);
        if (url.contains("youtube")) {
            String[] externalmedia = url.split("=");
            createMediaRequest.setExternalMediaId(externalmedia[1]);
        } else {
            String externalMeadiaId = null;
            try {
                externalMeadiaId = URLDecoder.decode(url.split("/")[url.split("/").length - 1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            createMediaRequest.setExternalMediaId(externalMeadiaId);
        }

        createMediaRequest.setUpdatedAt(newInstance.getUpdatedAt());
        final MediaEntity mediaEntity = mediaMapper.transform(createMediaRequest);
        final MediaEntity olderInstance = mediaRepository.save(mediaEntity);
        // 3. Transform the created entity to an experience model and return it.
        return songMapper.transform(newInstance);
    }

    @Instrument
    @Transactional
    public Song createSong_(@Valid final CreateSongRequest payload, final String mediadescription, final String url) {
        // 1. Transform the experience model to a persistence model.

        payload.setCreatedBy(AuthenticationUtils.getPrincipalId());
        payload.setUpdatedBy(AuthenticationUtils.getPrincipalId());
        payload.setCreatedAt(new Date());
        payload.setUpdatedAt(null);
        final SongEntity songEntity = songMapper.transform(payload);
        // String userId
        // 2. Save the entity.
        SongService.LOGGER.debug("Saving a new instance of type - SongEntity");
        //songEntity.setCreatedBy(AuthenticationUtils.getPrincipalOrThrow().getUsername());
        final SongEntity newInstance = songRepository.save(songEntity);
        SongService.LOGGER.debug("created by{}", AuthenticationUtils.getPrincipalId());

        CreateMediaTypeRequest createMediaTypeRequest = new CreateMediaTypeRequest();
        createMediaTypeRequest.setCreatedAt(newInstance.getCreatedAt());
        createMediaTypeRequest.setUpdatedAt(newInstance.getUpdatedAt());
        createMediaTypeRequest.setDescription(mediadescription);
        final MediaTypeEntity mediaTypeEntity = mediaTypeMapper.transform(createMediaTypeRequest);
        // mediaTypeEntity.setId(10);
        final MediaTypeEntity oldInstance = mediaTypeRepository.save(mediaTypeEntity);
        CreateMediaRequest createMediaRequest = new CreateMediaRequest();
        createMediaRequest.setCreatedAt(newInstance.getCreatedAt());
        createMediaRequest.setSongId(newInstance.getId());
        createMediaRequest.setMediaTypeId(oldInstance.getId());
        createMediaRequest.setMediaLocTypeId(1);
        //createMediaRequest.setMediaLocTypeId(2);

        createMediaRequest.setUrl(url);

        String externalMeadiaId = null;
        try {
            externalMeadiaId = URLDecoder.decode(url.split("/")[url.split("/").length - 1], "UTF-8");
        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException(e);
        }
        createMediaRequest.setExternalMediaId(externalMeadiaId);
        //createMediaRequest.setExternalMediaId("");
        createMediaRequest.setUpdatedAt(newInstance.getUpdatedAt());
        final MediaEntity mediaEntity = mediaMapper.transform(createMediaRequest);
        final MediaEntity olderInstance = mediaRepository.save(mediaEntity);
        // 3. Transform the created entity to an experience model and return it.
        return songMapper.transform(newInstance);
    }


    /**
     * This method attempts to update an existing instance of type {@link SongEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateSongRequest}.
     *
     * @param songId  Unique identifier of Song in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Song, which needs to be
     *                updated in the system.
     * @return A instance of type {@link Song} containing the updated details.
     */
    @Instrument
    @Transactional
    public Song updateSong(final Integer songId, @Valid final UpdateSongRequest payload, final String mediadescription, final String url) {
        // 1. Verify that the entity being updated truly exists.
        final SongEntity matchingInstance = songRepository.findByIdOrThrow(songId);
        payload.setUpdatedBy(AuthenticationUtils.getPrincipalId());
        payload.setCreatedAt(matchingInstance.getCreatedAt());
        payload.setUpdatedAt(new Date(System.currentTimeMillis()));
        matchingInstance.setUpdatedAt(new Date(System.currentTimeMillis()));

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        songMapper.transform(payload, matchingInstance);
        UpdateMediaTypeRequest updateMediaTypeRequest = new UpdateMediaTypeRequest();
        updateMediaTypeRequest.setCreatedAt(matchingInstance.getCreatedAt());
        updateMediaTypeRequest.setUpdatedAt(matchingInstance.getUpdatedAt());
        updateMediaTypeRequest.setDescription(mediadescription);
        final MediaTypeEntity mediaTypeEntity = mediaTypeMapper.transform(updateMediaTypeRequest);
        final MediaTypeEntity oldInstance = mediaTypeRepository.save(mediaTypeEntity);
        UpdateMediaRequest updateMediaRequest = new UpdateMediaRequest();
        updateMediaRequest.setCreatedAt(matchingInstance.getCreatedAt());
        updateMediaRequest.setSongId(matchingInstance.getId());
        updateMediaRequest.setMediaTypeId(oldInstance.getId());
        updateMediaRequest.setMediaLocTypeId(1);
        if (url.contains("youtube")) {
            updateMediaRequest.setUrl(url);
            String[] externalmedia = url.split("=");
            updateMediaRequest.setExternalMediaId(externalmedia[1]);
        } else {
            updateMediaRequest.setUrl(mediaRepository.findBySongId(songId).getUrl());
            updateMediaRequest.setExternalMediaId(mediaRepository.findBySongId(songId).getExternalMediaId());
        }
        updateMediaRequest.setUpdatedAt(matchingInstance.getUpdatedAt());
        final MediaEntity mediaEntity = mediaMapper.transform(updateMediaRequest);
        final MediaEntity olderInstance = mediaRepository.save(mediaEntity);

        // 3. Save the entity
        SongService.LOGGER.debug("Saving the updated entity - SongEntity");

        MediaEntity mediaEntity1 = mediaRepository.getOne(songId);
        mediaRepository.deleteOne(mediaEntity1.getId());
        final SongEntity updatedInstance = songRepository.save(matchingInstance);
        // 4. Transform updated entity to output object
        return songMapper.transform(updatedInstance);


    }

    /**
     * This method attempts to update an existing instance of type {@link SongEntity} using the
     * details from the provided input, which is an instance of type {@link PatchSongRequest}.
     *
     * @param songId  Unique identifier of Song in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Song, which needs to be
     *                updated in the system.
     * @return A instance of type {@link Song} containing the updated details.
     */
    @Instrument
    @Transactional
    public Song patchSong(final Integer songId, @Valid final PatchSongRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final SongEntity matchingInstance = songRepository.findByIdOrThrow(songId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        final ObjectMapper oMapper = new ObjectMapper();
        final Map<String, Object> saveInstance = oMapper.convertValue(payload, Map.class);
        saveInstance.forEach(
                (key, value) -> {
                    if (value != null) {
                        Field field = ReflectionUtils.findField(SongEntity.class, key);
                        Objects.requireNonNull(field).setAccessible(true);
                        ReflectionUtils.setField(field, matchingInstance, value);
                    }
                });

        // 3. Save the entity
        SongService.LOGGER.debug("Saving the updated entity - SongEntity");
        final SongEntity updatedInstance = songRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return songMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link SongEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param songId Unique identifier of Song in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Song} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Song findSong(final Integer songId) {
        // 1. Find a matching entity and throw an exception if not found.
        final SongEntity matchingInstance = songRepository.findByIdOrThrow(songId);
        final MediaEntity mediaEntity = mediaRepository.findBySongId(songId);
        final Song song = songMapper.transform(matchingInstance);
        song.setMediaurl(mediaEntity.getUrl());
        return song;
    }


    /**
     * This method attempts to find a {@link SongEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param songId Unique identifier of Song in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Song} if found, else returns null.
     */
//    @Instrument
//    @Transactional(readOnly = true)
//    public Song findSongByUserId(final Integer songId) {
//        // 1. Find a matching entity and throw an exception if not found.
//        String userId = AuthenticationUtils.getPrincipalId();
//        List<SongEntity> songList;
//        songList = songRepository.findAllSongsByUserId(userId);
//        List<Song> songs = songMapper.transformListTo(songList);
//        Song matchingSong = null;
//
//        for (Song song : songs) {
//            if (Objects.equals(song.getId(), songId)) {
//                matchingSong = song;
//            }
//        }
//        //final SongEntity matchingInstance = songRepository.findByIdOrThrow(songId);
//        if(Objects.nonNull(matchingSong)){
//            final MediaEntity mediaEntity = mediaRepository.findBySongId(songId);
//            matchingSong.setMediaurl(mediaEntity.getUrl());
//
//            return matchingSong;
//        }
//        return null;
//    }
    @Instrument
    @Transactional(readOnly = true)
    public Song findSongByUserId(final Integer songId) {
        // CreateMediaTypeRequest createMediaTypeRequest = new CreateMediaTypeRequest();
        MediaTypeEntity newinstance = new MediaTypeEntity();
        final MediaType mediaType = mediaTypeMapper.transform(newinstance);
        // 1. Find a matching entity and throw an exception if not found.
        String username = AuthenticationUtils.getPrincipalId();
        List<String> users = songRepository.allUsers();

        Integer userId = songRepository.getUserIdBasedOnUsername(username);

        String roleId = songRepository.getRoleIdUsingUserId(userId);


        String roleName = songRepository.getRoleUsingRoleId(roleId);

        List<SongEntity> songList;
        if (roleName.equalsIgnoreCase("Administrator")) {
            songList = songRepository.findAll();
        } else {
            songList = songRepository.findAllSongsByUserId(username);
        }

       // songList = songRepository.findAllSongsByUserId(username);
        List<Song> songs = songMapper.transformListTo(songList);
        Song matchingSong = null;
        for (Song song : songs) {
            if (song.getId() == (int) songId) {

                matchingSong = song;
            }
        }
        //final SongEntity matchingInstance = songRepository.findByIdOrThrow(songId);
        if (Objects.nonNull(matchingSong)) {
            final MediaEntity mediaEntity = mediaRepository.findBySongId(songId);
            String url = mediaEntity.getUrl();
            if (url.contains("youtube")) {
                matchingSong.setMediaurl(url);
                matchingSong.setExternalMediaId(mediaEntity.getExternalMediaId());
            } else {
                matchingSong.setMediaurl(mediaEntity.getExternalMediaId());
                matchingSong.setExternalMediaId(mediaEntity.getExternalMediaId());
            }
            final Optional<MediaTypeEntity> mediaTypeEntity = mediaTypeRepository.findById(mediaEntity.getMediaTypeId().getId());
            if (mediaTypeEntity.isPresent()) {

                matchingSong.setMediadescription(mediaTypeEntity.get().getDescription());
            }
            return matchingSong;
        }
        return null;
    }

    /**
     * This method attempts to find instances of type SongEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     * returned page is an instance of type {@link Song}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Song> findAllSongs(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        SongService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<SongEntity> pageData = songRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Song> dataToReturn =
                    pageData.getContent().stream()
                            .map(songMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link SongEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param songId Unique identifier of Song in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type SongEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteSong(final Integer songId) {

        MediaEntity mediaEntity = mediaRepository.getOne(songId);
        mediaRepository.deleteOne(mediaEntity.getId());

        // 1. Delegate to our repository method to handle the deletion.
        return songRepository.deleteOne(songId);
    }


    /**
     * This method attempts to search instances of type SongEntity based on the provided request
     * param. A case-insensitive search will be performed while matching the provided search string.
     *
     * @return Returns a page of matching SongEntity based on the provided request param. Each
     *     object in the returned page is an instance of type {@link Song}.
     */
    /**
     * This method attempts to search instances of type SongEntity based on the provided request
     * param. A case-insensitive search will be performed while matching the provided search string.
     *
     * @return Returns a page of matching SongEntity based on the provided request param. Each
     * object in the returned page is an instance of type {@link Song}.
     */
    @Instrument
    @Transactional
    public Page<Song> searchSongWithOr(
            final String title,
            final String singer,
            final String composer,
            final String recordingYearStart,
            final String recordingYearEnd,
            final String raagaDescription,
            final String taalaDescription,
            final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        List<SongEntity> songList;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(recordingYearStart);
        } catch (ParseException e) {
            Calendar calndr = Calendar.getInstance();
            calndr.set(Calendar.YEAR, 1996);
            date1 = calndr.getTime();
        }

        try {
            date2 = formatter.parse(recordingYearEnd);
        } catch (ParseException e) {
            date2 = Calendar.getInstance().getTime();
        }
        songList = songRepository.searchWithOr(title, singer, composer, date1, date2, raagaDescription, taalaDescription, pageSettings)
                .getContent();
//        List<Song> Songs = songMapper.transformListTo(songEntity);
        List<Song> Songs = songMapper.transformListTo(songList);
        List<Song> songswithMediaurl = Songs.stream().map(song -> {
            MediaEntity mediaEntity = mediaRepository.findUrl(song.getId());
            if (Objects.nonNull(mediaEntity)) {
                song.setExternalMediaId(mediaEntity.getExternalMediaId());
                song.setMediaurl(mediaEntity.getUrl());
            }


            return song;
        }).collect(Collectors.toList());
        return PageUtils.createPage(songswithMediaurl, pageSettings, Songs.size());
    }


    /**
     * This method attempts to search instances of type SongEntity based on the provided request
     * param. A case-insensitive search will be performed while matching the provided search string.
     *
     * @return Returns a page of matching SongEntity based on the provided request param. Each
     * object in the returned page is an instance of type {@link Song}.
     */
    @Instrument
    @Transactional
    public Page<Song> searchSongWithAnd(
            final String title,
            final String singer,
            final String composer,
            final String recordingYearStart,
            final String recordingYearEnd,
            final String raagaDescription,
            final String taalaDescription,
            final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        List<SongEntity> songList;

        if (recordingYearStart.equals("") && recordingYearEnd.equals("")) {

            songList = songRepository.searchWithAnd(title, singer, composer, raagaDescription, taalaDescription, pageSettings)
                    .getContent();
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
            Date date1 = null;
            Date date2 = null;

            try {
                date1 = formatter.parse(recordingYearStart);
            } catch (ParseException e) {
                Calendar calndr = Calendar.getInstance();
                calndr.set(Calendar.YEAR, 1996);
                date1 = calndr.getTime();
            }

            try {
                date2 = formatter.parse(recordingYearEnd);
            } catch (ParseException e) {
                date2 = Calendar.getInstance().getTime();
            }

            songList = songRepository.searchWithAnd(title, singer, composer, date1, date2, raagaDescription, taalaDescription, pageSettings)
                    .getContent();
        }
        List<Song> Songs = songMapper.transformListTo(songList);
        List<Song> songswithMediaurl = Songs.stream().map(song -> {
            MediaEntity mediaEntity = mediaRepository.findUrl(song.getId());
            if (Objects.nonNull(mediaEntity)) {
                song.setExternalMediaId(mediaEntity.getExternalMediaId());
                song.setMediaurl(mediaEntity.getUrl());
            }
            return song;
        }).collect(Collectors.toList());
        return PageUtils.createPage(songswithMediaurl, pageSettings, Songs.size());
    }

    /**
     * This method attempts to find instances of type SongEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     * returned page is an instance of type {@link Song}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public List<Song> findAllSongsByUserId(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        SongService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());
        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        String username = AuthenticationUtils.getPrincipalId();
        List<String> users = songRepository.allUsers();

        Integer userId = songRepository.getUserIdBasedOnUsername(username);

       String roleId = songRepository.getRoleIdUsingUserId(userId);


       String roleName = songRepository.getRoleUsingRoleId(roleId);

        List<SongEntity> songList;

        if (roleName.equalsIgnoreCase("Administrator")) {
            songList = songRepository.findAllForAdmin();
        } else {
            songList = songRepository.findAllSongsByUserId(username);
        }

        // 3. If the page has data, transform each element into target type.
        List<Song> songs = songMapper.transformListTo(songList);

        List<Song> songswithMediaurl = songs.stream().map(song -> {
            MediaEntity mediaEntity = mediaRepository.findUrl(song.getId());


            if (Objects.nonNull(mediaEntity)) {

                song.setMediaurl(mediaEntity.getUrl());
                song.setExternalMediaId(mediaEntity.getExternalMediaId());

            }


            return song;
        }).collect(Collectors.toList());
        // return PageUtils.createPage(songswithMediaurl, pageSettings, Songs.size());
        return songswithMediaurl;
    }


    /**
     * This method attempts to delete an existing instance of type {@link SongEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param songId Unique identifier of Song in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type SongEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteSongByUserId(final Integer songId) {

        MediaEntity mediaEntity = mediaRepository.findBySongId(songId);
        mediaRepository.deleteOne(mediaEntity.getId());
        String fileName = mediaEntity.getExternalMediaId();
        storageService.deleteFile(fileName);

        // 1. Delegate to our repository method to handle the deletion.
        return songRepository.deleteOne(songId);
    }


    /**
     * This method attempts to update an existing instance of type {@link SongEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateSongRequest}.
     *
     * @param songId  Unique identifier of Song in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Song, which needs to be
     *                updated in the system.
     * @return A instance of type {@link Song} containing the updated details.
     */
    @Instrument
    @Transactional
    public Song updateSongByUserId(final Integer songId, @Valid final UpdateSongRequest payload, final String url,
                                   final String mediadescription) throws Exception {
        // 1. Verify that the entity being updated truly exists.
        final SongEntity matchingInstance = songRepository.findByIdOrThrow(songId);
        payload.setUpdatedBy(AuthenticationUtils.getPrincipalId());
        payload.setCreatedAt(matchingInstance.getCreatedAt());
        payload.setUpdatedAt(new Date(System.currentTimeMillis()));
        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.

        songMapper.transform(payload, matchingInstance);
        // 3. Save the entity
        SongService.LOGGER.debug("Saving the updated entity - SongEntity");

        MediaEntity mediaEntity = mediaRepository.findBySongId(songId);
        if (url.contains("youtube") && mediaEntity.getUrl().contains("youtube")) {
            mediaEntity.setUrl(url);
            String[] externalmedia = url.split("=");
            mediaEntity.setExternalMediaId(externalmedia[1]);
        } else if (storageService.isUrlPresent(url)) {
            if (url.contains(".mp3") && mediadescription.equalsIgnoreCase("audio")) {
                mediaEntity.setUrl(storageService.objectUrl(url));
                mediaEntity.setExternalMediaId(url);
            } else if (url.contains(".mp4") && mediadescription.equalsIgnoreCase("video")) {
                mediaEntity.setUrl(storageService.objectUrl(url));
                mediaEntity.setExternalMediaId(url);
            } else throw new Exception("update failed");
        } else throw new Exception("update failed");

        Optional<MediaTypeEntity> mediaTypeEntity = mediaTypeRepository.findById(mediaEntity.getMediaTypeId().getId());
        mediaTypeEntity.get().setDescription(mediadescription);
        mediaRepository.save(mediaEntity);
        final SongEntity updatedInstance = songRepository.save(matchingInstance);
        // 4. Transform updated entity to output object
        return songMapper.transform(updatedInstance);
    }

    public List<SongEntity> singers() {

        LinkedHashMap<String, SongEntity> lhm = new LinkedHashMap<>();
        for (SongEntity se : songRepository.findAllSingers()) {
            lhm.put(se.getSinger(), se);
        }
        List<SongEntity> listOfSongs = (lhm.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new)))
                .entrySet().stream()
                .map(x -> x.getValue()).collect(Collectors
                        .toCollection(ArrayList::new));
        return listOfSongs;
    }


}
