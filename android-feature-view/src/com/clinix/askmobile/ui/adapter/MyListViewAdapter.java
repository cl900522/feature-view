package com.clinix.askmobile.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ListView的适配器，用于生成，此处是为了解决listView中含有CheckBox时，会多选的bug
 * @author SipingWork
 */
public class MyListViewAdapter extends BaseAdapter implements MainAdapterInterface {
    private int[] mTo;
    private String[] mFrom;

    List<Map<String, Object>> mData;
    List<View> viewList = new ArrayList<View>();
    private LayoutInflater inflater;
    private int resource;
    private ViewGroup parent;

    public MyListViewAdapter(ViewGroup viewGroup, List<Map<String, Object>> data, int resource, String[] from, int[] to) {
        this.inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (data != null) {
            this.mData = data;
        } else {
            this.mData = new ArrayList<Map<String, Object>>();
        }
        this.mFrom = from;
        this.mTo = to;
        this.parent = viewGroup;
        this.resource = resource;

        /**
         * init all views
         */
        for (int i = 0; i < mData.size(); i++) {
            View view = createViewFromResource(i, null, parent, this.resource);
            viewList.add(view);
        }
    }

    public void addData(Map<String, Object> data) {
        if (mData == null) {
            mData = new ArrayList<Map<String, Object>>();
        }
        this.mData.add(data);
        int position = mData.size() - 1;
        View view = createViewFromResource(position, null, this.parent, this.resource);
        viewList.add(view);
    }

    public void remove(int postion) {
        mData.remove(postion);
        viewList.remove(postion);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != viewList.get(position)) {
            return viewList.get(position);
        } else {
            return convertView;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        View v = inflater.inflate(resource, parent, false);
        bindView(position, v);
        return v;
    }

    private void bindView(int position, View view) {
        final Map dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }

        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }

                boolean bound = false;

                if (!bound) {
                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the
                            // bottom of these
                            // ifs since a lot of views are TextViews (e.g.
                            // CheckBoxes).
                            setViewText((TextView) v, text);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() + " should be bound to a Boolean, not a " + (data == null ? "<unknown type>" : data.getClass()));
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the
                        // bottom of these
                        // ifs since a lot of views are TextViews (e.g.
                        // CheckBoxes).
                        setViewText((TextView) v, text);
                    } else if (v instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " + " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
        }
    }

    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            v.setImageURI(Uri.parse(value));
        }
    }
}
