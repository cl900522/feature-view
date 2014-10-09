package com.clinix.askmobile.ui.fragment;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clinix.askmobile.res.R;

/**
 * Fragment的虚拟父类，最好所有的Fragment都继承该类，便于以统一增加新的功能
 * @author SipingWork
 */
public abstract class MainFragmen extends Fragment {
    protected View rootView;
    protected Button preButton;
    protected Button nexButton;
    protected TextView title;

    /**
     * 初始化页面头变量
     * @param rootView
     * @throws Exception
     */
    public void initHeader() throws Exception {
        try {
            this.preButton = (Button) rootView.findViewById(R.id.merge_header_pre);
            this.nexButton = (Button) rootView.findViewById(R.id.merge_header_next);
            this.title = (TextView) rootView.findViewById(R.id.merge_header_title);
            this.preButton.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            throw new Exception("Loading Header failed, Please include merge_header.xml ONLY ONCE into you layout file!");
        }
    }
}
