package com.zxy.Utils;

public class FilterUtil {
    static DoubleArrayTrie dat = new DoubleArrayTrie(40);
    static {
        dat.insert("共产党");
        dat.insert("毛泽东");
        dat.insert("习近平");
        dat.insert("贸易战");
    }
    public static String filter(String s){
        return dat.filter("*",s);
    }
}
