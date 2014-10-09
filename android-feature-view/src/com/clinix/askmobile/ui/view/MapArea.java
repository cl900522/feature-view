package com.clinix.askmobile.ui.view;

import android.graphics.Path;
import android.util.Log;

import com.clinix.askmobile.core.bean.BodyPart;

/**
 * 区域对象类，使用Path保存存储路径
 * @author SipingWork
 */
public class MapArea {
    private BodyPart bodyPart;
    private Point[] path;
    /**
     * 缩放比例，用于将数据库的（图片的）像素值转换为设备上具体的像素点，参见toDip()函数
     */
    private float SCALE = 1;

    /**
     * 区域图像对应的人体部位
     * @return
     */
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public MapArea(BodyPart bodyPart, float scale) {
        super();
        this.SCALE = scale;
        setBodyPart(bodyPart);
    }

    /**
     * 设置身体部位，同时解析coords的坐标串
     * @param bodyPart
     */
    private void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
        String[] paths = bodyPart.getCoords().split(",");
        /**
         * 每两个点做一个坐标
         */
        try {
            int size = paths.length / 2;
            path = new Point[size];
            for (int i = 0; i < size; i++) {
                path[i] = new Point(toDip(Float.parseFloat(paths[2 * i].trim())), toDip(Float.parseFloat(paths[2 * i + 1].trim())));
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    public Path getPath() {
        Path drawPath = new Path();
        for (int i = 0; i < path.length; i++) {
            if (i != 0) {
                drawPath.lineTo(path[i].x, path[i].y);
            } else {
                drawPath.moveTo(path[i].x, path[i].y);
            }
        }
        drawPath.close();
        return drawPath;
    }

    /**
     * 检查坐标(x,y)是否被path的点连接区域包含
     * @param x
     * @param y
     * @return
     */
    public boolean contains(float x, float y) {
        boolean result = false;
        for (int i = 0, j = path.length - 1; i < path.length; j = i++) {
            if ((path[i].y < y && path[j].y >= y) || (path[j].y < y && path[i].y >= y)) {
                if (path[i].x + (y - path[i].y) / (path[j].y - path[i].y) * (path[j].x - path[i].x) < x) {
                    result = !result;
                }
            }
        }
        return result;
    }

    /**
     * 进行不同材质机器的兼容而转换像素比例
     * @param pxValue
     * @return
     */
    private float toDip(float pxValue) {
        return pxValue * SCALE;
    }

    /**
     * 表示设备上点(dip)类
     * @author SipingWork
     */
    private class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
