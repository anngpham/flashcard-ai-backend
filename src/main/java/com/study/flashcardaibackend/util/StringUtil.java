package com.study.flashcardaibackend.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}
