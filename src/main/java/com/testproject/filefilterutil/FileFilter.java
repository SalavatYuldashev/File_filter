package com.testproject.filefilterutil;

public class FileFilter {
    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser(args);
        FileProcessor fileProcessor = new FileProcessor();

        fileProcessor.processFiles(argumentParser.getFileNames());
        fileProcessor.writeOutputFiles(argumentParser.getOutputPath(), argumentParser.getPrefix(), argumentParser.isAppendMode());
        fileProcessor.printStatistics(argumentParser.isFullStats());
    }
}

