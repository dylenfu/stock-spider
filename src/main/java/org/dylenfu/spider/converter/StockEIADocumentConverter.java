package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StockEIADocumentConverter implements Converter<Document, Stock> {

    public void convert(Document doc, Stock stock) {
        List<String> list = getSearchEIA(doc);
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()) {
            String news = iterator.next();
            stock.addNews(news);
        }
    }

    private List<String> getSearchEIA(Document doc) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
