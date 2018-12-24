package org.dylenfu.spider.accessor;

import org.dylenfu.spider.excel.ExcelReader;
import org.dylenfu.spider.helper.FileHelper;

public class ExcelAccessor extends AbstractAccessor implements Accessor<ExcelReader> {

    public ExcelAccessor(String dirPath) {
        setCacheDir(dirPath);
    }

    public ExcelReader getFileFromUrl(String url) {
        String filename = urlToFileName(url);
        String filepath = getDirPath() + filename;

        FileHelper fileHelper = new FileHelper();

        try {
            if (!fileHelper.exist(filepath)) {
                fileHelper.download(url, filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ExcelReader(filepath);
    }

    public String urlToFileName(String url) {
        String[] data = url.split("=");
        if (data.length < 4) throw new RuntimeException("url for xls invalid");

        String stockcode = data[3];
        String prefix = "stock_";
        String suffix = "_finanical.xls";

        return prefix + stockcode + suffix;
    }

}
