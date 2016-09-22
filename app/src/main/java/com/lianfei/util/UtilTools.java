package com.lianfei.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lianfei1314 on 2016/9/21.
 */
public class UtilTools {
    private static Map<String,Integer> map_str2int = new HashMap<>();
    static {
        map_str2int.put("零", 0);
        // map_str2int.put("0", 0);
        map_str2int.put("一", 1);
        map_str2int.put("壹", 1);
        // map_str2int.put("1", 1);
        map_str2int.put("二", 2);
        map_str2int.put("两", 2);
        map_str2int.put("贰", 2);
        // map_str2int.put("2", 2);
        map_str2int.put("三", 3);
        map_str2int.put("叁", 3);
        // map_str2int.put("3", 3);
        map_str2int.put("四", 4);
        map_str2int.put("肆", 4);
        // map_str2int.put("4", 4);
        map_str2int.put("五", 5);
        map_str2int.put("伍", 5);
        // map_str2int.put("5", 5);
        map_str2int.put("六", 6);
        map_str2int.put("陆", 6);
        // map_str2int.put("6", 6);
        map_str2int.put("七", 7);
        map_str2int.put("柒", 7);
        // map_str2int.put("7", 7);
        map_str2int.put("八", 8);
        map_str2int.put("捌", 8);
        // map_str2int.put("8", 8);
        map_str2int.put("九", 9);
        map_str2int.put("玖", 9);
        // map_str2int.put("9", 9);
        map_str2int.put("十", 10);
        map_str2int.put("拾", 10);
        map_str2int.put("百", 100);
        map_str2int.put("佰", 100);
        map_str2int.put("千", 1000);
        map_str2int.put("仟", 1000);
        map_str2int.put("万", 10000);
        map_str2int.put("亿", 100000000);
    }

    public static int convertStringToDights(String str) {
        int len = str.length();
        int total = 0;
        int r = 1;

        for (int index = len - 1; index >= 0; index--) {
            String s = str.substring(index,index + 1);
            Integer val = map_str2int.get(s);
            if (val != null) {
                int v = val.intValue();
                if (v >= 10 && index == 0) {
                    // 处理 十三、十四、十*等
                    if (v > r) {
                        r = v;
                        total = total + v;
                    } else {
                        r = r * v;
                    }
                } else if (v >= 10) {
                    if (v > r) {
                        r = v;
                    } else {
                        r = r * v;
                    }
                } else {
                    total = total + r * v;
                }
            } else {
                break;
            }
        }
        return total;
    }

    public static String convertStringWithDights(String str) {
        int len = str.length();
        int total = 0;
        int r = 1;
        boolean total_accept = false;
        String ret_str = "";

        for (int index = len - 1; index >= 0; index--) {
            String s = str.substring(index,index + 1);
            Integer val = map_str2int.get(s);
            if (val != null) {
                int v = val.intValue();
                total_accept = true;
                if (v >= 10 && index == 0) {
                    // 处理 十三、十四、十*等
                    if (v > r) {
                        r = v;
                        total = total + v;
                    } else {
                        r = r * v;
                    }
                } else if (v >= 10) {
                    if (v > r) {
                        r = v;
                    } else {
                        r = r * v;
                    }
                } else {
                    total = total + r * v;
                }
                if (index == 0) {
                    ret_str = String.valueOf(total) + ret_str;
                }
            } else {
                if (!total_accept) {
                    ret_str = s + ret_str;
                } else {
                    ret_str = s + String.valueOf(total) + ret_str;
                }
                total_accept = false;
                total = 0;
                r = 1;
            }
        }
        return ret_str;
    }
}
