package com.Shredder.logprocessor.writer;

import com.Shredder.logprocessor.model.LogEntry;
import com.Shredder.logprocessor.util.BalanceCalculator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileWriter {

    public static void writeUserLogs(Map<String, List<LogEntry>> userLogs, String outputDir) throws IOException {
        Files.createDirectories(Paths.get(outputDir));

        for (Map.Entry<String, List<LogEntry>> entry : userLogs.entrySet()) {
            writeUserLogFile(entry.getKey(), entry.getValue(), outputDir);
        }
    }

    private static void writeUserLogFile(String user, List<LogEntry> entries, String outputDir) throws IOException {
        entries.sort(Comparator.comparing(LogEntry::getTimestamp));

        Path outFile = Paths.get(outputDir, user + ".log");
        try (BufferedWriter writer = Files.newBufferedWriter(outFile)) {
            for (LogEntry entry : entries) {
                writer.write(entry.toString());
                writer.newLine();
            }

            double balance = BalanceCalculator.calculateFinalBalance(entries);
            String finalLine = String.format("[%s] %s final balance %.2f",
                    LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    user, balance);
            writer.write(finalLine);
        }
    }
}