package com.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    private LogUtil() {

    }

    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void logInfo(String message) {
        logger.info(message);
    }
}
