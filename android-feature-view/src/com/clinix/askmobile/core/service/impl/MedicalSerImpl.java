package com.clinix.askmobile.core.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.BodyPart;
import com.clinix.askmobile.core.bean.Depart;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.IllStruct;
import com.clinix.askmobile.core.bean.ShortIllInfo;
import com.clinix.askmobile.core.bean.ShortSymInfo;
import com.clinix.askmobile.core.dao.MedicalDao;
import com.clinix.askmobile.core.dao.impl.MedicalDaoImpl;
import com.clinix.askmobile.core.service.MedicalSer;

public class MedicalSerImpl implements MedicalSer {
    private MedicalDao medicalDao;
    private MedicalCore medicalCore;
    private static MedicalSer instance;

    private MedicalSerImpl() {
        medicalCore = MedicalCore.instance();
        medicalDao = MedicalDaoImpl.instance();
    }

    public static MedicalSer instance() {
        if (instance == null) {
            instance = new MedicalSerImpl();
        }
        return instance;
    }

    public ArrayList<ShortSymInfo> getSymListByPartId(Body body) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("partId", body.getPart().getId());
        return medicalDao.getSymListByPartId(paramMap);
    }

    /**
     * 获取主症选择列表
     * @param body
     * @return
     */
    public List<ShortSymInfo> SymToFB(Body body) {
        List<ShortSymInfo> outSymList = new ArrayList<ShortSymInfo>();
        return outSymList;
    }

    /**
     * 主症+起病
     * @param cmdInfo
     * @return
     */
    public List<ShortSymInfo> SymBSToZZ(Body body) {
        List<ShortSymInfo> symList = new ArrayList<ShortSymInfo>();
        return symList;
    }

    /**
     * 主症+起病+辅症
     * @param cmdInfo
     * @return
     */
    public List<ShortIllInfo> SymTZToILL(Body body) {
        List<ShortIllInfo> illList = new ArrayList<ShortIllInfo>();
        return illList;
    }

    /**
     * 回溯
     * @param cmdInfo
     * @return
     */
    public List<ShortSymInfo> IllToSym(Body body) {
        List<ShortSymInfo> symList = new ArrayList<ShortSymInfo>();
        return symList;
    }

    /**
     * 根据疾病编号返回疾病信息
     * @param key_no
     * @return
     * @throws Exception
     */
    public IllStruct getIllInfoStruct(int key_no) throws Exception {
        return medicalDao.getIllInfoStruct(key_no);
    }

    @Override
    public List<Depart> getDepartList() {
        return this.medicalDao.getDepartList();
    }

    @Override
    public List<ShortIllInfo> getIllsByDepartId(String deptid) {
        return this.medicalDao.getIllsByDepartId(deptid);
    }

    @Override
    public List<ShortSymInfo> searchSymByKeyWord(String keyWord, Body body) {
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("keyWord", keyWord);
        paramMap.put("sex", Integer.valueOf(body.sex));
        return this.medicalDao.searchSymByKeyWord(paramMap);
    }

    @Override
    public void saveFavourite(HistoryRecord his) throws SQLException {
        this.medicalDao.saveFavourite(his);
    }

    @Override
    public List<BodyPart> getBodyPartList(Body body) {
        return this.medicalDao.getBodyPartList(body);
    }

    @Override
    public void addHistoryRecord(HistoryRecord history) throws Exception {
        this.medicalDao.addHistoryRecord(history);
    }

    @Override
    public List<HistoryRecord> getHistoryRecordList(String tag) {
        return this.medicalDao.getHistoryRecordList(tag);
    }

    @Override
    public void updateHistoryRecord(HistoryRecord history) throws Exception {
        this.medicalDao.updateHistoryRecord(history);
    }
}
