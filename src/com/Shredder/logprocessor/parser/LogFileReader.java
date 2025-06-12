package com.Shredder.logprocessor.parser;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class LogFileReader {

    public static List<Path> listLogFiles(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            throw new IOException("Директория не найдена: " + dir);
        }

        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.log")) {
            for (Path path : stream) {
                files.add(path);
            }
        }
        return files;
    }
}