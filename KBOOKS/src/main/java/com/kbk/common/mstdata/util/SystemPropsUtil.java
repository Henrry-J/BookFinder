package com.kbk.common.mstdata.util;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemPropsUtil {
	
    private static Properties envProperties;
    @Autowired
    private Properties evnProps;

    @PostConstruct
    public void init() {
        envProperties = this.evnProps;
    }

    public static String getEnvironProperty(String propId) {
        String prop = null;
        if (propId != null && !propId.trim().isEmpty()) {
            prop = envProperties.getProperty(propId);
            if (prop != null) {
                return prop;
            }
            return "";
        }
        return "";
    }

    public static String getHttpSessionTimeout() {
        return SystemPropsUtil.getEnvironProperty("HTTP_SESSION_TIME_OUT");
    }
}
