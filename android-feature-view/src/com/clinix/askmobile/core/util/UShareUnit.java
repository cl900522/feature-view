package com.clinix.askmobile.core.util;

import java.util.ArrayList;
import java.util.List;

import com.clinix.askmobile.core.bean.SymCountAndRate;

/**
 * 手机问病巡诊的算法计算工具类
 * @author Mx
 */
public class UShareUnit {
    /**
     * 查找Atext在ArrTmp数组中的位置，查不到返回-1
     * @param Atext
     * @param ArrTmp
     * @return
     */
    public static int indexOfStringArray(String Atext, String[] ArrTmp) {
        int index = -1;
        for (int ni = 0; ni <= ArrTmp.length - 1; ni++) {
            if (Atext.toUpperCase().equals(ArrTmp[ni].toUpperCase())) {
                index = ni;
                break;
            }
        }
        return index;
    }

    /**
     * 把Key_no转换长度5的字符串，不够前面补0
     * @param Key_no
     * @return
     */
    public static String formatKeyNo(int Key_no) {
        if (Key_no == -1)
            return "00000";
        String Kno = String.valueOf(Key_no);
        while (Kno.length() < 5)
            Kno = "0" + Kno;
        return Kno;
    }

    /**
     * 判断是否包含否决症状列表中的一个症状，如包含则返回true
     * @param DenyCodeLst
     * @param SymLinkStr
     * @return
     */
    public static boolean WthConDenyCvoLst(ArrayList<String> DenyCodeLst, String SymLinkStr) {
        boolean bRes = false;
        return bRes;
    }

    /**
     * 该函数返回该疾病包含症状个数及百分比
     */
    public static SymCountAndRate getSymRateAndCount(String SymLinkStr, String SymOrStr, String SymRateStr, List<String> inSymList, int XZFlag, float JZCst) {
        SymCountAndRate symCountAndRate = new SymCountAndRate();
        return symCountAndRate;
    }

    /**
     * 该函数返回该疾病包含症状个数及否决症状个数
     * @param SymLinkStr
     * @param SymOrStr
     * @param SymRateStr
     * @param CodeLst
     * @param DenyCodeLst
     * @param SymCount
     * @param DenySymCount
     */
    private void getSymCountAndDenySymCount(String SymLinkStr, String SymOrStr, String SymRateStr, ArrayList<String> CodeLst, ArrayList<String> DenyCodeLst, int SymCount, int DenySymCount) {
    }

    public static String removeStringRange(String resource, int startIndex, int endIndex) {
        return resource.substring(0, startIndex) + resource.substring(startIndex + endIndex);
    }
}
