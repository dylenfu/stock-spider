package org.dylenfu.spider;

import org.dylenfu.spider.accessor.*;
import org.dylenfu.spider.data.*;
import org.dylenfu.spider.converter.*;
import org.jsoup.nodes.Document;

public class Main {

    public static void main(String[] args) {

        String dir = "/Users/dylen/workspace/javahome/github.com/dylenfu/stock-spider/data/";
        String url = "http://stockpage.10jqka.com.cn/002648/";

        StockAccessor accessor = new StockAccessor();
        accessor.setCacheDir(dir);

        Converter converter = new StockConverter();

        try {
            Document doc = accessor.getDocFromUrl(url);
            Stock stock = new Stock();
            converter.convert(doc, stock);

            System.out.println(stock);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        StockImporter importer = new StockImporter();
//        importer.createWorkBook();
    }
}
