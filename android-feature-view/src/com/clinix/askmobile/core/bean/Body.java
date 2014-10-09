package com.clinix.askmobile.core.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 身体信息
 * @author Mx
 */
public class Body implements Serializable {
    public static final String WOMAN_SEX_STR = "wm";
    public static final String MAN_SEX_STR = "m";
    public static final String CHILD_SEX_STR = "c";
    /**
     * 年龄，1大人，2小孩
     */
    public int age;
    /**
     * 性别，1男，2女
     */
    public int sex;

    /**
     * 性别字符串
     */
    public String sexStr;
    /**
     * 上级部位
     */
    private BodyPart ploy;
    /**
     * 部位
     */
    private BodyPart part;

    public BodyPart getPloy() {
        return ploy;
    }

    public void setPloy(BodyPart ploy) {
        this.ploy = ploy;
    }

    public BodyPart getPart() {
        return part;
    }

    public void setPart(BodyPart part) {
        this.part = part;
    }

    private ShortBasicInfo mainSym;
    private ShortBasicInfo freqSym;
    private List<ShortBasicInfo> subSyms;
    private Depart depart;
    private ShortBasicInfo ill;

    public Body(int age, int sex) {
        this.age = age;
        this.sex = sex;
        ploy = new BodyPart();
    }

    public Body(String sexString) {
        reSet(sexString);
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public ShortBasicInfo getIll() {
        return ill;
    }

    public void setIll(ShortBasicInfo ill) {
        this.ill = ill;
    }

    public ShortBasicInfo getMainSym() {
        return mainSym;
    }

    public void setMainSym(ShortBasicInfo mainSym) {
        this.mainSym = mainSym;
    }

    public ShortBasicInfo getFreqSym() {
        return freqSym;
    }

    public void setFreqSym(ShortBasicInfo freqSym) {
        this.freqSym = freqSym;
    }

    public List<ShortBasicInfo> getSubSyms() {
        return subSyms;
    }

    public void setSubSyms(List<ShortBasicInfo> subSyms) {
        this.subSyms = subSyms;
    }

    /**
     * 重置性别信息
     * @param sexString
     */
    public void reSet(String sexString) {
        ploy = new BodyPart();
        sexStr = sexString;
        if (sexString.equals(CHILD_SEX_STR)) {
            age = 2;
            sex = 1;
        } else if (sexString.equals(WOMAN_SEX_STR)) {
            age = 1;
            sex = 2;
        } else if (sexString.equals(MAN_SEX_STR)) {
            age = 1;
            sex = 1;
        } else {
            try {
                throw new Exception("Wrong sexString parameter!");
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取调用service的ids
     * @return
     */
    public int[] getIds() {
        int selectedCount = 0;
        if (mainSym != null) {
            selectedCount++;
        }
        if (freqSym != null) {
            selectedCount++;
        }
        if (subSyms != null) {
            selectedCount += subSyms.size();
        }
        if (selectedCount == 0) {
            return new int[0];
        }
        int[] ids = new int[selectedCount];

        if (mainSym != null) {
            ids[0] = mainSym.Key_no;
        }
        if (freqSym != null) {
            ids[1] = freqSym.Key_no;
        }
        if (subSyms != null) {
            for (int i = 0; i < subSyms.size(); i++) {
                ids[i + 2] = subSyms.get(i).Key_no;
            }
        }
        return ids;
    }

    public String toString() {
        int[] ids = getIds();
        String ills = "";
        for (int i = 0; i < ids.length; i++) {
            ills += ids[i] + ",";
        }
        return "sexStr:[" + sexStr + "]age:[" + age + "]sex:[" + sex + "]ills:[" + ills + "]";
    }

}
