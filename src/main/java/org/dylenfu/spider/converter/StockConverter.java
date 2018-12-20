package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockConverter implements Converter<Document, Stock> {

    public void convert(Document doc, Stock stock) {
        String[] header = getNameAndCode(doc);
        stock.setName(header[0]);
        stock.setCode(header[1]);

        double capital = getCapital(doc);
        stock.setCapital(capital);
    }

    private String[] getNameAndCode(Document doc) {
        String[] ret = new String[2];

        Element squote = doc.getElementById("in_squote");
        Node header = squote.childNode(3).childNode(1).childNode(1);
        ret[0] = header.childNode(1).childNode(0).toString().trim();
        ret[1] = header.childNode(2).toString().trim();
        return ret;
    }

    private double getCapital(Document doc) {
        Elements elements = doc.getElementsByClass("company_details");
        String str = elements.get(0).childNode(55).childNode(0).toString();
        str = trimCn(str);
        return str2double(str);
    }

    private String trimCn(String str) {
        String REGEX_CHINESE = "[\u4e00-\u9fa5]";

        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("").trim();
    }

    private double str2double(String str) {
        return Double.parseDouble(str);
    }

}
