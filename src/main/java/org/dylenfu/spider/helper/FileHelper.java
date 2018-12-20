package org.dylenfu.spider.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHelper {

    public void write(String filepath, String data) {
        try {
            Path path = Paths.get(filepath);
            Files.write(path, data.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(String filepath) {
        String content = "";
        try {
            Path path = Paths.get(filepath);
            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public boolean exist(String filepath) {
        Path path = Paths.get(filepath);
        return Files.exists(path);
    }

}
