package org.dylenfu.spider.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class StringHelper {

    // 字符串中去除中文
    public static String trimCn(String str) {
        String REGEX_CHINESE = "[\u4e00-\u9fa5]";

        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("").trim();
    }

    // 判断字符串中是否包含数字
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches())
            flag = true;
        return flag;
    }

    public static double str2double(String str) {
        return Double.parseDouble(str);
    }
}
