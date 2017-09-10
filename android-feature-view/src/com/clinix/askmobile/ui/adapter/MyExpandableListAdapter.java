package com.clinix.askmobile.ui.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clinix.askmobile.res.R;

/**
 * 可扩展的（可展开子集信息）的ListView适配器，继承类BaseExpandableListAdapter
 * @author SipingWork
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter implements MainAdapterInterface{
    public static final String TITLE_KEY = "title";
    public static final String CONTENT_KEY = "content";
    private int groupLayoutId;
    private int childLayoutId;
    private int groupTextId;
    private int childTextId;

    private List<Map<String, Object>> groupData;
    private LayoutInflater inflater;
    /**
     * 保存group view的列表
     */
    private View[] groupViews;
    /**
     * 保存child view的列表，理论上来说，应该是二位数组，但是项目中的子集是和groupView一一对应的，所以仍然为一维数组
     */
    private View[] childViews;

    public MyExpandableListAdapter(Context context, List<Map<String, Object>> groupData, int groupLayoutId, int childLayoutId, int groupTextId, int childTextId) {
        super();
        this.groupLayoutId = groupLayoutId;
        this.childLayoutId = childLayoutId;
        this.groupTextId = groupTextId;
        this.childTextId = childTextId;
        this.groupData = groupData;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        groupViews = new View[groupData.size()];
        childViews = new View[groupData.size()];
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = groupViews[groupPosition];
        if (view == null) {
            view = inflater.inflate(groupLayoutId, parent, false);
            groupViews[groupPosition] = view;
        }
        TextView textView = (TextView) view.findViewById(groupTextId);
        textView.setText((String) groupData.get(groupPosition).get(TITLE_KEY));
        ImageView imageView = (ImageView) view.findViewById(R.id.layout_expandable_indicatorview);
        if (isExpanded) {
            imageView.setImageResource(R.drawable.arrow_up);
        } else {
            imageView.setImageResource(R.drawable.arrow_down);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = childViews[groupPosition];
        if (view == null) {
            view = inflater.inflate(childLayoutId, parent, false);
            childViews[groupPosition] = view;
        }
        TextView textView = (TextView) view.findViewById(childTextId);
        textView.setText((String) groupData.get(groupPosition).get(CONTENT_KEY));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
