package com.study.flashcardaibackend.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class StringUtil {

    /**
     * Joins a list of strings with a delimiter
     * Eg: join(["a", "b", "c"], ",") -> "a,b,c"
     *
     * @param strList   list of strings
     * @param delimiter the delimiter to join the strings with
     * @return joined string
     */
    public static String join(List<String> strList, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strList.size(); i++) {
            sb.append(strList.get(i));
            if (i != strList.size() - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    /**
     * Builds a statement from a key-value pair
     * Eg: {title: "abc", description: "xyz"} -> "title = abc, description = xyz"
     *
     * @param kv key-value pair
     * @return statement
     */
    public static String buildKVStatement(HashMap<String, String> kv, String operator) {
        if (kv.isEmpty()) return null;
        List<String> items = new ArrayList<>();
        for (String key : kv.keySet()) {
            List<String> stringList = List.of(key, operator, kv.get(key));
            items.add(join(stringList, " "));
        }
        return join(items, ", ");
    }

    /**
     * Extracts a string between a prefix and a suffix
     * If the prefix is not found, return null
     * If suffix is not found, return the string from the prefix to the end
     * If suffix is found before prefix, return null
     * If prefix is found before suffix, return the string between prefix and suffix
     *
     * @param str    string to extract from
     * @param prefix prefix
     * @param suffix suffix
     * @return extracted string
     */
    public static String extractString(String str, String prefix, String suffix) {
        if (str == null || prefix == null || !str.contains(prefix)) return null;
        int prefixIndex = str.indexOf(prefix);
        int suffixIndex;
        if (suffix == null || !str.contains(suffix)) {
            suffixIndex = str.length();
        } else {
            suffixIndex = str.indexOf(suffix);
        }
        return str.substring(prefixIndex + prefix.length(), suffixIndex);
    }

    /**
     * Tries to parse a string to a UUID
     * Eg: tryParseUUID("123e4567-e89b-12d3-a456-426614174000") -> UUID
     *
     * @param str string to parse
     * @return UUID if successful, null otherwise
     */
    public static UUID tryParseUUID(String str) {
        try {
            return UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
