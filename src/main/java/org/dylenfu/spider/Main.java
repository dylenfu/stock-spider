package org.dylenfu.spider;

import org.apache.poi.ss.usermodel.Workbook;
import org.dylenfu.spider.accessor.*;
import org.dylenfu.spider.data.*;
import org.dylenfu.spider.converter.*;
import org.dylenfu.spider.excel.ExcelReader;
import org.jsoup.nodes.Document;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        String corporationBaseUrl = config.getString("base_url");
        String financialBaseUrl = config.getString("financial_url");
        List<String> stockCodeList = config.getStringList("stock_codes");
        String briefDir = config.getString("brief_cache_dir");
        String forecastDir = config.getString("forecast_cache_dir");
        String financialDir = config.getString("financial_cache_dir");

        HtmlAccessor briefAccessor = new HtmlAccessor();
        briefAccessor.setCacheDir(briefDir);
        HtmlAccessor forecastAccessor = new HtmlAccessor();
        forecastAccessor.setCacheDir(forecastDir);
        ExcelAccessor financialAccessor = new ExcelAccessor();
        financialAccessor.setCacheDir(financialDir);

        Converter briefConverter = new StockBriefDocumentConverter();
        Converter financialConverter = new StockFinancialXlsConverter();
        Converter forecastConverter = new StockForecastDocumentConverter();
        try {
            Iterator<String> iterator = stockCodeList.iterator();
            while(iterator.hasNext()) {
                String stockCode = iterator.next();
                String briefInfoUrl = corporationBaseUrl + stockCode + "/";
                String financialUrl = financialBaseUrl + stockCode + "/finance.html#stockpage";
                String forecastUrl = briefInfoUrl + "worth/#forecastdetail";
                Stock stock = new Stock();

                Document docBriefInfo = briefAccessor.getDocFromUrl(briefInfoUrl);
                briefConverter.convert(docBriefInfo, stock);

                Workbook financialExcel = financialAccessor.getWorkbookFromUrl(financialUrl);
                financialConverter.convert(financialExcel, stock);

                Document docForecast = forecastAccessor.getDocFromUrl(forecastUrl);
                forecastConverter.convert(docForecast, stock);

                System.out.println(stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ExcelReader excelReader = new ExcelReader();
//        StockImporter importer = new StockImporter();
//        importer.createWorkBook();
    }
}
