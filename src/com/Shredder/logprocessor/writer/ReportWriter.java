package com.Shredder.logprocessor.writer;

import com.Shredder.logprocessor.model.LogEntry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportWriter {

    public void writeReports(Map<String, List<LogEntry>> userLogs, String outputDir) throws IOException {
        FileWriter.writeUserLogs(userLogs, outputDir);
    }
}