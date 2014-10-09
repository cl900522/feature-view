package com.clinix.askmobile.ui.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.clinix.askmobile.res.R;

/**
 * 带有导航条的页面父类，请确保layout文件include merge_navigation.xml文件
 * @author SipingWork
 */
public abstract class NavigationActivity extends MainActivity {
    protected Button selfCheckButton;
    protected Button departButton;
    protected Button historyButton;
    protected Button consultButton;
    protected OnClickListener selfCheckListenr = new NullOnClickListener();
    protected OnClickListener departCheckListenr = new NullOnClickListener();
    protected OnClickListener historyCheckListenr = new NullOnClickListener();
    protected OnClickListener consultingCheckListenr = new NullOnClickListener();

    /**
     * 初始化导航条
     * @throws Exception
     */
    protected void initNavigationBar() throws Exception {
        try {
            selfCheckButton = (Button) findViewById(R.id.activity_index_button);
            departButton = (Button) findViewById(R.id.activity_depart_button);
            historyButton = (Button) findViewById(R.id.activity_history_button);
            consultButton = (Button) findViewById(R.id.activity_help_button);

            selfCheckButton.setOnClickListener(selfCheckListenr);
            departButton.setOnClickListener(departCheckListenr);
            historyButton.setOnClickListener(historyCheckListenr);
            consultButton.setOnClickListener(consultingCheckListenr);
        } catch (Exception e) {
            throw new Exception("Loading Header failed, Please include merge_navigation.xml ONLY ONECE into you layout file, and this function must be"
                + "called after calling setContentView(R.layout.xxx) to make sure the resource is loaded!");
        }
    }

    /**
     * 什么也不做的ClickListener
     * @author SipingWork
     */
    private class NullOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
        }
    }

}
