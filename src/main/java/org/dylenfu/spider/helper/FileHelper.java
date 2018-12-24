package org.dylenfu.spider.helper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import org.apache.commons.io.FileUtils;

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

    public void download(String url, String filepath) throws Exception {
        File file = new File(filepath);
        URL remoteUrl = new URL(url);
        URLConnection conn = remoteUrl.openConnection();
        conn.setRequestProperty("User-agent", "chrome");
        InputStream input = conn.getInputStream();
        FileUtils.copyInputStreamToFile(input, file);
    }
}
