package com.clinix.askmobile.ui.activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.clinix.askmobile.res.R;

/**
 * 带有Header的Activity父类，需要包含merge_header.xml文件在layout文件中
 * @author SipingWork
 */
public abstract class HeaderActivity extends MainActivity {
    protected Button preButton;
    protected Button nexButton;
    protected TextView title;

    /**
     * 初始化Page的Head部分，即标题和左右按钮
     * @throws Exception
     */
    public void initHeaderView() {
        try {
            this.preButton = (Button) this.findViewById(R.id.merge_header_pre);
            this.nexButton = (Button) this.findViewById(R.id.merge_header_next);
            this.title = (TextView) this.findViewById(R.id.merge_header_title);
            this.preButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "Loading Header failed, Please include merge_header.xml ONLY ONECE into you layout file, and this function must be"
                + "called after calling setContentView(R.layout.xxx) to make sure the resource is loaded!");
        }
    }
}
