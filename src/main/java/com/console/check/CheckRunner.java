package com.console.check;

import com.console.check.service.*;
import com.console.check.util.ConnectionManager;
import lombok.SneakyThrows;

public class CheckRunner {
    private static final PrintServiceFile printServiceFile = PrintServiceFile.getInstance();
    private static final PrintServiceDB printServiceDB = PrintServiceDB.getInstance();


    @SneakyThrows
    public static void main(String[] args) {

        try {
            printServiceFile.printFile();
            printServiceDB.printDB();
        } finally {
            ConnectionManager.closePool();
        }

    }
}