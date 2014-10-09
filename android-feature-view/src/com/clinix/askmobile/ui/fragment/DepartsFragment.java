package com.clinix.askmobile.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.Depart;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.activity.IllListActivity;
import com.clinix.askmobile.ui.activity.MainActivity;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;

/**
 * 科室页面的Fragment类
 * @author SipingWork
 */
public class DepartsFragment extends MainFragmen {
    private ListView departViewList;
    List<Depart> departList;
    private OnItemClickListener itemClickListener = new ItemClickListener();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_departs, container, false);
        departViewList = (ListView) rootView.findViewById(R.id.fragment_departs_viewlist);

        try {
            initHeader();
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }

        new DepartsAsyncTask().execute("Loading departlist!");
        return rootView;
    }

    /**
     * 内部数据同步类，用于载入科室数据并加载到页面
     * @author SipingWork
     */
    private class DepartsAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            departList = service.getDepartList();

            List<Map<String, Object>> listAdapterMap = new ArrayList<Map<String, Object>>();
            for (Depart depart : departList) {
                Map<String, Object> deptMap = new HashMap<String, Object>();
                deptMap.put(MainAdapterInterface.ITEMTEXT, depart.toString());
                listAdapterMap.add(deptMap);
            }
            return listAdapterMap;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            SimpleAdapter adapter = new SimpleAdapter(departViewList.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_directlink_textview });
            departViewList.setAdapter(adapter);

            departViewList.setOnItemClickListener(itemClickListener);
        }
    }

    /**
     * ItemList的点击事件监听器，保存数据并跳转页面
     * @author SipingWork
     */
    private class ItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Body body = AskMobileApplication.instance().getBody();
            body.setDepart(departList.get(position));

            MainActivity activity = (MainActivity) view.getContext();
            activity.jump2Activity(IllListActivity.class);
        }
    }

    public void initHeader() throws Exception {
        super.initHeader();
        this.title.setText(R.string.app_departs);
    }

}
