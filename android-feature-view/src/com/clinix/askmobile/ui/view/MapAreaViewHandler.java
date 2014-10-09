package com.clinix.askmobile.ui.view;

import android.os.Handler;
import android.os.Message;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.ui.activity.BodyActivity;
import com.clinix.askmobile.ui.activity.MainActivity;
import com.clinix.askmobile.ui.activity.MainSymActivity;

/**
 * MapAreaView的消息处理类,
 * 以前触碰事件处理   onTouchEvent->MapAreaViewOnClikListener->MapAreaViewHandler，此处功能被ClickListener调用
 * 现在将功能都转移到MapAreaViewOnClikListener
 * @author SipingWork
 */
@Deprecated
class MapAreaViewHandler extends Handler {
    /**
     * 执行消息处理，具体管理跳转
     */
    public void handleMessage(Message msg) {
        MapAreaView param = (MapAreaView) msg.obj;
        MapArea selected = param.getSelectedArea();

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
    };
};
