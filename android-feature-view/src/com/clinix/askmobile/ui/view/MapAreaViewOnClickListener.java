package com.clinix.askmobile.ui.view;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.ui.activity.BodyActivity;
import com.clinix.askmobile.ui.activity.MainActivity;
import com.clinix.askmobile.ui.activity.MainSymActivity;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * MapAreaView的点击事件处理，避免轻微的Touch就跳转页面的bug
 * @author SipingWork
 */
public class MapAreaViewOnClickListener implements OnClickListener {
    @Override
    public void onClick(View v) {
        MapAreaView param = (MapAreaView) v;
        MapArea selected = param.getSelectedArea();
        if (selected == null) {
            /**
             * 没有选中区域则返回
             */
            return;
        }

        /**
         * 保存参数并跳转相应的Activity页面
         */
        if (selected != null) {
            MainActivity activity = (MainActivity) (param.getContext());
            AskMobileApplication.instance().getBody().reSet(param.getSEX_TAG());
            if (selected.getBodyPart().isLeaf()) {
                /**
                 * 跳转主要症状
                 */
                AskMobileApplication.instance().getBody().setPart(selected.getBodyPart());
                activity.jump2Activity(MainSymActivity.class);
            } else {
                /**
                 * 跳转主要子级部位列表
                 */
                AskMobileApplication.instance().getBody().setPloy(selected.getBodyPart());
                activity.jump2Activity(BodyActivity.class);
            }
        }
        param.clearArea();
        param.invalidate();
    }
}
