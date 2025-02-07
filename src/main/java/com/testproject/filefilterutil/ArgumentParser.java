package com.testproject.filefilterutil;

import org.apache.commons.cli.*;

import java.util.*;

class ArgumentParser {
    private boolean appendMode = false;
    private String outputPath = "";
    private String prefix = "";
    private boolean fullStats = false;
    private final List<String> fileNames = new ArrayList<>();

    public ArgumentParser(String[] args) {
        Options options = new Options();
        options.addOption("a", false, "Append mode");
        options.addOption("o", true, "Output path");
        options.addOption("p", true, "File prefix");
        options.addOption("f", false, "Full statistics");
        options.addOption("s", false, "Short statistics");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            appendMode = cmd.hasOption("a");
            fullStats = cmd.hasOption("f");
            if (cmd.hasOption("o")) outputPath = cmd.getOptionValue("o");
            if (cmd.hasOption("p")) prefix = cmd.getOptionValue("p");
            fileNames.addAll(cmd.getArgList());
        } catch (ParseException e) {
            System.err.println("Ошибка разбора аргументов: " + e.getMessage());
            System.exit(1);
        }
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isFullStats() {
        return fullStats;
    }

    public List<String> getFileNames() {
        return fileNames;
    }
}