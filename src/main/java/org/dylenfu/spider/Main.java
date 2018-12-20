package org.dylenfu.spider;

import org.dylenfu.spider.accessor.*;
import org.dylenfu.spider.data.*;
import org.dylenfu.spider.converter.*;
import org.jsoup.nodes.Document;

public class Main {

    public static void main(String[] args) {

        StockAccessor accessor = new StockAccessor();
        String url = "http://stockpage.10jqka.com.cn/002648/";

        try {
            Document doc = accessor.getDocFromUrl(url);
            Stock stock = new Stock();
            Converter converter = new StockConverter();
            converter.convert(doc, stock);

            System.out.println(stock);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        StockImporter importer = new StockImporter();
//        importer.createWorkBook();
    }
}
