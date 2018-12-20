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
        String baseUrl = config.getString("base_url");
        List<String> stockCodeList = config.getStringList("stock_codes");
        String dir = config.getString("cache_dir");

        StockAccessor accessor = new StockAccessor();
        accessor.setCacheDir(dir);

        Converter converter = new StockConverter();

        try {
            Iterator<String> iterator = stockCodeList.iterator();
            while(iterator.hasNext()) {
                String briefInfoUrl = baseUrl + iterator.next() + "/";
                String financialUrl = briefInfoUrl + "finance/";
                Stock stock = new Stock();

                Document docBriefInfo = accessor.getDocFromUrl(briefInfoUrl);
                converter.convertBaseInfo(docBriefInfo, stock);

                Document docFinancial = accessor.getDocFromUrl(financialUrl);
                converter.convertFinancial(docFinancial, stock);

                System.out.println(stock);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        StockImporter importer = new StockImporter();
//        importer.createWorkBook();
    }
}
