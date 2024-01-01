/*
 * Copyright (c) 2020 Innominds Software Pvt. Ltd. All rights reserved.
 *
 * This file is part of iSymphony Platform.
 *
 * iSymphony Platform and associated code cannot be copied and/or
 * distributed without a written permission of Innominds Software Pvt. Ltd.,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons.utils;

import com.google.common.base.CaseFormat;
import com.mbk.app.commons.SpecialCharacter;
import org.apache.commons.lang3.StringUtils;

/**
 * An implementation that provides the functionality for converting text from one format to another.
 * <p>
 * For example: converting from camel-case to pascal / upper-camel case, etc.
 *
 * @author Admin
 */
public final class CaseFormatUtils {
    /**
     * Private constructor.
     */
    private CaseFormatUtils() {
        throw new IllegalStateException("Cannot create instances of this class");
    }

    /**
     * This method converts the provided input string from a snake-case (lower underscore) to upper-camel-case.
     *
     * @param input
     *         Input string that needs to be converted to upper-camel case.
     *
     * @return Input string that is converted to upper-camel-case.
     */
    public static String snakeCaseToUpperCamelCase(final String input) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, input.toLowerCase());
    }

    /**
     * This method converts the provided input string from a snake-case (lower underscore) to lower-camel-case (i.e.
     * regular camel-case and used for field names).
     *
     * @param input
     *         Input string that needs to be converted to lower-camel case.
     *
     * @return Input string that is converted to lower-camel-case.
     */
    public static String snakeCaseToLowerCamelCase(final String input) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, input.toLowerCase());
    }

    /**
     * This method converts the provided input string from a snake-case (lower underscore) to lower-kebab-case (used for
     * urls).
     *
     * @param input
     *         Input string that needs to be converted to lower kebab case.
     *
     * @return Input string that is converted to lower-kebab-case.
     */
    public static String snakeCaseToLowerKebabCase(final String input) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, input.toLowerCase());
    }

    /**
     * This method converts the provided input string from a lower-camel-case to upper-camel-case.
     *
     * @param input
     *         Input string that needs to be converted to upper-camel case.
     *
     * @return Input string that is converted to upper-camel-case.
     */
    public static String lowerCamelCaseToUpperCamelCase(final String input) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, input);
    }

    /**
     * This method converts the provided input string to camel case.
     *
     * @param input
     *         Input string that needs to be converted to camel case.
     *
     * @return Input string that is converted to camel-case.
     */
    public static String upperCamelCaseToLowerCamelCase(final String input) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, input);
    }

    /**
     * This method converts the provided input string to snake-case.
     *
     * @param input
     *         Input string that needs to be converted to snake-case.
     *
     * @return Input string that is converted to snake-case.
     */
    public static String upperCamelCaseToLowerSnakeCase(final String input) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
    }

    /**
     * This method converts the provided input string to snake-case.
     *
     * @param input
     *         Input string that needs to be converted to snake-case.
     *
     * @return Input string that is converted to snake-case.
     */
    public static String lowerCamelCaseToLowerSnakeCase(final String input) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
    }

    /**
     * This method converts the provided input string to lower kebab-case.
     *
     * @param input
     *         Input string that needs to be converted to lower kebab-case.
     *
     * @return Input string that is converted to lower kebab-case.
     */
    public static String upperCamelCaseToLowerKebabCase(final String input) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, input);
    }

    /**
     * This method converts the provided input string from a kebab-case to upper-camel-case.
     *
     * @param input
     *         Input string that needs to be converted to upper-camel case.
     *
     * @return Input string that is converted to upper-camel-case.
     */
    public static String kebabCaseToUpperCamelCase(final String input) {
        return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, input.toLowerCase());
    }

    /**
     * This method converts the provided input string from a kebab-case to lower-camel-case.
     *
     * @param input
     *         Input string that needs to be converted to lower-camel case.
     *
     * @return Input string that is converted to lower-camel-case.
     */
    public static String kebabCaseToLowerCamelCase(final String input) {
        return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, input.toLowerCase());
    }

    /**
     * For the provided input, this method attempts to convert to an upper camel case naming convention, which is
     * generally used for Class / Interface names.
     *
     * @param input
     *         Input that needs to be converted to upper camel case.
     *
     * @return Input string converted to upper camel case.
     */
    public static String toUpperCamelCase(final String input) {
        if (StringUtils.isBlank(input)) {
            return StringUtils.EMPTY;
        }

        String upperCamelCaseInput = input;
        if (input.contains(SpecialCharacter.DASH.getValue())) {
            upperCamelCaseInput = CaseFormatUtils.kebabCaseToUpperCamelCase(input);
        } else if (input.contains(SpecialCharacter.UNDERSCORE.getValue())) {
            upperCamelCaseInput = CaseFormatUtils.snakeCaseToUpperCamelCase(input);
        } else if (Character.isLowerCase(input.charAt(0))) {
            upperCamelCaseInput = CaseFormatUtils.lowerCamelCaseToUpperCamelCase(input);
        }

        return upperCamelCaseInput;
    }

    /**
     * For the provided input, this method attempts to convert to a lower camel case naming convention, which is
     * generally used for field names.
     *
     * @param input
     *         Input that needs to be converted to lower camel case.
     *
     * @return Input string converted to lower camel case.
     */
    public static String toLowerCamelCase(final String input) {
        if (StringUtils.isBlank(input)) {
            return StringUtils.EMPTY;
        }

        String lowerCamelCaseInput = input;
        if (input.contains(SpecialCharacter.DASH.getValue())) {
            lowerCamelCaseInput = CaseFormatUtils.kebabCaseToLowerCamelCase(input);
        } else if (input.contains(SpecialCharacter.UNDERSCORE.getValue())) {
            lowerCamelCaseInput = CaseFormatUtils.snakeCaseToLowerCamelCase(input);
        } else if (Character.isUpperCase(input.charAt(0))) {
            lowerCamelCaseInput = CaseFormatUtils.upperCamelCaseToLowerCamelCase(input);
        }

        return lowerCamelCaseInput;
    }

    /**
     * For the provided input, this method attempts to convert to a lower kebab case naming convention, which is
     * generally used for urls.
     *
     * @param input
     *         Input that needs to be converted to lower kebab case.
     *
     * @return Input string converted to lower kebab case.
     */
    public static String toLowerKebabCase(final String input) {
        if (StringUtils.isBlank(input)) {
            return StringUtils.EMPTY;
        }

        String lowerKebabCaseInput = input;
        if (input.contains(SpecialCharacter.UNDERSCORE.getValue())) {
            lowerKebabCaseInput = CaseFormatUtils.snakeCaseToLowerKebabCase(input);
        } else if (Character.isUpperCase(input.charAt(0))) {
            lowerKebabCaseInput = CaseFormatUtils.upperCamelCaseToLowerKebabCase(input);
        }

        return lowerKebabCaseInput.replace(SpecialCharacter.SPACE.getValue(), SpecialCharacter.DASH.getValue()).toLowerCase();
    }
}
