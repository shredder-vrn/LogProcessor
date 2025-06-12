package com.Shredder.logprocessor.parser;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogFileReaderTest {

    private Path testDir;

    @BeforeEach
    void setUp() throws IOException {
        testDir = Files.createTempDirectory("LogFileReaderTest");
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(testDir)) {
            Files.walk(testDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try { Files.delete(path); }
                        catch (IOException e) { /* Ignore */ }
                    });
        }
    }

    @Test
    void shouldListAllLogFiles_WhenDirectoryContainsLogs() throws IOException {
        Path file1 = Files.createFile(testDir.resolve("file1.log"));
        Path file2 = Files.createFile(testDir.resolve("file2.log"));
        Files.createFile(testDir.resolve("not_a_log.txt"));

        List<Path> result = LogFileReader.listLogFiles(testDir);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(file1));
        assertTrue(result.contains(file2));
    }

    @Test
    void shouldThrowException_WhenDirectoryDoesNotExist() {
        Path invalidPath = Paths.get("nonexistent_directory");

        assertThrows(IOException.class, () -> {
            LogFileReader.listLogFiles(invalidPath);
        });
    }

    @Test
    void shouldReturnEmptyList_WhenNoLogFilesExist() throws IOException {
        Files.createFile(testDir.resolve("document.txt"));

        List<Path> result = LogFileReader.listLogFiles(testDir);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}