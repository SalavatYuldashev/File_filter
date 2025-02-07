package com.testproject.filefilterutil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.Collections;

class FileProcessor {
    private final List<Long> integers = new ArrayList<>();
    private final List<Double> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    public void processFiles(List<String> fileNames) {
        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    classifyData(line.trim());
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла: " + fileName);
            }
        }
    }

    private void classifyData(String line) {
        if (line.isEmpty()) return;
        try {
            if (line.matches("[-+]?\\d+")) {
                integers.add(Long.parseLong(line));
            } else if (line.matches("[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?")) {
                floats.add(Double.parseDouble(line));
            } else {
                strings.add(line);
            }
        } catch (NumberFormatException e) {
            strings.add(line);
        }
    }

    public void writeOutputFiles(String outputPath, String prefix, boolean appendMode) {
        writeToFile(outputPath, prefix + "integers.txt", integers.stream().map(String::valueOf).collect(Collectors.toList()), appendMode);
        writeToFile(outputPath, prefix + "floats.txt", floats.stream().map(String::valueOf).collect(Collectors.toList()), appendMode);
        writeToFile(outputPath, prefix + "strings.txt", strings, appendMode);
    }

    private void writeToFile(String outputPath, String fileName, List<String> data, boolean appendMode) {
        if (data.isEmpty()) return;
        Path path = outputPath.isEmpty() ? Path.of(fileName) : Path.of(outputPath, fileName);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent()); // Создаем директорию, если она задана
            }
            Files.write(path, data, appendMode ? new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND} : new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + path + " - " + e.getMessage());
        }
    }

    public void printStatistics(boolean fullStats) {
        System.out.println("Статистика:");
        System.out.println("Целые числа: " + integers.size());
        System.out.println("Вещественные числа: " + floats.size());
        System.out.println("Строки: " + strings.size());

        if (fullStats) {
            System.out.println("Дополнительная статистика:");
            if (!integers.isEmpty()) {
                System.out.println("  Min: " + Collections.min(integers) + ", Max: " + Collections.max(integers));
            }
            if (!floats.isEmpty()) {
                System.out.println("  Min: " + Collections.min(floats) + ", Max: " + Collections.max(floats));
            }
            if (!strings.isEmpty()) {
                System.out.println("  Min Length: " + strings.stream().mapToInt(String::length).min().orElse(0) + ", Max Length: " + strings.stream().mapToInt(String::length).max().orElse(0));
            }
        }
    }
}

