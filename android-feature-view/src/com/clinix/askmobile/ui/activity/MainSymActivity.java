package com.clinix.askmobile.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.ShortSymInfo;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;

/**
 * 主要症状页面，根据body.getPart()查询主症
 * @author SipingWork
 */
public class MainSymActivity extends HeaderActivity {
    private ListView symViewList;
    List<ShortSymInfo> mainSymList;
    private OnItemClickListener itemClickListener = new ItemClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mainsymptom);
        symViewList = (ListView) this.findViewById(R.id.activity_mainsym_viewlist);
        super.onCreate(savedInstanceState);

        initHeaderView();

        new MainSymAsyncTask().execute("loading mainsym list");
    }

    @Override
    public void initHeaderView() {
        try {
            super.initHeaderView();
            this.title.setText(R.string.app_mainsymptom);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 查询部位的主要症状并更新UI
     * @author SipingWork
     */
    private class MainSymAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            mainSymList = service.getSymListByPartId(AskMobileApplication.instance().getBody());

            List<Map<String, Object>> listAdapterMap = new ArrayList<Map<String, Object>>();
            for (ShortSymInfo mainSym : mainSymList) {
                Map<String, Object> symMap = new HashMap<String, Object>();
                symMap.put(MainAdapterInterface.ITEMTEXT, mainSym.toString());
                listAdapterMap.add(symMap);
            }

            return listAdapterMap;
        }

        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            SimpleAdapter adapter = new SimpleAdapter(symViewList.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_directlink_textview });
            symViewList.setAdapter(adapter);

            symViewList.setOnItemClickListener(itemClickListener);
        }
    }

    /**
     * ItemList的点击事件监听器，保存数据并跳转页面
     * @author SipingWork
     */
    private class ItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShortSymInfo mainSym = mainSymList.get(position);
            Body body = AskMobileApplication.instance().getBody();
            body.setMainSym(mainSym);
            Log.d(this.getClass().toString(), "Body info is:" + body.toString());
            MainActivity activity = (MainActivity) view.getContext();
            activity.jump2Activity(FrequencyActivity.class);
        }
    }
}
