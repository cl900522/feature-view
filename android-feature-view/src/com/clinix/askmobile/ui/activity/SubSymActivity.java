package com.clinix.askmobile.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.ShortBasicInfo;
import com.clinix.askmobile.core.bean.ShortSymInfo;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;
import com.clinix.askmobile.ui.adapter.MyListViewAdapter;

/**
 * 辅助症状页面，和ReCall页面很相似，只是调用的算法不一样
 * @author SipingWork
 */
public class SubSymActivity extends HeaderActivity {
    private ListView subSymListView;
    List<ShortSymInfo> subSymList;
    /**
     * 被选中的症状列表
     */
    List<ShortBasicInfo> selectedSymList = new ArrayList<ShortBasicInfo>();
    private OnItemClickListener itemClickListener = new ItemClickListener();
    private OnClickListener diagnoseButtonListener = new DiagnoseClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_subsymptom);
        subSymListView = (ListView) this.findViewById(R.id.activity_subsymptom_listview);
        super.onCreate(savedInstanceState);

        initHeaderView();

        new SubSymAsyncTask().execute("Loading subSym data");
    }

    public void initHeaderView() {
        try {
            super.initHeaderView();
            this.nexButton.setVisibility(View.VISIBLE);
            this.nexButton.setText(R.string.app_diagnosis);
            this.title.setText(R.string.app_subsymptom);
            this.nexButton.setOnClickListener(diagnoseButtonListener);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 获取辅症列表并更新UI界面
     * @author SipingWork
     */
    private class SubSymAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            MedicalSer service = MedicalSerImpl.instance();
            subSymList = service.SymBSToZZ(AskMobileApplication.instance().getBody());

            List<Map<String, Object>> adapterListMap = new ArrayList<Map<String, Object>>();

            for (ShortSymInfo subSym : subSymList) {
                Map<String, Object> symMap = new HashMap<String, Object>();
                symMap.put(MainAdapterInterface.ITEMTEXT, subSym.toString());
                adapterListMap.add(symMap);
            }

            return adapterListMap;
        }

        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            final MyListViewAdapter adapter = new MyListViewAdapter(subSymListView, listAdapterMap, R.layout.layout_multyselect, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_multyselect_textview });
            subSymListView.setAdapter(adapter);

            subSymListView.setItemsCanFocus(false);
            subSymListView.setOnItemClickListener(itemClickListener);
        }
    }

    private class ItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.layout_multyselect_checkbox);
            Boolean checked = checkBox.isChecked();
            checkBox.setChecked(!checked);
            ShortSymInfo subSymInfo = subSymList.get(position);
            if (checked) {
                if (selectedSymList.contains(subSymInfo))
                    selectedSymList.remove(subSymInfo);
            } else {
                if (!selectedSymList.contains(subSymInfo))
                    selectedSymList.add(subSymInfo);
            }
        }
    }

    /**
     * 诊断按钮（nextbutton）点击的监听
     * @author SipingWork
     */
    private class DiagnoseClickListener implements OnClickListener {
        @Override
        public void onClick(View buttonView) {
            int selectedCount = selectedSymList.size();
            Log.d(this.getClass().toString(), "Selected count is:" + selectedCount);
            if (selectedCount == 0) {
                showMessage(R.string.error_nosubsymsselected);
                return;
            }

            Body body = AskMobileApplication.instance().getBody();
            body.setSubSyms(selectedSymList);
            Log.d(this.getClass().toString(), "Body info is:" + body.toString());
            MainActivity activity = (MainActivity) buttonView.getContext();
            activity.jump2Activity(SuspectIllActivity.class);
        }
    }
}
