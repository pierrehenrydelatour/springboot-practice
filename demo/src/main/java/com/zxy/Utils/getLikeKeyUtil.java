package com.zxy.Utils;

public class getLikeKeyUtil {
    public static String getKey(int aId, int userId){
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf(aId));
        sb.append("::");
        sb.append(String.valueOf(userId));
        return sb.toString();
    }
}
