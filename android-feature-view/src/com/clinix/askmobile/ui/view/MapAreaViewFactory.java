package com.clinix.askmobile.ui.view;

import java.util.List;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.BodyPart;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;

/**
 * 创建MapAreaView的工厂类，可创建WOMAN,MAN,CHILD三种MapAreaView
 * @author SipingWork
 */
public class MapAreaViewFactory {
    private LayoutInflater inflater;

    public MapAreaViewFactory(ViewGroup group, LayoutInflater inflater) {
        this.inflater = inflater;
    }

    /**
     * 工厂方法
     * @param sex 性别字符，参见Body类中的三个性别量WOMAN_SEX_STR，MAN_SEX_STR，CHILD_SEX_STR
     * @return
     * @throws Exception
     */
    public View createView(String sex) throws Exception {

        MapAreaView areaView = (MapAreaView) inflater.inflate(R.layout.view_maparea_image, null);
        if (sex.equals(Body.WOMAN_SEX_STR)) {
            areaView.setImageResource(R.drawable.woman_body);
        } else if (sex.equals(Body.MAN_SEX_STR)) {
            areaView.setImageResource(R.drawable.man_body);
        } else if (sex.equals(Body.CHILD_SEX_STR)) {
            areaView.setImageResource(R.drawable.child_body);
        } else {
            throw new Exception("Wrong sex parameter!Fail to create ImageView");
        }

        /**
         * 使用新线程载入路径数据，避免主线程（操纵ui）的卡顿
         */
        new AsyncTask<MapAreaView, Integer, String>() {
            @Override
            protected String doInBackground(MapAreaView... params) {
                List<BodyPart> bodyPartList = MedicalSerImpl.instance().getBodyPartList(new Body(params[0].getSEX_TAG()));
                params[0].initMapArea(bodyPartList);
                return null;
            }
        }.execute(areaView);

        areaView.setSEX_TAG(sex);
        areaView.setScaleType(ImageView.ScaleType.FIT_START);
        areaView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        return areaView;
    }
}
