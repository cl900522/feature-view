package com.clinix.askmobile.ui.activity;

import java.io.Serializable;
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
import com.clinix.askmobile.core.bean.ShortIllInfo;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;

/**
 * 科室疾病列表页面
 * @author SipingWork
 */
public class IllListActivity extends HeaderActivity {
    private ListView illViewList;
    List<ShortIllInfo> illList;
    private OnItemClickListener itemClickListener = new ItemClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ills);
        super.onCreate(savedInstanceState);
        illViewList = (ListView) this.findViewById(R.id.activity_ills_viewlist);
        initHeaderView();

        new IllListAsyncTask().execute("Loading depart ills");
    }

    public void initHeaderView() {
        try {
            super.initHeaderView();
            Body body = AskMobileApplication.instance().getBody();
            this.title.setText(body.getDepart().toString());
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 查询科室的疾病列表，并刷新UI
     * @author SipingWork
     */
    private class IllListAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            Body body = AskMobileApplication.instance().getBody();
            illList = service.getIllsByDepartId(body.getDepart().getName());

            List<Map<String, Object>> listAdapterMap = new ArrayList<Map<String, Object>>();
            for (ShortIllInfo ill : illList) {
                Map<String, Object> bodyMap = new HashMap<String, Object>();
                bodyMap.put(MainAdapterInterface.ITEMTEXT, ill.toString());
                listAdapterMap.add(bodyMap);
            }

            return listAdapterMap;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            SimpleAdapter adapter = new SimpleAdapter(illViewList.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_directlink_textview });
            illViewList.setAdapter(adapter);
            illViewList.setOnItemClickListener(itemClickListener);
        }
    };

    /**
     * ItemList的点击事件监听器，保存数据并跳转页面
     * @author SipingWork
     */
    private class ItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Body body = AskMobileApplication.instance().getBody();
            body.setIll(illList.get(position));

            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put(MainActivity.PARAMKEY_ISDIAGNOSIS, Boolean.FALSE);

            jump2Activity(IllDetailActivity.class, params);
        }
    }
}
