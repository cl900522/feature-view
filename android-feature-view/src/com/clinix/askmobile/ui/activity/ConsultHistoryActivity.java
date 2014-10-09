package com.clinix.askmobile.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.clinix.askmobile.res.R;

/**
 * 在线咨询记录列表
 * @author SipingWork
 */
public class ConsultHistoryActivity extends HeaderActivity {
    private static final String[] tabTitles = new String[] { "进行中", "已完成" };
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulthistory);

        initHeaderView();
        initTabHost();

    }

    @Override
    public void initHeaderView() {
        try {
            super.initHeaderView();
            this.title.setText(R.string.app_consulthistory);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
        preButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jump2Activity(IndexActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        jump2Activity(IndexActivity.class);
    }

    private void initTabHost() {
        tabHost = (TabHost) this.findViewById(android.R.id.tabhost);
        try {
            tabHost.setup();
            TabSpec spec = tabHost.newTabSpec(tabTitles[0]);
            spec.setContent(R.id.activity_consult_inprogresshistory);
            spec.setIndicator(tabTitles[0]);
            tabHost.addTab(spec);

            spec = tabHost.newTabSpec(tabTitles[1]);
            spec.setContent(R.id.activity_consult_finishedhistory);
            spec.setIndicator(tabTitles[1]);
            tabHost.addTab(spec);
            tabHost.setCurrentTab(0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().toString(), e.getMessage());
        }
        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            View child = tabWidget.getChildAt(i);
            child.setBackgroundColor(getResources().getColor(R.color.gray));

            final TextView tv = (TextView) child.findViewById(android.R.id.title);
            tv.setTextSize(20);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setBackgroundColor(getResources().getColor(R.color.gray));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0); // 取消文字底边对齐
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); // 设置文字居中对齐
        }
    }
}
