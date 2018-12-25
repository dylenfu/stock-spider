package org.dylenfu.spider.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Date;
import java.util.List;

public class ExcelWriter {

    private Workbook excel;
    private Sheet sheet;
    private CellStyle cellStyle;
    private String filepath;

    // create a workbook from an existing excel file, support xls/xlsx
    public ExcelWriter(String filepath) {
        this.filepath = filepath;
        File file = new File(filepath);
        try {
            InputStream inputStream = new FileInputStream(file);
            excel = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get default sheet
    public void createSheet(String sheetName) {
        sheet = excel.createSheet(sheetName);
    }

    public void setCellStyle() {
        cellStyle = excel.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
    }

    public void save() {
        try {
            OutputStream outputStream = new FileOutputStream(filepath);
            excel.write(outputStream);
            excel.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createRow(int column, List<Object> list) {
        Row row = sheet.createRow(column);
        for(int i = 0; i < list.size(); i++) {
            setCell(row, i, list.get(i));
        }
    }

    private <T> void setCell(Row row, int column, Object value) {
        Cell cell = row.createCell(column);
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
