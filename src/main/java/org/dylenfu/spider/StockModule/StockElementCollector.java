package org.dylenfu.spider.StockModule;

import org.dylenfu.spider.accessor.Accessor;
import org.dylenfu.spider.converter.Converter;
import org.dylenfu.spider.data.Stock;

public class StockElementCollector<A> {

    private StockElementConfig config;
    private Accessor<A> accessor;
    private Converter<A, Stock> converter;

    public StockElementCollector(StockElementConfig config, Accessor<A> accessor, Converter<A, Stock> converter) {
        this.config = config;
        this.accessor = accessor;
        this.converter = converter;
    }

    public void read(Stock stock) {
        String url = config.getUrl();
        A data = accessor.getFileFromUrl(url);
        converter.convert(data, stock);
    }
}
