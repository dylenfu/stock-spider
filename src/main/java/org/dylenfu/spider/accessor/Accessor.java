package org.dylenfu.spider.accessor;

abstract class Accessor {

    public String dirPath;

    public void setCacheDir(String path) { this.dirPath = path; }

    public String urlToFileName(String url) { return ""; }
}
