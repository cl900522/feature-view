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
import com.clinix.askmobile.core.bean.ShortIllInfo;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;

/**
 * 诊断的疑似疾病列表
 * @author SipingWork
 */
public class SuspectIllActivity extends HeaderActivity {
    private ListView suspectedIllViewList;
    List<ShortIllInfo> suspectedIllList;
    private OnItemClickListener itemClickListener = new ItemClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_suspectedill);
        suspectedIllViewList = (ListView) this.findViewById(R.id.activity_suspectedill_listview);
        super.onCreate(savedInstanceState);

        initHeaderView();

        new SuspectIllAsyncTask().execute("Loding suspectIll data");
    }

    @Override
    public void initHeaderView() {
        try {
            super.initHeaderView();
            this.title.setText(R.string.app_suspected_disease);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 诊断疑似疾病并更新UI
     * @author SipingWork
     */
    private class SuspectIllAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            suspectedIllList = service.SymTZToILL(AskMobileApplication.instance().getBody());

            List<Map<String, Object>> adapterListMap = new ArrayList<Map<String, Object>>();
            for (ShortIllInfo suspectedIll : suspectedIllList) {
                Map<String, Object> symMap = new HashMap<String, Object>();
                symMap.put(MainAdapterInterface.ITEMTEXT, suspectedIll.toString());

                int imageId = 0, rateInteger = 0;
                if (suspectedIll.Rate > 100) {
                    rateInteger = 100;
                } else {
                    rateInteger = new Double(suspectedIll.Rate / 5).intValue() * 5;
                }
                try {
                    Class<R.drawable> drawableClass = R.drawable.class;
                    imageId = drawableClass.getDeclaredField("pie_" + rateInteger).getInt(null);
                } catch (Exception e) {
                    Log.e(this.getClass().toString(), e.getMessage());
                }
                symMap.put(MainAdapterInterface.ITEMIMAGE, imageId);
                adapterListMap.add(symMap);
            }

            return adapterListMap;
        }

        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            final SimpleAdapter adapter = new SimpleAdapter(suspectedIllViewList.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT,
                    MainAdapterInterface.ITEMIMAGE }, new int[] { R.id.layout_directlink_textview, R.id.layout_directlink_imageview });
            suspectedIllViewList.setAdapter(adapter);

            suspectedIllViewList.setOnItemClickListener(itemClickListener);
        }
    }

    /**
     * ItemList的点击事件监听器，保存数据并跳转页面
     * @author SipingWork
     */
    private class ItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShortIllInfo ill = suspectedIllList.get(position);
            Body body = AskMobileApplication.instance().getBody();
            body.setIll(ill);
            Log.d(this.getClass().toString(), "Body info is:" + body.toString());
            MainActivity activity = (MainActivity) view.getContext();
            activity.jump2Activity(IllDetailActivity.class);
        }
    }
}
