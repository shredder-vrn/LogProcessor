package com.Shredder.logprocessor;

import com.Shredder.logprocessor.model.LogEntry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            String inputDir = args.length > 0 ? args[0] : "input_logs";
            String outputDir = args.length > 1 ? args[1] : "transactions_by_users";

            TransactionProcessor processor = new TransactionProcessor();
            Map<String, List<LogEntry>> userLogs = processor.parseAllFiles(inputDir);
            processor.writeUserLogs(userLogs, outputDir);

            System.out.println("Обработка завершена. Результат сохранён в директорию: " + outputDir);

        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}