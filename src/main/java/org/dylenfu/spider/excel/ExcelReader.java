package org.dylenfu.spider.excel;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ExcelReader {

    private Workbook excel;
    private Sheet sheet;
    DataFormatter dataFormatter;

    public ExcelReader(String workbookFilePath, String sheetName) throws IOException {
        // create a workbook from an existing excel file, support xls/xlsx
        excel = WorkbookFactory.create(new File(workbookFilePath));
        // get sheet
        sheet = excel.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("sheet not exist");
        }
        dataFormatter = new DataFormatter();
    }

    public void read() {
        Iterator<Row> rowIterator = sheet.rowIterator();
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
