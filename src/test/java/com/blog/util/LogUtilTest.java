package com.blog.util;

import org.junit.jupiter.api.Test;

class LogUtilTest {

    @Test
    void testLogError() {
        LogUtil.logError("message", new Exception("message"));
    }

    @Test
    void testLogInfo() {
        LogUtil.logInfo("message");
    }
}
