package org.dylenfu.spider.excel;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class ExcelWriter {

    private Workbook excel;
    private Sheet sheet;
    private CellStyle cellStyle;
    private String filepath;
    private DataFormatter dataFormatter;

    // create a workbook from an existing excel file, support xls/xlsx
    public ExcelWriter(String filepath) {
        this.filepath = filepath;
        try {
            excel = WorkbookFactory.create(false);
            //excel = WorkbookFactory.create(new File(this.filepath));
            cellStyle = excel.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            dataFormatter = new DataFormatter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get default sheet
    public void getSheet(String sheetName) {
        sheet = excel.getSheet(sheetName);
        if (sheet == null) {
            sheet = excel.createSheet(sheetName);
        }
    }

    public void save() {
        System.out.println(excel);
        try {
            OutputStream stream = new FileOutputStream(filepath);
            excel.write(stream);
            stream.close();
            excel.close();
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
