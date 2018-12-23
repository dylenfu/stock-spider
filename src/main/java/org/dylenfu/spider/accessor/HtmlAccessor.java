package org.dylenfu.spider.accessor;

import org.dylenfu.spider.helper.FileHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// TODO(FUK): search engine matching "stock code & key words"

public class HtmlAccessor extends Accessor {

    public Document getDocFromUrl(String url) throws Exception {
        String filename = urlToFileName(url);
        String filepath = dirPath + filename;

        FileHelper fileHelper = new FileHelper();
        if (!fileHelper.exist(filepath)) {
            Document doc = Jsoup.connect(url).get();
            String html = doc.toString();
            fileHelper.write(filepath, html);
        }

        // data from wget is different with content of cache file
        String content = fileHelper.read(filepath);
        return Jsoup.parse(content);
    }

    public String urlToFileName(String url) {
        String[] data = url.split("/");
        if (data.length < 4) throw new RuntimeException("url invalid");

        String stockcode = data[3];
        String prefix = "stock_";
        String suffix = "_brief.html";

        return prefix + stockcode + suffix;
    }
}
