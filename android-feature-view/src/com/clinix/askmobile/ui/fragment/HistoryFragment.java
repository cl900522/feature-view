package com.clinix.askmobile.ui.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.activity.IllDetailActivity;
import com.clinix.askmobile.ui.activity.MainActivity;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;

/**
 * 收藏页面的Fragment类
 * @author SipingWork
 */
public class HistoryFragment extends MainFragmen {
    private ListView historyListView;
    List<HistoryRecord> historyList;
    SimpleAdapter adapter;
    List<Map<String, Object>> listAdapterMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_departs, container, false);
        historyListView = (ListView) rootView.findViewById(R.id.fragment_departs_viewlist);
        historyListView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);

        try {
            initHeader();
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }

        new HistoryAsyncTask().execute("Loading historylist!");
        historyListView.setOnItemLongClickListener(new HistoryItemLongClickListener());
        historyListView.setOnItemClickListener(new HistoryItemClickListener());
        return rootView;
    }

    /**
     * 内部数据同步类，用于载入收藏数据并加载到页面
     * @author SipingWork
     */
    private class HistoryAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            historyList = service.getHistoryRecordList("WILLING");

            listAdapterMap = new ArrayList<Map<String, Object>>();
            for (HistoryRecord record : historyList) {
                Map<String, Object> recordMap = new HashMap<String, Object>();
                recordMap.put(MainAdapterInterface.ITEMTEXT, record.toString());
                listAdapterMap.add(recordMap);
            }
            return listAdapterMap;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            adapter = new SimpleAdapter(historyListView.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_directlink_textview });
            historyListView.setAdapter(adapter);
        }
    }

    public void initHeader() throws Exception {
        super.initHeader();
        this.title.setText(R.string.app_favourite);
    }

    /**
     * ItemList的点击事件监听器，保存数据并跳转页面
     * @author SipingWork
     */
    private class HistoryItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            HistoryRecord history = historyList.get(position);
            params.put(MainActivity.PARAMKEY_HISTORYRECORD, history);

            AskMobileApplication.instance().setBody(history.getBody());
            MainActivity activity = (MainActivity) view.getContext();
            activity.jump2Activity(IllDetailActivity.class, params);
        }
    }

    private class HistoryItemLongClickListener implements OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            final Button deleteButton = (Button) view.findViewById(R.id.layout_directlink_button);
            if (deleteButton.getVisibility() != View.VISIBLE) {
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                deleteButton.setVisibility(View.GONE);
                return true;
            }

            final int index = position;
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MedicalSer service = MedicalSerImpl.instance();
                                HistoryRecord history = historyList.get(index);
                                history.setActive(false);
                                try {
                                    service.updateHistoryRecord(history);
                                } catch (Exception e) {
                                    Log.e(this.getClass().toString(), "Fail to delete historyrecord");
                                }
                            }
                        }).start();
                        deleteButton.setVisibility(View.GONE);
                        listAdapterMap.remove(index);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e(this.getClass().toString(), e.getMessage());
                    }
                }
            });
            return true;
        }
    }

}
