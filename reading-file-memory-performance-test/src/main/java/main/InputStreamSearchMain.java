package main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import thread.InputStreamSearch;
import thread.MemoryObserver;

public class InputStreamSearchMain {
    static int fileSize = 1000000;
    static int threadCount = 400;

    public static void main(String[] args) throws InterruptedException {
        
        readInputArgs(args);
        
        System.out.println("fileSize: " + fileSize);
        System.out.println("threadCount: " + threadCount);

        String filePath = System.getProperty("java.io.tmpdir") + "test.dat";
        System.out.println("inputFile: " + filePath);

        createFile(filePath, fileSize);

        Thread memoryObserver = new Thread(new MemoryObserver());
        memoryObserver.start();

        ExecutorService executorService = Executors
                .newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount * 5; i++) {
            Thread.sleep(100);
            InputStreamSearch inputStreamSearch = new InputStreamSearch();
            inputStreamSearch.setFilePath(filePath);
            executorService.execute(inputStreamSearch);
        }

        executorService.shutdown();
        while (!memoryObserver.isInterrupted()) {
            if (executorService.isTerminated()) {
                System.out.println("ExecuterService is terminated.");
                memoryObserver.interrupt();
            }
        }
    }

    private static void readInputArgs(String[] args) {
        if (args.length >= 1) {
            try {
                fileSize = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("args[0] is fileSize. should be integer.");
            }
        }

        if (args.length >= 2) {
            try {
                threadCount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out
                        .println("args[1] is threadCount. should be integer.");
            }
        }
    }

    private static void createFile(String filePath, int fileSize) {
        File outputFile = new File(filePath);
        BufferedOutputStream outputStream = null;
        try {

            outputStream = new BufferedOutputStream(new FileOutputStream(
                    outputFile));

            for (int i = 0; i < fileSize; i++) {
                outputStream.write("a".getBytes()[0]);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
