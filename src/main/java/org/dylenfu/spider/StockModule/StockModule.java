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

        Accessor<Document> briefAccessor = new HtmlAccessor(briefDir);
        Accessor<Document> forecastAccessor = new HtmlAccessor(forecastDir);
        Accessor<ExcelReader> financialSeasonAccessor = new ExcelAccessor(financialSeasonDir);
        Accessor<ExcelReader> financialYearAccessor = new ExcelAccessor(financialYearDir);

        Converter briefConverter = new StockBriefDocumentConverter();
        Converter forecastConverter = new StockForecastDocumentConverter();
        Converter financialSeasonConverter = new StockFinancialSeasonXlsConverter();
        Converter financialYearConverter = new StockFinancialYearXlsConverter();

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
                System.out.println(stock);

                stocks.add(stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write() {

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
}