package com.clinix.askmobile.ui.adapter;

/**
 * ListView的适配器类的父类，如果要重写某些适配器，可以从该类继承 现在主要提供一些字段用于标记Item
 * @author SipingWork
 */
public interface MainAdapterInterface {
    /**
     * ListViewAdapter使用的Text字段标记
     */
    public static final String ITEMTEXT = "ItemText";
    /**
     * ListViewAdapter使用的副标题
     */
    public static final String ITEMSUBTEXT = "ItemSubText";
    /**
     * ListViewAdapter使用的图像字段标记
     */
    public static final String ITEMIMAGE = "ItemImage";
}
