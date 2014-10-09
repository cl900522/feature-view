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
import com.clinix.askmobile.core.bean.BodyPart;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;

/**
 * 身体部位页面
 * @author SipingWork
 */
public class BodyActivity extends HeaderActivity {
    private ListView bodyListView;
    private List<BodyPart> bodyPartList;
    private OnItemClickListener itemClickListener = new ItemClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_body);
        super.onCreate(savedInstanceState);
        bodyListView = (ListView) this.findViewById(R.id.activity_body_partlist);
        initHeaderView();

        new BodyPartAsyncTask().execute("Loading body parts");
    }

    @Override
    public void initHeaderView() {
        try {
            super.initHeaderView();
            this.title.setText(R.string.app_bodypart);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 载入身体部位并刷新列表数据
     * @author SipingWork
     */
    private class BodyPartAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            bodyPartList = service.getBodyPartList(AskMobileApplication.instance().getBody());

            List<Map<String, Object>> listAdapterMap = new ArrayList<Map<String, Object>>();
            for (BodyPart bodyPart : bodyPartList) {
                Map<String, Object> bodyMap = new HashMap<String, Object>();
                bodyMap.put(MainAdapterInterface.ITEMTEXT, bodyPart.getName());
                listAdapterMap.add(bodyMap);
            }
            return listAdapterMap;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            SimpleAdapter adapter = new SimpleAdapter(bodyListView.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_directlink_textview });
            bodyListView.setAdapter(adapter);
            bodyListView.setOnItemClickListener(itemClickListener);
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
            body.setPart(bodyPartList.get(position));
            MainActivity activity = (MainActivity) view.getContext();
            activity.jump2Activity(MainSymActivity.class);
        }
    }
}
