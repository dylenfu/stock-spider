package org.dylenfu.spider.accessor;

import org.dylenfu.spider.helper.FileHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class StockAccessor {

    private String dirPath;

    public void setCacheDir(String path) {
        this.dirPath = path;
    }

    public Document getDocFromUrl(String url) throws Exception {
        String filename = urlToFileName(url);
        String filepath = dirPath + filename;

        FileHelper fileHelper = new FileHelper();
        if (fileHelper.exist(filepath)) {
            String content = fileHelper.read(filepath);
            return Jsoup.parse(content);
        } else {
            Document doc = Jsoup.connect(url).get();
            String html = doc.toString();
            fileHelper.write(filepath, html);
            return doc;
        }
    }

    private String urlToFileName(String url) {
        String[] data = url.split("/");
        assert (data.length >= 3):"url invalid";
        return data[3];
    }
}
