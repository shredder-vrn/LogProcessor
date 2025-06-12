package com.Shredder.logprocessor;

import com.Shredder.logprocessor.model.LogEntry;
import com.Shredder.logprocessor.parser.LogParser;
import com.Shredder.logprocessor.writer.ReportWriter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TransactionProcessor {

    private final LogParser logParser = new LogParser();
    private final ReportWriter reportWriter = new ReportWriter();

    public Map<String, List<LogEntry>> parseAllFiles(String inputDir) throws IOException {
        return logParser.parseAllFiles(inputDir);
    }

    public void writeUserLogs(Map<String, List<LogEntry>> userLogs, String outputDir) throws IOException {
        reportWriter.writeReports(userLogs, outputDir);
    }
}