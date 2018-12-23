package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class StockBriefDocumentConverter implements Converter<Document, Stock> {

    public void convert(Document doc, Stock stock) {
        String[] header = getNameAndCode(doc);
        stock.setName(header[0]);
        stock.setCode(header[1]);

        String capital = getCapital(doc);
        stock.setCapital(capital);

        String floating = getFloating(doc);
        stock.setFloating(floating);
    }

    private String[] getNameAndCode(Document doc) {
        String[] ret = new String[2];

        Element squote = doc.getElementById("in_squote");
        Node header = squote.childNode(3).childNode(1).childNode(1);
        ret[0] = header.childNode(1).childNode(0).toString().trim();
        ret[1] = header.childNode(2).toString().trim();
        return ret;
    }

    private String getCapital(Document doc) {
        Elements elements = doc.getElementsByClass("company_details");
        Node capitalNode = elements.get(0).childNode(55);
        return capitalNode.childNode(0).toString().trim();
    }

    private String getFloating(Document doc) {
        Elements elements = doc.getElementsByClass("company_details");
        Node floatingNode = elements.get(0).childNode(59);
        return floatingNode.childNode(0).toString().trim();
    }

}
