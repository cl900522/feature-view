package com.clinix.askmobile.core.bean;

import java.io.Serializable;

/**
 * 诊断疾病的临时概率信息
 * @author Mx
 */
public class SymCountAndRate implements Serializable {
    private Integer symRate;
    private Integer symMRate;
    private Integer symCount;
    private double xzValue;
    private Integer ifMain;

    public Integer getIfMain() {
        return ifMain;
    }

    public void setIfMain(Integer ifMain) {
        this.ifMain = ifMain;
    }

    public double getXzvalue() {
        return xzValue;
    }

    public void setXzvalue(double xzvalue1) {
        this.xzValue = xzvalue1;
    }

    public Integer getSymRate() {
        return symRate;
    }

    public void setSymRate(Integer symRate) {
        this.symRate = symRate;
    }

    public Integer getSymMRate() {
        return symMRate;
    }

    public void setSymMRate(Integer symMRate) {
        this.symMRate = symMRate;
    }

    public Integer getSymCount() {
        return symCount;
    }

    public void setSymCount(Integer symCount) {
        this.symCount = symCount;
    }

    public Integer getTotalRate() {
        return symRate + symMRate;
    }

    public SymCountAndRate() {
        this.symRate = 0;
        this.symMRate = 0;
        this.symCount = 0;
        this.xzValue = 0.0;
        this.ifMain = 2;
    }
}
