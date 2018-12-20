package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockConverter implements Converter<Document, Stock> {

    public void convertBaseInfo(Document doc, Stock stock) {
        String[] header = getNameAndCode(doc);
        stock.setName(header[0]);
        stock.setCode(header[1]);

        double capital = getCapital(doc);
        stock.setCapital(capital);
    }

    public void convertFinancial(Document doc, Stock stock) {
        List<Double> profits = getProfits(doc);
        stock.setProfits(profits);
    }

    public void convertNewsFlash(Document doc, Stock stock) {

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

    private List<Double> getProfits(Document doc) {
        List<Double> list = new ArrayList<>();

        // Elements elements = doc.getElementsByAttribute();
        return list;
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
