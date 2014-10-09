package com.clinix.askmobile.ui.view;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 提供activty_index页面的ViewPager的对象适配器，使用List<View>作为存储对象
 * @author SipingWork
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    /**
     * 获取location位置的view
     * @param location
     * @return
     */
    public View getView(int location) {
            return views.get(location);
    }

    /**
     * 获得size
     */
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 销毁Item
     */
    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView(views.get(position));
    }

    /**
     * 实例化Item
     */
    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(views.get(position), 0);
        return views.get(position);
    }

}
