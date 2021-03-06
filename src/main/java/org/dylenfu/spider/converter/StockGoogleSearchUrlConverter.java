package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class StockGoogleSearchUrlConverter implements Converter<Document, Stock> {

    public void convert(Document doc, Stock stock) {
        List<String> list = getUrl(doc);
        for(String url: list) {
            stock.addNews(url);
        }
    }

    private List<String> getUrl(Document doc) {
        List<String> list = new ArrayList<>();
        Element searchRes = doc.getElementById("search");
        if (searchRes == null) {
            return list;
        }
        Elements results = searchRes.getElementsByClass("g");
        if (results.isEmpty()) {
            return list;
        }

        for(Element res: results) {
            Elements rc = res.getElementsByClass("rc");
            if (rc.isEmpty()) {
                continue;
            }
            Elements link = rc.select("div.r > a");
            if (link.isEmpty()) {
                continue;
            }
            String url = link.attr("href");
            list.add(url);
        }

        return list;
    }
}
