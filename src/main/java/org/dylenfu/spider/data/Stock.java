package org.dylenfu.spider.data;

import java.util.ArrayList;
import java.util.List;

public class Stock {

    private String name;
    private String code;
    private String capital;
    private String floating;
    private List<String> profits;
    private List<String> news;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFloating() { return floating; }

    public void setFloating(String floating) { this.floating = floating; }

    public List<String> getProfits() {
        return profits;
    }

    public void setProfits(List<String> profits) {
        this.profits = profits;
    }

    public List<String> getNews() {
        return news;
    }

    public void setNews(List<String> news) {
        this.news = news;
    }

    public void addProfit(String profit) {
        if (this.profits.isEmpty()) {
            this.profits = new ArrayList<>();
        }
        this.profits.add(profit);
    }

    public void addNews(String url) {
        if (this.news.isEmpty()) {
            this.news = new ArrayList<>();
        }
        this.news.add(url);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", capital='" + capital + '\'' +
                ", floating='" + floating + '\'' +
                ", profits=" + profits +
                ", news=" + news +
                ", concatNews='" + concatNews + '\'' +
                '}';
    }

    public List toHeadList() {
        List list = new ArrayList<String>();
        list.add("股票名称");
        list.add("股票代码");
        list.add("股本");
        list.add("流通股");
        list.add("2013");
        list.add("2014");
        list.add("2015");
        list.add("2016");
        list.add("2017");
        list.add("2018Q1");
        list.add("2018Q2");
        list.add("2018Q3");
        list.add("2018E");
        list.add("2019E");
        list.add("2020E");
        list.add("新闻(扩建、环评、投产)");
        return list;
    }

    String concatNews = "";

    public List toContentList() {

        assert(this.profits.size() == 8):"profits length invalid";

        List list = new ArrayList<String>();

        list.add(this.name);
        list.add(this.code);
        list.add(this.capital);
        list.add(this.profits);
        this.news.forEach(x -> concatNews += (x + "\r"));
        list.add(concatNews);
        return list;
    }
}
