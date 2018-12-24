package org.dylenfu.spider.accessor;

abstract class AbstractAccessor {

    private String dirPath;

    public void setCacheDir(String path) { this.dirPath = path; }
    public String getDirPath() { return this.dirPath; }
}
