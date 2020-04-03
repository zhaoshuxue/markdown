package com.zsx.md.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static void checkFileExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void writeFile(String text, String filePath) {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String filePath) {
        StringBuilder result = new StringBuilder();
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        Path path = Paths.get(filePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String text = null;
            List<String> list = new ArrayList<>();
            while ((text = reader.readLine()) != null) {
                list.add(text);
//                result.append(text);
//                result.append(System.getProperty("line.separator"));
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                result.append(list.get(i));
                if (i < size - 1) {
                    result.append(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
