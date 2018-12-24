package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.dylenfu.spider.excel.ExcelReader;

import java.util.List;

public class StockFinancialYearXlsConverter implements Converter<ExcelReader, Stock>{

    public void convert(ExcelReader excel, Stock stock) {
        excel.setSheet();
        List<String> list = excel.readRow(3, 1, 7);
        stock.setProfitsYear(list);
    }

}
