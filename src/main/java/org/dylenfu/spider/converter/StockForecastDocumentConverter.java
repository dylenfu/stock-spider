package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.dylenfu.spider.helper.StringHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StockForecastDocumentConverter implements Converter<Document, Stock>{

    public void convert(Document doc, Stock stock) {
        List<String> list = getForecasts(doc);
        stock.setForecasts(list);
    }

    private List<String> getForecasts(Document doc) {
        List<String> list = new ArrayList<>();
        Element forecastDetail = doc.getElementById("forecastdetail");
        if (forecastDetail == null) {
            return list;
        }
        Element table = forecastDetail.select("table").get(0);
        Elements rows = table.select("tr");

        if (rows.size() < 2) {
            return list;
        }

        int startYear = 2018;
        int startCol = 4;
        int size = 3;
        List<ColData> colDataList = new ArrayList<>();
        for(int s = 0; s < size; s++) {
            ColData c = new ColData(startYear + s, startCol + s);
            colDataList.add(c);
        }

        // 2018， 2019， 20203年预测均值, 保留3位小数
        DecimalFormat doubleFormatter = new DecimalFormat("0.000");
        for(ColData colData: colDataList) {
            Double sum = 0d;
            int length = 0;

            for(int i = 2; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cols = row.select("td");
                String colv = cols.get(colData.colIdx).text();
                if (StringHelper.hasDigit(colv)) {
                    Double value = StringHelper.str2double(StringHelper.trimCn(colv));
                    sum += value;
                    length++;
                }
            }

            // get average value
            if (sum == 0) {
                list.add("--");
            } else {
                Double avrg = sum / length;
                list.add(doubleFormatter.format(avrg));
            }
        }

        return list;
    }

    class ColData {
        int year;
        int colIdx;

        ColData(int year, int colIdx) {
            this.year = year;
            this.colIdx = colIdx;
        }
    }
}
