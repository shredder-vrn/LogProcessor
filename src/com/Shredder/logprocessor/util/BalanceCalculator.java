package com.Shredder.logprocessor.util;

import com.Shredder.logprocessor.model.LogEntry;
import com.Shredder.logprocessor.model.Operation;

import java.util.List;

public class BalanceCalculator {

    public static double calculateFinalBalance(List<LogEntry> entries) {
        double balance = 0;
        for (LogEntry entry : entries) {
            Operation op = entry.getOperation();
            switch (op.getType()) {
                case BALANCE_INQUIRY:
                    balance = op.getAmount();
                    break;
                case TRANSFERRED:
                case WITHDREW:
                    balance -= op.getAmount();
                    break;
                case RECEIVED:
                    balance += op.getAmount();
                    break;
                default:
                    break;
            }
        }
        return balance;
    }
}