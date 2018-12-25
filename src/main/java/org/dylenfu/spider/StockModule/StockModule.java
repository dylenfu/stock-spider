package org.dylenfu.spider.StockModule;

import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dylenfu.spider.accessor.*;
import org.dylenfu.spider.converter.*;
import org.dylenfu.spider.data.*;
import org.dylenfu.spider.excel.*;

import org.jsoup.nodes.Document;

public class StockModule {

    private List<Stock> stocks = new ArrayList<>();
    private Config config;

    public StockModule(Config config) {
        this.config = config;
    }

    public void read() {
        List<String> stockCodeList = config.getStringList("stock_codes");
        String briefDir = config.getString("brief_cache_dir");
        String forecastDir = config.getString("forecast_cache_dir");
        String financialSeasonDir = config.getString("financial_season_cache_dir");
        String financialYearDir = config.getString("financial_year_cache_dir");
        String capacityDir = config.getString("capacity_cache_dir");
        String eiaDir = config.getString("eia_cache_dir");
        String expandDir = config.getString("expand_cache_dir");

        Accessor<Document> briefAccessor = new HtmlAccessor(briefDir);
        Accessor<Document> forecastAccessor = new HtmlAccessor(forecastDir);
        Accessor<ExcelReader> financialSeasonAccessor = new ExcelAccessor(financialSeasonDir);
        Accessor<ExcelReader> financialYearAccessor = new ExcelAccessor(financialYearDir);
        Accessor<Document> capacityAccessor = new HtmlAccessor(capacityDir);
        Accessor<Document> eiaAccessor = new HtmlAccessor(eiaDir);
        Accessor<Document> expandAccessor = new HtmlAccessor(expandDir);

        Converter briefConverter = new StockBriefDocumentConverter();
        Converter forecastConverter = new StockForecastDocumentConverter();
        Converter financialSeasonConverter = new StockFinancialSeasonXlsConverter();
        Converter financialYearConverter = new StockFinancialYearXlsConverter();
        Converter urlConverter = new StockGoogleSearchUrlConverter();

        try {
            Iterator<String> iterator = stockCodeList.iterator();
            while(iterator.hasNext()) {
                String stockCode = iterator.next();

                Stock stock = new Stock();

                StockElementConfig c1 = new StockElementConfig(getBriefInfoUrl(stockCode), briefDir);
                StockElementCollector briefInfoCollector = new StockElementCollector(c1, briefAccessor, briefConverter);
                briefInfoCollector.read(stock);

                StockElementConfig c2 = new StockElementConfig(getForecastUrl(stockCode), forecastDir);
                StockElementCollector forecastCollector = new StockElementCollector(c2, forecastAccessor, forecastConverter);
                forecastCollector.read(stock);

                StockElementConfig c3 = new StockElementConfig(getFinancialYearUrl(stockCode), financialYearDir);
                StockElementCollector financialYearCollector = new StockElementCollector(c3, financialYearAccessor, financialYearConverter);
                financialYearCollector.read(stock);

                StockElementConfig c4 = new StockElementConfig(getFinancialSeasonUrl(stockCode), financialYearDir);
                StockElementCollector financialSeasonCollector = new StockElementCollector(c4, financialSeasonAccessor, financialSeasonConverter);
                financialSeasonCollector.read(stock);

                StockElementConfig c5 = new StockElementConfig(getGoogleSearchCapacity(stock.getName()), capacityDir);
                StockElementCollector capacityCollector = new StockElementCollector(c5, capacityAccessor, urlConverter);
                capacityCollector.read(stock);

                StockElementConfig c6 = new StockElementConfig(getGoogleSearchExpand(stock.getName()), expandDir);
                StockElementCollector expandCollector = new StockElementCollector(c6, expandAccessor, urlConverter);
                expandCollector.read(stock);

                StockElementConfig c7 = new StockElementConfig(getGoogleSearchEIA(stock.getName()), eiaDir);
                StockElementCollector eiaCollector = new StockElementCollector(c7, eiaAccessor, urlConverter);
                eiaCollector.read(stock);

                System.out.println(stock);

                stocks.add(stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write() {
        String sheetName = config.getString("sheet_name");
        String outputPath = config.getString("output_excel");
        ExcelWriter writer = new ExcelWriter(outputPath);
        writer.createSheet(sheetName);
        writer.setCellStyle();

        Stock head = new Stock();
        writer.createRow(1, head.toHeadList());

        for(int i = 0; i < stocks.size(); i++) {
            int rowIdx = i + 2;
            List<Object> row = stocks.get(i).toContentList();
            writer.createRow(rowIdx, row);
        }
        writer.save();
    }

    private String getBriefInfoUrl(String stockCode) {
        String corporationBaseUrl = config.getString("base_url");
        return corporationBaseUrl + stockCode + "/";
    }

    private String getFinancialSeasonUrl(String stockCode) {
        String financialSeasonBaseUrl = config.getString("financial_simple_url");
        return financialSeasonBaseUrl + stockCode;
    }

    private String getFinancialYearUrl(String stockCode) {
        String financialYearBaseUrl = config.getString("financial_year_url");
        return financialYearBaseUrl + stockCode;
    }

    private String getForecastUrl(String stockCode) {
        String briefInfoUrl = getBriefInfoUrl(stockCode);
        return briefInfoUrl + "worth/#forecastdetail";
    }

    final int GOOGLE_SEARCH_CAPACITY = 2;
    private String getGoogleSearchCapacity(String stockName) {
        String baseUrl = config.getString("google_url");
        // "https://www.google.com/search?q=002648 投产&num=10"
        return baseUrl + "q=" + stockName + " 投产&num=" + GOOGLE_SEARCH_CAPACITY;
    }

    private String getGoogleSearchEIA(String stockName) {
        String baseUrl = config.getString("google_url");
        // "https://www.google.com/search?q=002648 环评&num=10"
        return baseUrl + "q=" + stockName + " 环评&num=" + GOOGLE_SEARCH_CAPACITY;
    }

    private String getGoogleSearchExpand(String stockName) {
        String baseUrl = config.getString("google_url");
        // "https://www.google.com/search?q=002648 环评&num=10"
        return baseUrl + "q=" + stockName + " 扩建&num=" + GOOGLE_SEARCH_CAPACITY;
    }
}
