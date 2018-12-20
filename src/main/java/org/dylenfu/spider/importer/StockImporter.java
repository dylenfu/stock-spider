package org.dylenfu.spider.importer;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockImporter {

    private HSSFWorkbook excel;
    private HSSFCellStyle cellStyle;
    private String filepath = "/Users/dylen/workspace/javahome/github.com/dylenfu/stock-spider/test.xls";

    public void createWorkBook(List head, List<List> content) {
        excel = new HSSFWorkbook();
        cellStyle = excel.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // create work sheet
        HSSFSheet sheet = excel.createSheet("学生表一");

        // add head row and element
        createRow(sheet, 0, head);

        // add content row and element
        for(int i = 0; i < content.size(); i++) {
            createRow(sheet, i + 1, content.get(i));
        }

        // save file
        try {
            OutputStream stream = new FileOutputStream(filepath);
            excel.write(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createRow(HSSFSheet sheet, int column, List<Object> list) {
        HSSFRow row = sheet.createRow(column);
        for(int i = 0; i < list.size(); i++) {
            setCell(row, i, list.get(i));
        }
    }

    private <T> void setCell(HSSFRow row, int column, T value) {
        HSSFCell cell = row.createCell(column);
        if (value instanceof String) {
            cell.setCellValue((String)value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double)value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date)value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean)value);
        } else {
            throw new RuntimeException("value type unsupported");
        }
        cell.setCellStyle(cellStyle);
    }
}
