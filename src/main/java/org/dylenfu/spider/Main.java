package org.dylenfu.spider;

import org.dylenfu.spider.accessor.*;
import org.dylenfu.spider.data.*;
import org.dylenfu.spider.converter.*;
import org.jsoup.nodes.Document;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        List<String> urls = config.getStringList("urls");
        String dir = config.getString("cache");

        StockAccessor accessor = new StockAccessor();
        accessor.setCacheDir(dir);

        Converter converter = new StockConverter();

        try {
            Iterator<String> iterator = urls.iterator();
            while(iterator.hasNext()) {
                String url = iterator.next();
                Document doc = accessor.getDocFromUrl(url);
                Stock stock = new Stock();
                converter.convert(doc, stock);
                System.out.println(stock);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        StockImporter importer = new StockImporter();
//        importer.createWorkBook();
    }
}
