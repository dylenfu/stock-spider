package org.dylenfu.spider.converter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.dylenfu.spider.data.Stock;
import org.dylenfu.spider.excel.ExcelReader;

import java.util.Iterator;
import java.util.List;

public class StockFinancialYearXlsConverter implements Converter<ExcelReader, Stock>{

    public void convert(ExcelReader excel, Stock stock) {
        excel.setSheet();
        List<String> list = excel.readRow(2, 3, 4);
        System.out.println("----hahha");
    }


}
