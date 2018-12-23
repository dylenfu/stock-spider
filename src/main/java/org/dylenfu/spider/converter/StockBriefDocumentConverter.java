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

        String capital = getCapitalAndFloating(doc);
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

    // TODO(fuk): debug to get floating data
    private String getCapitalAndFloating(Document doc) {
        Elements elements = doc.getElementsByClass("company_details");
        String str = elements.get(0).childNode(55).childNode(0).toString();
        return str.trim();
    }

//    private List<String> getProfits(Document doc) {
//        List<String> list = new ArrayList<>();
//        Element table = doc.getElementById("cwzbTable").getElementsByClass("data_tbody").get(0);
//        Element data = table.getElementById("main");
//        return list;
//    }

}
