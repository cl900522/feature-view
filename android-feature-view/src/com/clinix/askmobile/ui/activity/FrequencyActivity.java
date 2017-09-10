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
 * 发病情况页面
 * @author SipingWork
 */
public class FrequencyActivity extends HeaderActivity {
    private ListView freqListView;
    List<ShortSymInfo> freqSymList;
    private OnItemClickListener itemClickListener = new ItemClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_frequency);
        super.onCreate(savedInstanceState);
        freqListView = (ListView) this.findViewById(R.id.activity_frequency_viewlist);
        initHeaderView();

        new FrequencyAsyncTask().execute("Loding freqSym data");
    }

    @Override
    public void initHeaderView() {
        try {
            super.initHeaderView();
            this.title.setText(R.string.app_frequency);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 刷新发病情况的列表，并更新到ui控件中
     * @author SipingWork
     */
    private class FrequencyAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            freqSymList = service.SymToFB(AskMobileApplication.instance().getBody());

            List<Map<String, Object>> adapterMapList = new ArrayList<Map<String, Object>>();
            for (ShortSymInfo freqSym : freqSymList) {
                Map<String, Object> symMap = new HashMap<String, Object>();
                symMap.put(MainAdapterInterface.ITEMTEXT, freqSym.toString());
                adapterMapList.add(symMap);
            }

            return adapterMapList;
        }

        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            SimpleAdapter adapter = new SimpleAdapter(freqListView.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_directlink_textview });
            freqListView.setAdapter(adapter);
            freqListView.setOnItemClickListener(itemClickListener);
        }
    }

    /**
     * ItemList的点击事件监听器，保存数据并跳转页面
     * @author SipingWork
     */
    private class ItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShortSymInfo freqSym = freqSymList.get(position);
            Body body = AskMobileApplication.instance().getBody();
            body.setFreqSym(freqSym);
            MainActivity activity = (MainActivity) view.getContext();
            activity.jump2Activity(SubSymActivity.class);
        }
    }
}
