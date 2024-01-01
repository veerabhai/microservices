/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

/**
 * Utility class that provides helper methods to extract details from HTTP request objects.
 *
 * @author Editor
 */
public final class HttpUtils {
    /** Unknown ip address. */
    private static final String UNKNOWN = "unknown";

    /** Order of headers to be looked into for extracting the IP address. */
    private static final String[] IP_ADDRESS_LOOKUP_HEADERS = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

    /** Value template for Content-Disposition header. */
    private static final String CONTENT_DISPOSITION_HEADER_VALUE = "attachment; filename={0}";

    /**
     * Private constructor.
     */
    private HttpUtils() {
        throw new IllegalStateException("Cannot create instances of this class");
    }

    /**
     * This method attempts to return the ip address of the client by looking for specific headers in the provided
     * request.
     *
     * @param request
     *         Http request of type {@link HttpServletRequest}.
     *
     * @return IP address of the caller.
     */
    public static String getClientIpAddress(final HttpServletRequest request) {
        String ipAddress = StringUtils.EMPTY;

        if (Objects.isNull(request)) {
            return ipAddress;
        }

        for (final String header : HttpUtils.IP_ADDRESS_LOOKUP_HEADERS) {
            ipAddress = request.getHeader(header);
            if (StringUtils.isNotBlank(ipAddress) && !ipAddress.equalsIgnoreCase(HttpUtils.UNKNOWN)) {
                break;
            }
        }
        return StringUtils.isBlank(ipAddress)
                ? request.getRemoteAddr()
                : ipAddress;
    }

    /**
     * This method creates an instance of type {@link HttpHeaders} with basic initializations and returns it.
     *
     * @return Instance of type {@link HttpHeaders}.
     */
    public static HttpHeaders generateHeadersForFileDownload(final String fileName) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        if (StringUtils.isNotBlank(fileName)) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, MessageFormat.format(CONTENT_DISPOSITION_HEADER_VALUE, fileName));
        }

        return headers;
    }
}