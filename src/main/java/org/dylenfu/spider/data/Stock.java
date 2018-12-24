package org.dylenfu.spider.data;

import java.util.ArrayList;
import java.util.List;

public class Stock {

    private String name;
    private String code;
    private String capital;
    private String floating;
    private List<String> forecasts;
    private List<String> profitsYear;
    private List<String> profitsSeason;
    private List<String> news;

    public void Stock() {
        this.profitsYear = new ArrayList<>();
        this.profitsSeason = new ArrayList<>();
        this.news = new ArrayList<>();
        this.profitsSeason = new ArrayList<>();
        this.forecasts = new ArrayList<>();
    }

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

    public List<String> getProfitsSeason() { return profitsSeason; }

    public void setProfitsSeason(List<String> profitsSeason) { this.profitsSeason = profitsSeason; }

    public List<String> getProfitsYear() {
        return profitsYear;
    }

    public void setProfitsYear(List<String> profitsYear) {
        this.profitsYear = profitsYear;
    }

    public List<String> getNews() {
        return news;
    }

    public void setNews(List<String> news) {
        this.news = news;
    }

    public List<String> getForecasts() { return forecasts; }

    public void setForecasts(List<String> forecasts) { this.forecasts = forecasts; }

    public void addProfitYear(String profit) { this.profitsYear.add(profit); }

    public void addProfitSeason(String profit) { this.profitsSeason.add(profit); }

    public void addNews(String url) { this.news.add(url); }

    public void addForecast(String forecast) { this.forecasts.add(forecast); }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", capital='" + capital + '\'' +
                ", floating='" + floating + '\'' +
                ", forecasts=" + forecasts +
                ", profitsYear=" + profitsYear +
                ", profitsSeason=" + profitsSeason +
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

        assert(this.profitsYear.size() == 8):"profitsYear length invalid";

        List list = new ArrayList<String>();

        list.add(this.name);
        list.add(this.code);
        list.add(this.capital);
        list.add(this.profitsYear);
        this.news.forEach(x -> concatNews += (x + "\r"));
        list.add(concatNews);
        return list;
    }
}
