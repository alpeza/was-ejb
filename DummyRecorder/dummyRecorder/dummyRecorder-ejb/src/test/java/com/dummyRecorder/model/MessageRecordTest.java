package com.dummyRecorder.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageRecordTest {

    @Test
    public void testSerializeMessage() {
        String jsonString = "{\"id\": 123, \"message\": \"Test message\"}";
        MessageRecord messageRecord = new MessageRecord();
        String serializedMessage = messageRecord.serializeMessage(jsonString);
        System.out.println(serializedMessage);
    }
}