package com.dummyRecorder.utils;

import org.junit.jupiter.api.Test;

public class AppendStringToFileTest {
    private static final String TEST_FILE = "test.csv";
    private static final String TEST_CONTENT = "20231206-095041;123;Test message";

    @Test
    public void testAppendToFile() {
        AppendStringToFile.appendToFile(TEST_FILE, TEST_CONTENT);
    }

}
