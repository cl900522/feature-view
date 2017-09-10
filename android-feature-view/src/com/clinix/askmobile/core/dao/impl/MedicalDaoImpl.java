package com.clinix.askmobile.core.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.BodyPart;
import com.clinix.askmobile.core.bean.Depart;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.IllStruct;
import com.clinix.askmobile.core.bean.ShortBasicInfo;
import com.clinix.askmobile.core.bean.ShortIllInfo;
import com.clinix.askmobile.core.bean.ShortSymInfo;
import com.clinix.askmobile.core.bean.SymStruct;
import com.clinix.askmobile.core.dao.MedicalDao;

public class MedicalDaoImpl implements MedicalDao {
    private static MedicalDao instance;

    private MedicalDaoImpl() {
    }

    public static MedicalDao instance() {
        if (instance == null) {
            instance = new MedicalDaoImpl();
        }
        return instance;
    }

    @Override
    public int getTableDataCount(String tableName) {
        int dataCount = 0;
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query(tableName, new String[] { "max(key_no)" }, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                dataCount = cursor.getInt(0);
            }
            Log.d("Data counts of table-" + tableName + " is", "" + dataCount);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取表" + tableName + "数据行出错");
        }
        return dataCount;
    }

    @Override
    public ArrayList<IllStruct> getIllData() {
        ArrayList<IllStruct> illList = new ArrayList<IllStruct>();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query("ill", IllStruct.tableColums, null, null, null, null, null);

            while (cursor.moveToNext()) {
                IllStruct ill = new IllStruct();
                /**
                 * O-R映射
                 */
                ill.Key_no = cursor.getInt(IllStruct.KEY_NO_INDEX);
                ill.Cvocable = cursor.getString(IllStruct.CVOCABLE_INDEX);
                ill.SYMLink = cursor.getString(IllStruct.SYMLINK_INDEX);
                ill.NOSYMLINK = cursor.isNull(IllStruct.NOSYMLINK_INDEX) ? "" : cursor.getString(IllStruct.NOSYMLINK_INDEX);
                ill.IsChild = cursor.isNull(IllStruct.ISCHILD_INDEX) ? 0 : cursor.getInt(IllStruct.ISCHILD_INDEX);
                ill.IsWoman = cursor.isNull(IllStruct.ISWOMAN_INDEX) ? 0 : cursor.getInt(IllStruct.ISWOMAN_INDEX);
                ill.IsMan = cursor.isNull(IllStruct.ISMAN_INDEX) ? 0 : cursor.getInt(IllStruct.ISMAN_INDEX);
                illList.add(ill);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取表疾病数据行出错");
        }
        return illList;
    }

    @Override
    public ArrayList<SymStruct> getSymData() {
        ArrayList<SymStruct> symList = new ArrayList<SymStruct>();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query("sym", SymStruct.tableColumns, null, null, null, null, null);

            while (cursor.moveToNext()) {
                SymStruct sym = new SymStruct();
                /**
                 * O-R映射
                 */
                sym.Key_no = cursor.getInt(SymStruct.KEY_NO_INDEX);
                sym.Cvocable = cursor.getString(SymStruct.CVOCABLE_INDEX);
                sym.CONID = cursor.isNull(SymStruct.CONID_INDEX) ? 0 : cursor.getInt(SymStruct.CONID_INDEX);
                sym.ISZS = cursor.isNull(SymStruct.ISZS_INDEX) ? 0 : cursor.getInt(SymStruct.ISZS_INDEX);
                sym.ISFB = cursor.isNull(SymStruct.ISFB_INDEX) ? 0 : cursor.getInt(SymStruct.ISFB_INDEX);
                sym.ISBS = cursor.isNull(SymStruct.ISBS_INDEX) ? 0 : cursor.getInt(SymStruct.ISBS_INDEX);
                sym.ISZZ = cursor.isNull(SymStruct.ISZZ_INDEX) ? 0 : cursor.getInt(SymStruct.ISZZ_INDEX);
                sym.ISTZ = cursor.isNull(SymStruct.ISTZ_INDEX) ? 0 : cursor.getInt(SymStruct.ISTZ_INDEX);
                sym.ISJY = cursor.isNull(SymStruct.ISJY_INDEX) ? 0 : cursor.getInt(SymStruct.ISJY_INDEX);
                symList.add(sym);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取症状表数据行出错");
        }
        return symList;
    }

    @Override
    public ArrayList<ShortSymInfo> getSymListByPartId(Map<String, Object> param) {
        String tableName = (String) param.get("tableName");
        String partId = param.get("partId").toString();
        ArrayList<ShortSymInfo> temp = new ArrayList<ShortSymInfo>();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query(tableName, ShortSymInfo.tableColumns, "partid=?", new String[] { partId }, null, null, null);

            while (cursor.moveToNext()) {
                ShortSymInfo sym = new ShortSymInfo();
                /**
                 * O-R映射
                 */
                sym.Key_no = Integer.parseInt(cursor.getString(ShortSymInfo.KEYNO_INDEX));
                sym.Cvocable = cursor.getString(ShortSymInfo.CVOCABLE_INDEX);
                temp.add(sym);
            }
        } catch (Exception e) {
            System.out.println("获取部位的症状选项出错");
        }
        return temp;
    }

    @Override
    public IllStruct getIllInfoStruct(int key_no) {
        IllStruct ill = new IllStruct();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query("ill", IllStruct.tableInfoColums, "key_no=?", new String[] { String.valueOf(key_no) }, null, null, null);
            if (cursor.moveToFirst()) {
                ill.xml = cursor.getString(IllStruct.XML_INDEX);
                ill.dept = cursor.getString(IllStruct.DEPT_INDEX);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取表症状数据行出错");
        }
        return ill;
    }

    /**
     *
     */
    @Override
    public ArrayList<Depart> getDepartList() {
        ArrayList<Depart> deptList = new ArrayList<Depart>();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query("v_dept", Depart.dataCoulumns, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Depart dept = new Depart();
                /**
                 * O-R映射
                 */
                /**
                 * dept.setId(cursor.getInt(Depart.ID_INDEX));
                 */
                dept.setName(cursor.getString(Depart.DEPT_INDEX));
                deptList.add(dept);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取科室列表出错");
        }
        return deptList;
    }

    @Override
    public ArrayList<ShortIllInfo> getIllsByDepartId(String dept) {
        ArrayList<ShortIllInfo> illList = new ArrayList<ShortIllInfo>();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query("ill", ShortIllInfo.tableColumns, "dept=?", new String[] { dept }, null, null, null);

            while (cursor.moveToNext()) {
                ShortIllInfo ill = new ShortIllInfo();
                /**
                 * O-R映射
                 */
                ill.Key_no = cursor.getInt(ShortIllInfo.KEYNO_INDEX);
                ill.Cvocable = cursor.getString(ShortIllInfo.TIT_INDDEX);
                illList.add(ill);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取科室疾病列表出错");
        }
        return illList;
    }

    @Override
    public ArrayList<ShortSymInfo> searchSymByKeyWord(Map<String, Object> paramMap) {
        String tableName = (String) paramMap.get("tableName");
        String keyWord = (String) paramMap.get("keyWord");
        Boolean pyFlag = (Boolean) paramMap.get("pyFlag");
        Integer sex = (Integer) paramMap.get("sex");

        String whereStr = "";
        if (pyFlag) {
            whereStr = "cvo like '%" + keyWord + "%'";
        } else {
            whereStr = "py like '%" + keyWord + "%'";
        }
        whereStr += " and (sex=? or sex=0) ";
        ArrayList<ShortSymInfo> symList = new ArrayList<ShortSymInfo>();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query(tableName, ShortSymInfo.tableColumns, whereStr, new String[] { sex.toString() }, "key_no", null, null);

            while (cursor.moveToNext()) {
                ShortSymInfo sym = new ShortSymInfo();
                /**
                 * O-R映射
                 */
                sym.Key_no = Integer.parseInt(cursor.getString(ShortSymInfo.KEYNO_INDEX));
                sym.Cvocable = cursor.getString(ShortSymInfo.CVOCABLE_INDEX);
                symList.add(sym);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "根据症状搜索症状列表出错");
        }
        return symList;
    }

    @Override
    public void saveFavourite(HistoryRecord his) throws SQLException {
    }

    @Override
    public List<BodyPart> getBodyPartList(Body body) {
        List<BodyPart> partList = new ArrayList<BodyPart>();
        try {
            SQLiteDatabase db = SQLiteHelper.instance().getReadableDatabase();
            String superPartId = body.getPloy().getId() + "";
            Cursor cursor = db.query("bodypart", BodyPart.tableColumns, "sex=? and superid=?", new String[] { body.sexStr, superPartId }, null, null, null);

            while (cursor.moveToNext()) {
                BodyPart bodyPart = new BodyPart();
                /**
                 * O-R映射
                 */
                bodyPart.setId(cursor.getInt(BodyPart.ID_INDEX));
                bodyPart.setName(cursor.getString(BodyPart.NAME_INDEX));
                bodyPart.setLeaf(cursor.getInt(BodyPart.LEAF_INDEX) == 1);
                bodyPart.setCoords(cursor.isNull(BodyPart.COORDS_INDEX) ? null : cursor.getString(BodyPart.COORDS_INDEX));
                partList.add(bodyPart);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取身体部位列表出错");
        }
        return partList;
    }

    /***************************** 本地数据记录处理部分，历史纪录等 **************************************/
    @Override
    public void addHistoryRecord(HistoryRecord history) throws Exception {
        try {
            SQLiteDatabase db = RecordSQLiteHelper.instance().getWritableDatabase();
            ContentValues valueMaps = new ContentValues();
            Body body = history.getBody();
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.DATE_INDEX], history.getDate().getTime());
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.SEX_INDEX], body.sexStr);
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.MAINSYM_INDEX], generateDataBaseString(body.getMainSym()));
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.FREQSYM_INDEX], generateDataBaseString(body.getFreqSym()));
            List<ShortBasicInfo> subSyms = body.getSubSyms();
            String syms = "";
            for (ShortBasicInfo subSym : subSyms) {
                syms += generateDataBaseString(subSym) + OBJECT_SPLIT_MASK;
            }
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.SUBSYMS_INDEX], syms);
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.ILL_INDEX], generateDataBaseString(body.getIll()));
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.DEPART_INDEX], body.getDepart().toString());
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.ISWILLING_INDEX], history.getIsWilling() ? 1 : 0);
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.ACTIVE_INDEX], history.getActive() ? 1 : 0);

            /**
             * 执行添加数据
             */
            long rowid = db.insert("historyrecord", null, valueMaps);
            history.setId(rowid);
        } catch (Exception e) {
            throw new Exception("增加历史记录出错");
        }
    }

    /**
     * sql中的逗号分隔符
     */
    private static final String SQL_SPLIT_MASK = ",";
    private static final String OBJECT_SPLIT_MASK = ";";

    /**
     * ShortBasicInfo生成存储字符串
     * @param info
     * @return
     */
    private String generateDataBaseString(ShortBasicInfo info) {
        return info.Key_no + SQL_SPLIT_MASK + info.Cvocable;
    }

    @Override
    public List<HistoryRecord> getHistoryRecordList(String tag) {
        String condition = "active = 1";
        if (tag.equals("UPLOAD")) {
            condition += " and serverid is null";
        }
        if (tag.equals("WILLING")) {
            condition += " and iswilling = 1";
        }
        List<HistoryRecord> historyRecordList = new ArrayList<HistoryRecord>();
        try {
            SQLiteDatabase db = RecordSQLiteHelper.instance().getReadableDatabase();
            Cursor cursor = db.query("historyrecord", HistoryRecord.tableColumns, condition, null, null, null, null);

            while (cursor.moveToNext()) {
                HistoryRecord record = new HistoryRecord();
                /**
                 * O-R映射
                 */
                Body body = new Body(cursor.getString(HistoryRecord.SEX_INDEX));
                ShortBasicInfo symOrIll = parseDataBaseString(cursor.getString(HistoryRecord.MAINSYM_INDEX));
                body.setMainSym(symOrIll);
                symOrIll = parseDataBaseString(cursor.getString(HistoryRecord.FREQSYM_INDEX));
                body.setFreqSym(symOrIll);
                symOrIll = parseDataBaseString(cursor.getString(HistoryRecord.ILL_INDEX));
                body.setIll(symOrIll);
                Depart depart = new Depart();
                depart.setName(cursor.getString(HistoryRecord.DEPART_INDEX));
                body.setDepart(depart);

                String subSymsStr = cursor.getString(HistoryRecord.SUBSYMS_INDEX);
                String[] subSymsStrs = subSymsStr.split(OBJECT_SPLIT_MASK);
                List<ShortBasicInfo> subSymList = new ArrayList<ShortBasicInfo>();
                for (int i = 0; i < subSymsStrs.length; i++) {
                    if (subSymsStrs[i] == null && subSymsStrs[i].equals("")) {
                        break;
                    }
                    symOrIll = parseDataBaseString(subSymsStrs[i]);
                    subSymList.add(symOrIll);
                }
                body.setSubSyms(subSymList);

                record.setServerId(cursor.isNull(HistoryRecord.SERVERID_INDEX) ? null : cursor.getString(HistoryRecord.SERVERID_INDEX));
                record.setBody(body);
                record.setId(cursor.getLong(HistoryRecord.RECORDID_INDEX));
                record.setActive(cursor.getInt(HistoryRecord.ACTIVE_INDEX) == 1);
                record.setIsWilling(cursor.getInt(HistoryRecord.ISWILLING_INDEX) == 1);
                record.setDate(new Date(cursor.getLong(HistoryRecord.DATE_INDEX)));

                historyRecordList.add(record);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "获取历史记录出错");
        }
        return historyRecordList;
    }

    /**
     * 解析字符串为ShortBasicInfo
     * @param dataBaseStoringString
     * @return
     * @throws Exception
     */
    private ShortBasicInfo parseDataBaseString(String dataBaseStoringString) throws Exception {
        ShortBasicInfo info = new ShortBasicInfo();
        String[] values = dataBaseStoringString.split(SQL_SPLIT_MASK);
        if (values.length != 2) {
            throw new Exception("dbString format is not effective!");
        } else {
            try {
                info.Key_no = Integer.parseInt(values[0]);
                info.Cvocable = values[1];
            } catch (Exception e) {
                throw new Exception("dbString value(s) is(are) not effective!");
            }
        }
        return info;
    }

    @Override
    public void updateHistoryRecord(HistoryRecord history) throws Exception {
        try {
            SQLiteDatabase db = RecordSQLiteHelper.instance().getWritableDatabase();
            ContentValues valueMaps = new ContentValues();
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.ISWILLING_INDEX], history.getIsWilling() ? 1 : 0);
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.ACTIVE_INDEX], history.getActive() ? 1 : 0);
            valueMaps.put(HistoryRecord.tableColumns[HistoryRecord.SERVERID_INDEX], history.getServerId());

            db.update("historyrecord", valueMaps, "rowid=?", new String[] { history.getId().toString() });
        } catch (Exception e) {
            throw new Exception("更新历史记录出错");
        }
    }
}
