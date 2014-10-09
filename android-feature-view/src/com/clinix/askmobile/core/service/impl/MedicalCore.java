package com.clinix.askmobile.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.IllStruct;
import com.clinix.askmobile.core.bean.ShortBasicInfo;
import com.clinix.askmobile.core.bean.ShortIllInfo;
import com.clinix.askmobile.core.bean.ShortSymInfo;
import com.clinix.askmobile.core.bean.SymStruct;
import com.clinix.askmobile.core.bean.Table;
import com.clinix.askmobile.core.dao.MedicalDao;
import com.clinix.askmobile.core.dao.impl.MedicalDaoImpl;

/**
 * 手机问病的核心数据及服务算法类
 * @author Mx
 */
public class MedicalCore {
    private MedicalDao medicalDao;
    private static MedicalCore medicalCore;

    private MedicalCore() {
        medicalDao = MedicalDaoImpl.instance();
    };

    public static MedicalCore instance() {
        if (medicalCore == null) {
            medicalCore = new MedicalCore();
        }
        return medicalCore;
    }

    /**
     * ILL数组，把ILL表的全部数据加载到这个数组
     */
    private IllStruct[] illDataArray;

    /**
     * SYM数组，把SYM表的全部数据加载到这个数组
     */
    private SymStruct[] symptomDataAarry;

    /**
     * 保存数据库中下面ArrETblNames中17个表的记录数+17个表对应拼音表的记录数+1个RelLink表的记录数（手机只用到Sym、Ill）
     */
    private final int[] dataMaxLengthArray = new int[Table.COUNTS];

    public IllStruct[] getIllDataArray() {
        return illDataArray;
    }

    public void setIllDataArray(IllStruct[] pTblStruIll) {
        illDataArray = pTblStruIll;
    }

    public SymStruct[] getSymptomDataArray() {
        return symptomDataAarry;
    }

    public void setSymptomDataArray(SymStruct[] pTblStruSym) {
        symptomDataAarry = pTblStruSym;
    }

    public int[] getDataMaxLengthArray() {
        return dataMaxLengthArray;
    }

    /**
     * 始化ILL,SYM数据。
     */
    public void init() {
        try {
            System.out.println("载入clinic.properties文件中系统参数！");
            System.getProperties().load(this.getClass().getClassLoader().getResourceAsStream("clinic.properties"));
        } catch (Exception e) {
            System.out.println("载入系统参数失败");
        }
        try {
            /**
             * ILL（疾病） data initial
             */
            if (dataMaxLengthArray[Table.ILL.getIndex()] == 0) {
                dataMaxLengthArray[Table.ILL.getIndex()] = medicalDao.getTableDataCount(Table.ILL.getName());
                loadIllDataToMemery();
            }

            /**
             * SYM(症状) data initial
             */
            if (dataMaxLengthArray[Table.SYM.getIndex()] == 0) {
                dataMaxLengthArray[Table.SYM.getIndex()] = medicalDao.getTableDataCount(Table.SYM.getName());
                loadSymDataToMemery();
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "failed to initial data");
        }
    }

    /**
     * 根据疾病ILL的Key_no，查找对应的FieldStr字段内容
     * @param Key_no
     * @param FieldStr
     * @return
     */
    public String findIllFieldByKeyNo(int Key_no, String FieldStr) {
        return "";
    }

    /**
     * 根据症状SYM的Key_no，查找对应的FieldStr字段内容
     * @param Key_no
     * @param FieldStr
     * @return
     */
    public String findSymFieldByKeyno(int Key_no, String FieldStr) {
        return "";
    }

    /**
     * 根据SYM表的ConID返回SymInfo，用于否症ILL.NOSYMLINK
     * @param ConID
     * @return
     */
    public ShortBasicInfo findSymInfoByConID(int ConID) {
        ShortBasicInfo tSymInfo = new ShortBasicInfo();
        return tSymInfo;
    }

    /**
     * 把疾病ILL表全部数据加载到PTblStruIll数组中，数组下标跟Key_no相同，因此数组从1开始
     * @param index
     */
    private void loadIllDataToMemery() throws Exception {
        illDataArray = new IllStruct[dataMaxLengthArray[Table.ILL.getIndex()] + 1]; // 因为从1开始不从0开始，所以加多1位

        List<IllStruct> illList = this.medicalDao.getIllData();

        for (IllStruct illNode : illList) {
            if (illNode.Cvocable.lastIndexOf(" ") > 0) {
                illNode.Cvocable = illNode.Cvocable.substring(0, illNode.Cvocable.lastIndexOf(" "));
            }
            illDataArray[illNode.Key_no] = illNode;
        }
    }

    /**
     * 把疾病SYM表全部数据加载到PTblStruSym数组中，数组下标跟Key_no相同，因此数组从1开始
     * @param index
     */
    private void loadSymDataToMemery() throws Exception {
        symptomDataAarry = new SymStruct[dataMaxLengthArray[Table.SYM.getIndex()] + 1]; // 因为从1开始不从0开始，所以加多1位

        List<SymStruct> illList = this.medicalDao.getSymData();

        for (SymStruct symNode : illList) {
            if (symNode.Cvocable.lastIndexOf(" ") > 0) {
                symNode.Cvocable = symNode.Cvocable.substring(0, symNode.Cvocable.lastIndexOf(" "));
            }
            symptomDataAarry[symNode.Key_no] = symNode;
        }
    }

    /**
     * 手机问病的推导-根据症状得到疾病列表
     * @param inSymList
     * @param OutCountLmt
     * @param OutPreLmt
     * @param sortType
     * @param xzFlag
     * @param jzCast
     * @param body
     * @param toIll
     */
    public List<ShortIllInfo> getIllBySymList(List<String> inSymList, int OutCountLmt, int OutPreLmt, int sortType, int xzFlag, float jzCast, Body body, int toIll) {
        List<ShortIllInfo> illList = new ArrayList<ShortIllInfo>();
        return illList;
    }

    /**
     * "查找并返回TblName表Key_no对应的FieldStr字段的内容
     * @param table
     * @param Key_no
     * @param fieldStr
     * @return
     */
    private String getTableFieldValueByKeyNo(Table table, int Key_no, String fieldStr) {
        String rsStr = "";
        return rsStr;
    }

    /**
     * 根据ILL.SYMLINK的对应内容返回SYM.KEY_NO列表
     * @param Symlink
     * @return
     */
    private ArrayList<Integer> parseIllSymlinkToList(String Symlink) {
        ArrayList<Integer> symList = new ArrayList<Integer>();
        return symList;
    }

    /**
     * 获取症状函数(Key_no,TblName,List<TSymInfo> SymLst) 返回SymList数量"
     * @param Key_no
     * @param TblName
     * @param outSymList
     * @return
     */
    public int getIllSymList(int Key_no, Table table, List<ShortSymInfo> outSymList) {
        return outSymList.size();
    }

    /**
     * 根据ILL.KEY_NO，查找对应的SYMLINK获取YM列表中所有的主诉，症状。
     * @param Key_no
     * @param Sym_no
     * @param symList
     * @return
     */
    public List<ShortSymInfo> getSymIllSymList(int Key_no, int Sym_no) {
        List<ShortSymInfo> symList = new ArrayList<ShortSymInfo>();
        return symList;
    }

    /**
     * 根据ILL.Key_no，查找对应的SYMLINK获取SYM列表中分值最高的25个主诉,症状；之前已选的Sym_no不要，分值小于5不要
     * @param Key_no
     * @param Sym_no
     * @param symList
     * @return
     */
    public int getMainIllSymList(int Key_no, int Sym_no, List<ShortSymInfo> symList) {
        return symList.size();
    }

}
