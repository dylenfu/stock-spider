package org.dylenfu.spider.converter;

import org.dylenfu.spider.data.Stock;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class StockConverter implements Converter<Document, Stock> {

    public void convert(Document doc, Stock stock) {
        String[] header = getNameAndCode(doc);
        stock.setName(header[0]);
        stock.setCode(header[1]);
    }

    private String[] getNameAndCode(Document doc) {
        String[] ret = new String[2];

        Element squote = doc.getElementById("in_squote");
        Node header = squote.childNode(3).childNode(1).childNode(1);
        ret[0] = header.childNode(1).childNode(0).toString().trim();
        ret[1] = header.childNode(2).toString().trim();
        return ret;
    }
}
