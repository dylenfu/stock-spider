package org.dylenfu.spider.accessor;

import org.dylenfu.spider.helper.FileHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// TODO(FUK): search engine matching "stock code & key words"

public class HtmlAccessor extends AbstractAccessor implements Accessor<Document> {

    public HtmlAccessor(String dirPath) {
        setCacheDir(dirPath);
    }

    public Document getFileFromUrl(String url) {
        String filename = urlToFileName(url);
        String filepath = getDirPath() + filename;
        FileHelper fileHelper = new FileHelper();

        try {
            if (!fileHelper.exist(filepath)) {
                Document doc = Jsoup.connect(url).get();
                String html = doc.toString();
                fileHelper.write(filepath, html);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // data from wget is different with content of cache file
        String content = fileHelper.read(filepath);
        return Jsoup.parse(content);
    }

    public String urlToFileName(String url) {
        String[] data = url.split("/");
        if (data.length < 4) throw new RuntimeException("url for html invalid");

        String stockcode = data[3];
        String prefix = "stock_";
        String suffix;

        if (data.length > 4) {
            suffix = "_"  + data[4] + ".html";
        } else {
            suffix = "_brief.html";
        }

        return prefix + stockcode + suffix;
    }
}
