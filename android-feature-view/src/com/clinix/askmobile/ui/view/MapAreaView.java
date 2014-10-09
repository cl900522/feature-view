package com.clinix.askmobile.ui.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.clinix.askmobile.core.bean.BodyPart;
import com.clinix.askmobile.ui.activity.MainActivity;

/**
 * 支持图片区域点击，用于人体部位点的点击
 */
public class MapAreaView extends ImageView {
    private String SEX_TAG;
    private float DEV_SCALE = 0f;
    /**
     * 保存所有热点区域
     */
    private List<MapArea> mapAreas = new ArrayList<MapArea>();

    /**
     * 画笔及其样式设定
     */
    private static final Paint paint;

    static {
        paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setARGB(170, 0, 205, 0);
    }
    private MapArea selectedArea;

    public MapAreaView(Context context) {
        super(context);
        initScale();
        setOnClickListener(new MapAreaViewOnClickListener());
    }

    public MapAreaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScale();
        setOnClickListener(new MapAreaViewOnClickListener());
    }

    public void initScale() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity) this.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        DEV_SCALE = displayMetrics.widthPixels / 522f;
    }

    public MapArea getSelectedArea() {
        return selectedArea;
    }

    /**
     * 清空点击而选中区域
     */
    public void clearArea() {
        this.selectedArea = null;
    }

    public String getSEX_TAG() {
        return SEX_TAG;
    }

    public void setSEX_TAG(String sEX_TAG) {
        SEX_TAG = sEX_TAG;
    }

    /**
     * 调用该接口载入区域数据
     * @param bodyPartList
     */
    public void initMapArea(List<BodyPart> bodyPartList) {
        mapAreas.clear();
        for (BodyPart bodyPart : bodyPartList) {
            MapArea bodyArea = new MapArea(bodyPart, DEV_SCALE);
            mapAreas.add(bodyArea);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 如果选中区域存在，则进行触摸区域绘制
         */
        if (selectedArea != null) {
            Path path = selectedArea.getPath();
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 触碰事件处理   onTouchEvent->MapAreaViewOnClikListener->MapAreaViewHandler
     * 此处主要是标记点中的部位区域对象，并保存到 selectedArea
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isInArea = false;
        for (MapArea area : mapAreas) {
            if (area.contains(event.getX(), event.getY())) {
                isInArea = true;
                selectedArea = area;
            }
        }
        if (!isInArea) {
            selectedArea = null;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

}
