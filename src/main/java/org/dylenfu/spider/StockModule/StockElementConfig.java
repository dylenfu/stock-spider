package org.dylenfu.spider.StockModule;

public class StockElementConfig {

    private String url;
    private String cacheDir;

    public StockElementConfig(String url, String cacheDir) {
        this.url = url;
        this.cacheDir = cacheDir;
    }

    public String getUrl() {
        return url;
    }

    public String getCacheDir() {
        return cacheDir;
    }
}
