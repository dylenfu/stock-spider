package org.dylenfu.spider.accessor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class StockAccessor {

    private String url;

    public StockAccessor(String url) {
        this.url = url;
    }

    public void getDoc() throws Exception {
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc);
    }
}
