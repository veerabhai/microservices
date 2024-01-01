/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.data.utils.*;
import com.mbk.app.commons.web.api.*;
import com.mbk.app.commons.web.configuration.properties.*;
import com.mbk.app.configuration.StorageService;
import com.mbk.app.features.platform.data.model.experience.song.*;
import com.mbk.app.features.platform.data.model.persistence.SongEntity;
import com.mbk.app.features.platform.web.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.*;
import lombok.extern.slf4j.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.util.*;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.mbk.app.features.platform.data.model.persistence.SongEntity}.
 *
 * @author Editor
 */
@Slf4j
@RestController
@RequestMapping(SongApi.rootEndPoint)
public class SongApi extends AuthAbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Songs";

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link SongService}. */
    private final SongService songService;

    /**
     * Constructor.
     *
     * @param songService Service instance of type {@link SongService}.
     */
    public SongApi(final SongService songService) {
        this.songService = songService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.SongEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Song}.
     */
    @Operation(
            method = "createSong",
            summary = "Create a new Song.",
            description = "This API is used to create a new Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successfully created a new Song in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })

    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/songs")
    public ResponseEntity<Song> createSong(@Valid @RequestBody final CreateSongRequest payload,
     @RequestParam String mediadescription,
     @RequestParam String url )
    {

        // Delegate to the service layer.
        final Song newInstance = songService.createSong(payload,mediadescription, url);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }
    @Autowired
    private StorageService service;


    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/s3Song", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE, })
    public ResponseEntity<?> uploadFile_(@Valid @RequestPart  final CreateSongRequest payload,
                                         @RequestPart (value = "file") MultipartFile file, @RequestParam String mediadescription) {



        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if((extension.equalsIgnoreCase("MP4") && mediadescription.equalsIgnoreCase("video")) ||
                (extension.equalsIgnoreCase("MP3") && mediadescription.equalsIgnoreCase("audio")) ||
                (extension.equalsIgnoreCase("MOV")&& mediadescription.equalsIgnoreCase("video")) ||
                (extension.equalsIgnoreCase("AVI")) ||
                (extension.equalsIgnoreCase("WMV") && mediadescription.equalsIgnoreCase("video")) ||
                (extension.equalsIgnoreCase("WMA")&& mediadescription.equalsIgnoreCase("audio")))
        {
            String url=service.uploadFile(file);
            // Delegate to the service layer.
            final Song newInstance = songService.createSong_(payload, mediadescription, url);

            // Build a response entity object and return it.
            return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
        }
        else return ResponseEntity.status(HttpStatus.CREATED).body("Uploaded File type is not compatible");

    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system.
     *
     * @param songId Unique identifier of Song in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Song, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Song}.
     */
    @Operation(
            method = "updateSong",
            summary = "Update an existing Song.",
            description = "This API is used to update an existing Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Song in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/songs/{songId}")
    public ResponseEntity<Song> updateSong(
            @PathVariable(name = "songId") final Integer songId,
            @Valid @RequestBody final UpdateSongRequest payload,
            @RequestParam String mediadescription,
            @RequestParam String url) {
        // Delegate to the service layer.
        final Song updatedInstance = songService.updateSong(songId, payload,mediadescription, url);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system.
     *
     * @param songId Unique identifier of Song in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Song, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Song}.
     */
    @Operation(
            method = "patchSong",
            summary = "Update an existing Song.",
            description = "This API is used to update an existing Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Song in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PatchMapping(value = "/songs/{songId}")
    public ResponseEntity<Song> patchSong(
            @PathVariable(name = "songId") final Integer songId,
            @Valid @RequestBody final PatchSongRequest payload) {
        // Delegate to the service layer.
        final Song updatedInstance = songService.patchSong(songId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system.
     *
     * @param songId Unique identifier of Song in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Song}.
     */
    @Operation(
            method = "findSong",
            summary = "Find an existing Song.",
            description = "This API is used to find an existing Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the details of an existing Song in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/songs/{songId}")
    public ResponseEntity<Song> findSong(@PathVariable(name = "songId") final Integer songId) {
        // Delegate to the service layer.
        final Song matchingInstance = songService.findSong(songId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system in a paginated
     * manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Song
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllSongs",
            summary = "Find all Songs.",
            description = "This API is used to find all Songs in the system.",
            tags = {SongApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the Songs in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/songs")
    public ResponseEntity<Page<Song>> findAllSongs(
           // @RequestParam(name = "description") final String description,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Song> matchingInstances = songService.findAllSongs(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system.
     *
     * @param songId Unique identifier of Song in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.SongEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteSong",
            summary = "Delete an existing Song.",
            description = "This API is used to delete an existing Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                    @SecurityRequirement(
                            name =
                                    ApiDocumentationSettings.ApiSecurityScheme
                                            .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted an existing Song in the system.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/songs/{songId}")
    public ResponseEntity<Integer> deleteSong(@PathVariable(name = "songId") final Integer songId) {
        // Delegate to the service layer.
        final Integer deletedInstance = (songService.deleteSong(songId));

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }

    /**
     * This method attempts to search for a {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} whose queryString matches
     * the provided {@code searchString} parameter.
     *
     * <p>A case-insensitive search will be performed while matching the provided search string.
     *
     * @param title
     * @param singer
     * @param composer
     * @param recordingDateStart
     * @param recordingDateEnd
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return A page of matching type Song where each element in the collection is an instance of
     *     type Song.
     */
    @Operation(
            method = "searchSong",
            summary = "Search an existing Song.",
            description =
                    "This API is used to search for Song based on the provided filter criteria.",
            tags = {SongApi.API_TAG},
            security = {
                    @SecurityRequirement(
                            name =
                                    ApiDocumentationSettings.ApiSecurityScheme
                                            .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description =
                                    "Successfully retrieved the Songs in the system based on the provided input.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/songs/searchWithOr")
    public ResponseEntity<Page<Song>> searchSongWithOr(
            @RequestParam(name = "title", required = false, defaultValue = "null") final String title,
            @RequestParam(name = "singer", required = false, defaultValue = "null") final String singer,
            @RequestParam(name = "composer", required = false, defaultValue = "null")
            final String composer,
            @RequestParam(name = "recordingYearStart", required = false, defaultValue = "null")
            final String recordingDateStart,
            @RequestParam(name = "recordingYearEnd", required = false, defaultValue = "")
            final String recordingDateEnd,
            @RequestParam(name = "raagaDescription", required = false, defaultValue = "")
            final String raagaDescription,
            @RequestParam(name = "taalaDescription", required = false, defaultValue = "")
            final String taalaDescription,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
            final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "100000") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
            final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Song> matchingInstances =
                songService.searchSongWithOr(title, singer, composer, recordingDateStart,recordingDateEnd, raagaDescription, taalaDescription, pageSettings);
        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This method attempts to search for a {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} whose queryString matches
     * the provided {@code searchString} parameter.
     *
     * <p>A case-insensitive search will be performed while matching the provided search string.
     *
     * @param title
     * @param singer
     * @param composer
     * @param recordingDateStart
     * @param recordingDateEnd
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return A page of matching type Song where each element in the collection is an instance of
     *     type Song.
     */
    @Operation(
            method = "searchSong",
            summary = "Search an existing Song.",
            description =
                    "This API is used to search for Song based on the provided filter criteria.",
            tags = {SongApi.API_TAG},
            security = {
                    @SecurityRequirement(
                            name =
                                    ApiDocumentationSettings.ApiSecurityScheme
                                            .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description =
                                    "Successfully retrieved the Songs in the system based on the provided input.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/songs/searchWithAnd")
    public ResponseEntity<Page<Song>> searchSongWithAnd(
            @RequestParam(name = "title", required = false, defaultValue = "") final String title,
            @RequestParam(name = "singer", required = false, defaultValue = "") final String singer,
            @RequestParam(name = "composer", required = false, defaultValue = "")
            final String composer,
            @RequestParam(name = "recordingYearStart", required = false, defaultValue = "null")
            final String recordingDateStart,
            @RequestParam(name = "recordingYearEnd", required = false, defaultValue = "null")
            final String recordingDateEnd,
            @RequestParam(name = "raagaDescription", required = false, defaultValue = "")
            final String raagaDescription,
            @RequestParam(name = "taalaDescription", required = false, defaultValue = "")
            final String taalaDescription,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id")
            final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "100000") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
            final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final Page<Song> matchingInstances =
                songService.searchSongWithAnd(title, singer, composer, recordingDateStart,recordingDateEnd, raagaDescription, taalaDescription,  pageSettings);
        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }


    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system in a paginated
     * manner.
     *
     * @param sortBy Sort by the property name.
     * @param page Page number.
     * @param size Page size.
     * @param direction Direction of the sorting (i.e. ASC or DESC).
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Song
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllSongsByUserId",
            summary = "Find all Songs with user.",
            description = "This API is used to find all Songs in the system.",
            tags = {SongApi.API_TAG},
            security = {
                    @SecurityRequirement(
                            name =
                                    ApiDocumentationSettings.ApiSecurityScheme
                                            .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description =
                                    "Successfully retrieved the Songs in the system based on the provided pagination settings.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/user/songs")
    public ResponseEntity<List<Song>> findAllSongsByUserId(
            @RequestParam(name = "sortBy", required = false, defaultValue = "title")
            final String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20000") final Integer size,
            @RequestParam(name = "direction", required = false, defaultValue = "asc")
            final String direction) {
        // Delegate to the service layer.
        final Pageable pageSettings =
                PageUtils.createPaginationConfiguration(page, size, sortBy, direction);
        final List<Song> matchingInstances = songService.findAllSongsByUserId(pageSettings);
        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }


    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system.
     *
     * @param songId Unique identifier of Song in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.mbk.app.features.platform.data.model.persistence.SongEntity} that was deleted
     *     from the system.
     */
    @Operation(
            method = "deleteSongByUserId",
            summary = "Delete an existing Song of a user.",
            description = "This API is used to delete an existing Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                    @SecurityRequirement(
                            name =
                                    ApiDocumentationSettings.ApiSecurityScheme
                                            .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted an existing Song in the system.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/user/songs/{songId}")
    public ResponseEntity<Integer> deleteSongByUserId(@PathVariable(name = "songId") final Integer songId) {
        // Delegate to the service layer.
        final Integer deletedInstance = (songService.deleteSongByUserId(songId));

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }


    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system.
     *
     * @param songId Unique identifier of Song in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Song, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Song}.
     */
    @Operation(
            method = "updateSong",
            summary = "Update an existing Song.",
            description = "This API is used to update an existing Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                    @SecurityRequirement(
                            name =
                                    ApiDocumentationSettings.ApiSecurityScheme
                                            .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated an existing Song in the system.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/user/songs/{songId}")
    public ResponseEntity<Song> updateSongByUserId(
            @PathVariable(name = "songId") final Integer songId,
            @Valid @RequestBody final UpdateSongRequest payload,
            @RequestParam final String url,
            @RequestParam final String mediadescription) throws Exception {
        // Delegate to the service layer.
        final Song updatedInstance = songService.updateSongByUserId(songId, payload, url, mediadescription);
        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }



    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} in the system.
     *
     * @param songId Unique identifier of Song in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Song}.
     */
    @Operation(
            method = "findSongById",
            summary = "Find an existing Song.",
            description = "This API is used to find an existing Song in the system.",
            tags = {SongApi.API_TAG},
            security = {
                    @SecurityRequirement(
                            name =
                                    ApiDocumentationSettings.ApiSecurityScheme
                                            .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description =
                                    "Successfully retrieved the details of an existing Song in the system.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "You do not have permissions to perform this operation.",
                            content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "user/songs/{songId}")
    public ResponseEntity<Song> findSongByUserId(@PathVariable(name = "songId") final Integer songId) {
        // Delegate to the service layer.
        final Song matchingInstance = songService.findSongByUserId(songId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/songs/Singers")
    public ResponseEntity<List<SongEntity>> findSongByUserId() {
        // Delegate to the service layer.
        final List<SongEntity> singers = songService.singers();
        return ResponseEntity.ok(singers);
    }

}
