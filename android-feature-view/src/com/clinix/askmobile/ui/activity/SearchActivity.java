package com.clinix.askmobile.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.ShortSymInfo;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;

/**
 * 症状搜索页面
 * @author SipingWork
 *
 */
public class SearchActivity extends MainActivity {
    private Button cancelSearch;
    private EditText keyWordInput;
    private ListView searchResultList;
    List<ShortSymInfo> searchSymList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        cancelSearch = (Button) findViewById(R.id.activity_search_cancel);
        keyWordInput = (EditText) findViewById(R.id.activity_search_keyword);
        searchResultList = (ListView) findViewById(R.id.activity_search_resultlist);
        initSearchWidgetsListeners();
    }

    /**
     * 初始化搜索控件的监听命令
     */
    private void initSearchWidgetsListeners() {
        /**
         * 取消搜索按钮命令
         */
        cancelSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 关键字输入框监听命令--屏蔽Enter键
         */
        keyWordInput.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });
        /**
         * 关键字输入框字长变化监听
         */
        this.keyWordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                new SearchAsyncTask().execute("Getting search result");
            }
        });
    }

    /**
     * 症状关键字查询的刷新任务执行类
     * @author SipingWork
     */
    private class SearchAsyncTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            List<Map<String, Object>> listAdapterMap = new ArrayList<Map<String, Object>>();
            String keyword = keyWordInput.getText().toString().trim();
            if (keyword.equals("")) {
                return listAdapterMap;
            }

            MedicalSer service = MedicalSerImpl.instance();
            Body body = AskMobileApplication.instance().getBody();
            searchSymList = service.searchSymByKeyWord(keyword, body);

            for (ShortSymInfo mainSym : searchSymList) {
                Map<String, Object> symMap = new HashMap<String, Object>();
                symMap.put(MainAdapterInterface.ITEMTEXT, mainSym.toString());
                listAdapterMap.add(symMap);
            }

            return listAdapterMap;
        }

        protected void onPostExecute(List<Map<String, Object>> listAdapterMap) {
            SimpleAdapter adapter = new SimpleAdapter(searchResultList.getContext(), listAdapterMap, R.layout.layout_directlink, new String[] { MainAdapterInterface.ITEMTEXT },
                new int[] { R.id.layout_directlink_textview });
            searchResultList.setAdapter(adapter);

            searchResultList.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ShortSymInfo mainSym = searchSymList.get(position);
                    Body body = AskMobileApplication.instance().getBody();
                    body.setMainSym(mainSym);
                    Log.d(this.getClass().toString(), "Body info is:" + body.toString());
                    MainActivity activity = (MainActivity) view.getContext();
                    activity.jump2Activity(FrequencyActivity.class);
                }
            });
        }
    }
}
