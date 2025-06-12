package com.Shredder.logprocessor.parser;

import com.Shredder.logprocessor.model.LogEntry;
import com.Shredder.logprocessor.model.Operation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogParserTest {

    @Test
    void testValidLineIsParsedCorrectly() {
        String inputDir = "test/resources/logs";

        Map<String, List<LogEntry>> result = null;
        try {
            result = LogParser.parseAllFiles(inputDir);
        } catch (IOException e) {
            fail("Произошла ошибка ввода-вывода: " + e.getMessage());
        }

        assertNotNull(result);
        assertTrue(result.containsKey("user001"));

        List<LogEntry> entries = result.get("user001");
        assertFalse(entries.isEmpty());

        LogEntry entry = entries.get(0);
        assertEquals(LocalDateTime.of(2025, 5, 10, 9, 0, 22), entry.getTimestamp());
        assertEquals("user001", entry.getUser());

        Operation op = entry.getOperation();
        assertEquals("balance inquiry 1000.00", op.toString());
    }
}