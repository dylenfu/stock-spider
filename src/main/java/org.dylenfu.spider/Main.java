package org.dylenfu.spider;

import org.dylenfu.spider.accessor.StockAccessor;
import org.dylenfu.spider.importer.StockImporter;

public class Main {

    public static void main(String[] args) {

//        StockAccessor accessor = new StockAccessor("http://stockpage.10jqka.com.cn/002648/");
//
//        try {
//            accessor.getDoc();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        StockImporter importer = new StockImporter();
        importer.createWorkBook();
    }
}
