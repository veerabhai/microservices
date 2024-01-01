/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.data.jpa.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps a {@link PageImpl} and enables the usage of paginated data in {@code RestTemplate} api invocations.
 *
 * @author Admin
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponsePage<T> extends PageImpl<T> {
    // Credits:
    // https://stackoverflow.com/questions/34647303/spring-resttemplate-with-paginated-api/46847429#46847429

    /**
     * Constructor.
     *
     * @param content
     *         Content of the page.
     * @param number
     *         Number of elements in the current page.
     * @param size
     *         Page size.
     * @param totalElements
     *         Total number of elements.
     * @param pageable
     *         Page request object containing the current page settings.
     * @param last
     *         Does this represent the last page.
     * @param totalPages
     *         Total number of pages.
     * @param sort
     *         Sort criteria.
     * @param first
     *         Does this represent the first page.
     * @param numberOfElements
     *         Total number of elements in the page.
     * @param empty
     *         Does this represent an empty page.
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestResponsePage(@JsonProperty("content") List<T> content, @JsonProperty("number") int number,
                            @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements,
                            @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
                            @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort,
                            @JsonProperty("first") boolean first,
                            @JsonProperty("numberOfElements") int numberOfElements,
                            @JsonProperty("empty") boolean empty) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    /**
     * Constructor.
     *
     * @param content
     *         Content of the page.
     * @param pageable
     *         Page request object containing the current page settings.
     * @param total
     *         Total number of elements.
     */
    public RestResponsePage(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
    }

    /**
     * Constructor.
     *
     * @param content
     *         Content of the page.
     */
    public RestResponsePage(final List<T> content) {
        super(content);
    }

    /**
     * Constructor.
     */
    public RestResponsePage() {
        super(new ArrayList<>());
    }
}