package org.dylenfu.spider.converter;

public interface Converter<Up, Down> {
    public void convertBaseInfo(Up src, Down dst);
    public void convertFinancial(Up src, Down dst);
    public void convertNewsFlash(Up src, Down dst);
}
