package org.dylenfu.spider.accessor;

public interface Accessor<T> {

    public T getFileFromUrl(String url);
    public String urlToFileName(String url);

}
