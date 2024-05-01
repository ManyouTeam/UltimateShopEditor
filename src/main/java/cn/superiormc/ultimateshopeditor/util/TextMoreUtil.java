package cn.superiormc.ultimateshopeditor.util;

import cn.superiormc.ultimateshop.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class TextMoreUtil {

    public static List<String> getListWithColor(List<String> inList) {
        List<String> resultList = new ArrayList<>();
        for (String s : inList) {
            resultList.add(TextUtil.parse(s));
        }
        return resultList;
    }

}
