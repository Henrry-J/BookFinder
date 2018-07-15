package com.kbk.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameworkConst {
    public static final String SYSTEM = "SYSTEM";
    public static final String LOGIN_USER = "LOGIN_USER";
    public static final int HTTPSESSION_TIME_OUT = 30;
    public static final String HTTP_SESSION_TIME_OUT = "HTTP_SESSION_TIME_OUT";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String FILE_PATH_SEPARATOR = "/";
    public static final String POPUP_COOKIE_PREFIX = "popupdisable";
    public static final Map<String, String> HTTP_DENY_METHOD = new HashMap<String, String>();
    public static final Map<String, String> VALID_REQUEST_URL_SUFFIX = new HashMap<String, String>();
    public static final Map<String, String> GET_REQUEST_URL = new HashMap<String, String>();
    public static final Map<String, String> REQUIRED_AUTH_URL = new HashMap<String, String>();
    public static final Map<String, String> RE_PASSWORD_URL = new HashMap<String, String>();
    public static final List<String> INVALID_FILE_PATH = new ArrayList<String>();
    public static final String REQUEST_MENU = "book";
    public static final String REQUEST_ACTION = "action";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_DELETE = "DELETE";
    public static final String REQUEST_METHOD_PUT = "PUT";
    public static final String REQUEST_METHOD_OPTIONS = "OPTIONS";
    public static final String REQUEST_METHOD_TRACE = "TRACE";
    public static final String REQUEST_METHOD_PATCH = "PATCH";
    public static final String STR_COLON = ":";
    public static final String STR_SEPERATOR = "^|";
    public static final String STR_SPLIT_DELIM = "\\^\\|";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String ON = "ON";
    public static final String OFF = "OFF";
    public static final String PASSWORD_RSKEY = "RSKEY";
    public static final String PASSWORD_RSNAME = "RSNAME";
    public static final String RESULT_CODE = "RESULT_CODE";
    public static final String RESULT_MSG = "RESULT_MSG";
    public static final String RESULT_DET_MSG = "RESULT_DET_MSG";
    public static final String RESULT_AFTER_URL = "RESULT_AFTER_URL";
    public static final String RESULT_SUCCESS = "SUC";
    public static final String RESULT_WARNING = "WARN";
    public static final String RESULT_ERR_BIZ = "EB";
    public static final String RESULT_ERR_SYS = "ES";

    static {
        HTTP_DENY_METHOD.put("DELETE", "Y");
        HTTP_DENY_METHOD.put("PUT", "Y");
        HTTP_DENY_METHOD.put("OPTIONS", "Y");
        HTTP_DENY_METHOD.put("TRACE", "Y");
        HTTP_DENY_METHOD.put("PATCH", "Y");
        VALID_REQUEST_URL_SUFFIX.put("book", "Y");
        VALID_REQUEST_URL_SUFFIX.put("action", "Y");
        INVALID_FILE_PATH.add("..");
        INVALID_FILE_PATH.add("../");
    }
}
