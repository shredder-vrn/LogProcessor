package com.Shredder.logprocessor.parser;

import com.Shredder.logprocessor.model.LogEntry;
import com.Shredder.logprocessor.model.Operation;
import com.Shredder.logprocessor.model.OperationType;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.*;

public class LogParser {

    private static final Pattern LOG_PATTERN = Pattern.compile("\\[(.+?)\\] (.+?) (.+)");

    public static Map<String, List<LogEntry>> parseAllFiles(String inputDir) throws IOException {
        Map<String, List<LogEntry>> userLogs = new HashMap<>();

        List<Path> logFiles = LogFileReader.listLogFiles(Paths.get(inputDir));

        for (Path file : logFiles) {
            processFile(file, userLogs);
        }

        return userLogs;
    }

    private static void processFile(Path file, Map<String, List<LogEntry>> userLogs) throws IOException {
        List<String> lines = Files.readAllLines(file);

        for (String line : lines) {
            Matcher matcher = LOG_PATTERN.matcher(line);
            if (matcher.find()) {
                String timestampStr = matcher.group(1);
                String user = matcher.group(2);
                String operationLine = matcher.group(3);

                LocalDateTime timestamp = LogEntry.parseTimestamp(timestampStr);
                Operation operation = Operation.parse(operationLine);

                LogEntry entry = new LogEntry(timestamp, user, operation);
                userLogs.computeIfAbsent(user, k -> new ArrayList<>()).add(entry);

                if (operation.getType() == OperationType.TRANSFERRED) {
                    String toUser = operation.getTo();
                    LogEntry receivedEntry = new LogEntry(
                            timestamp,
                            toUser,
                            new Operation(OperationType.RECEIVED, operation.getAmount(), user)
                    );
                    userLogs.computeIfAbsent(toUser, k -> new ArrayList<>()).add(receivedEntry);
                }
            }
        }
    }
}