package com.clinix.askmobile.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.activity.MainActivity;
import com.clinix.askmobile.ui.activity.SearchActivity;
import com.clinix.askmobile.ui.view.MapAreaView;
import com.clinix.askmobile.ui.view.MapAreaViewFactory;
import com.clinix.askmobile.ui.view.ViewPagerAdapter;

/**
 * 自诊页面的Fragment类
 * @author SipingWork
 */
public class SelfCheckFragment extends MainFragmen {
    private List<View> viewList = new ArrayList<View>();
    private Button searchButton;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selfcheck, container, false);
        initSearchBar(rootView);
        initMapAreaView(inflater, rootView);
        return rootView;
    }

    /**
     * 初始化MapArea对应的人体图
     * @param inflater
     * @param rootView
     */
    private void initMapAreaView(LayoutInflater inflater, View rootView) {
        viewPager = (ViewPager) rootView.findViewById(R.id.fragment_selfcheck_viewpager);
        MapAreaViewFactory factory = new MapAreaViewFactory(viewPager, inflater);
        try {
            viewList.add(factory.createView(Body.WOMAN_SEX_STR));
            viewList.add(factory.createView(Body.MAN_SEX_STR));
            viewList.add(factory.createView(Body.CHILD_SEX_STR));
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "初始化人体图形失败");
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
    }

    /**
     * 搜索Bar的初始化
     * @param rootView
     */
    private void initSearchBar(View rootView) {
        searchButton = (Button) rootView.findViewById(R.id.fragment_selfcheck_tosearchbutton);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Body body = AskMobileApplication.instance().getBody();
                ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();

                MapAreaView currentView = (MapAreaView) adapter.getView(viewPager.getCurrentItem());
                body.reSet(currentView.getSEX_TAG());

                MainActivity activity = (MainActivity) view.getContext();
                activity.jump2Activity(SearchActivity.class);
            }
        });
    }

    @Override
    public void onStart() {
        for (View view : viewList) {
            ((MapAreaView) view).clearArea();
        }
        super.onStart();
    }
}
