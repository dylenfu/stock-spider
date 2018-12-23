package org.dylenfu.spider.converter;

public interface Converter<Up, Down> {
    public void convert(Up src, Down dst);
}
