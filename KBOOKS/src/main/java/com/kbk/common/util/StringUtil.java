package com.kbk.common.util;

public class StringUtil {
	
    public static String nvl(String str) {
        return StringUtil.nvl(str, "");
    }

    public static String nvl(String str, String NVLString) {
        if (str == null) {
            return NVLString;
        }
        return str;
    }

    public static String getSuffix(String input) {
        if (input != null) {
            return StringUtil.getSuffix(input, ".");
        }
        return null;
    }

    public static String getSuffix(String input, String delim) {
        if (input != null && delim != null) {
            return input.substring(input.lastIndexOf(delim) + 1);
        }
        return null;
    }
}
