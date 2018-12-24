package org.dylenfu.spider.excel;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    private Workbook excel;
    private Sheet sheet;
    private DataFormatter dataFormatter;
    private Iterator<Row> rowIterator;

    // create a workbook from an existing excel file, support xls/xlsx
    public ExcelReader(String workbookFilePath) {
        try {
            excel = WorkbookFactory.create(new File(workbookFilePath));
            dataFormatter = new DataFormatter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get default sheet
    public void setSheet() {
        sheet = excel.getSheetAt(0);
        if (sheet == null) {
            throw new RuntimeException("sheet not exist");
        }
        rowIterator = sheet.rowIterator();
    }

    public void setSheet(String sheetName) {
        sheet = excel.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("sheet not exist");
        }
    }

    public List<String> readRow(int rowIndex, int startCol, int endCol) {
        List<String> list = new ArrayList<>();
        Row row = sheet.getRow(rowIndex);
        for(int i = startCol; i <= endCol; i++) {
            Cell cell = row.getCell(i);
            String cellValue = dataFormatter.formatCellValue(cell);
            list.add(cellValue);
        }
        return list;
    }

    public void read() {
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.println(cellValue);
            }
        }
    }
}
