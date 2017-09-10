package com.clinix.askmobile.ui.activity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleAdapter;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.Depart;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.IllStruct;
import com.clinix.askmobile.core.service.MedicalSer;
import com.clinix.askmobile.core.service.impl.MedicalSerImpl;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;
import com.clinix.askmobile.ui.adapter.MyExpandableListAdapter;
import com.clinix.askmobile.webservice.WebServiceProxy;
import com.clinix.askmobile.webservice.impl.WebServiceProxyImpl;

/**
 * 诊断结果和疾病信息页面，采用body.getMainSym()是否为null判断区别 如果为null则是科室-》疾病 如果不为null则是自诊结果
 * @author SipingWork
 */
public class IllDetailActivity extends HeaderActivity {
    private ExpandableListView illInfoListView;
    private SimpleAdapter viewListHeadAdapter;
    private View viewListFooterView;
    Button saveButton;
    Button consultButton;

    HistoryRecord history;
    Boolean isDiagnosis = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
        super.onCreate(savedInstanceState);
        illInfoListView = (ExpandableListView) this.findViewById(R.id.activity_detail_illinfo);
        getParamsFromPreActivity();

        initHeaderView();
        if (isDiagnosis) {
            initExpandableListViewHeader();
            initExpandableListViewFooter();
        }

        new IllDetailAsyncTask().execute("Loading ill xml info");
    }

    @Override
    protected void getParamsFromPreActivity() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        /**
         * 查询跳转之前的页面是否传入history记录
         */
        try {
            Serializable tempHistory = bundle.getSerializable(MainActivity.PARAMKEY_HISTORYRECORD);
            if (tempHistory instanceof HistoryRecord) {
                history = (HistoryRecord) tempHistory;
                AskMobileApplication.instance().setBody(history.getBody());
            }
        } catch (Exception e) {
            Log.d(this.getClass().toString(), "Hisroty info is null");
        }
        /**
         * 确认是否为诊断结果
         */
        try {
            Serializable tempIsDiagnosis = bundle.getSerializable(MainActivity.PARAMKEY_ISDIAGNOSIS);
            if (tempIsDiagnosis instanceof Boolean) {
                isDiagnosis = (Boolean) tempIsDiagnosis;
            }
        } catch (Exception e) {
            Log.d(this.getClass().toString(), "Hisroty info is null");
        }
    }

    public void initHeaderView() {
        try {
            super.initHeaderView();
            if (isDiagnosis) {
                this.nexButton.setText(R.string.app_recall);
                this.nexButton.setVisibility(View.VISIBLE);
                this.nexButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View buttonView) {
                        Body body = AskMobileApplication.instance().getBody();
                        Log.d(this.getClass().toString(), "Body info is:" + body.toString());
                        MainActivity acitivty = (MainActivity) buttonView.getContext();
                        acitivty.jump2Activity(ReCallActivity.class);
                    }
                });
                this.title.setText(R.string.app_result);
            } else {
                Body body = AskMobileApplication.instance().getBody();
                this.nexButton.setVisibility(View.INVISIBLE);
                this.title.setText(body.getIll().toString());
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 载入ListViewHeader，即身体信息汇总包括性别、年龄、症状、疾病等
     */
    private void initExpandableListViewHeader() {
        Body body = AskMobileApplication.instance().getBody();
        List<Map<String, Object>> listAdapterMapList = parseBodyToMap(body);
        viewListHeadAdapter = new SimpleAdapter(illInfoListView.getContext(), listAdapterMapList, R.layout.layout_formrow, new String[] { MainAdapterInterface.ITEMTEXT,
                MainAdapterInterface.ITEMSUBTEXT }, new int[] { R.id.layout_formrow_title, R.id.layout_formrow_content });

        for (int i = 0; i < viewListHeadAdapter.getCount(); i++) {
            View headView = viewListHeadAdapter.getView(i, null, null);
            illInfoListView.addHeaderView(headView);
        }
    }

    /**
     * ExpandableViewList的footer按钮初始化
     */
    private void initExpandableListViewFooter() {
        try {
            LayoutInflater inlater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewListFooterView = inlater.inflate(R.layout.layout_resultoperate, null);
            saveButton = (Button) viewListFooterView.findViewById(R.id.layout_resultoperate_save);
            consultButton = (Button) viewListFooterView.findViewById(R.id.layout_resultoperate_consulting);
            /**
             * 如果传入记录，则不允许再保存
             */
            if (history != null) {
                saveButton.setClickable(false);
                saveButton.setEnabled(false);
            }

            saveButton.setOnClickListener(new SaveLocalButtonClickListenr());
            consultButton.setOnClickListener(new ConsultButtonClickListener());
            illInfoListView.addFooterView(viewListFooterView);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "Loading result operate buttons failed");
        }
    }

    /**
     * 载入疾病信息并刷新UI界面
     * @author SipingWork
     */
    private class IllDetailAsyncTask extends AsyncTask<String, List<Map<String, Object>>, IllStruct> {
        @Override
        protected IllStruct doInBackground(String... params) {
            Body body = AskMobileApplication.instance().getBody();

            MedicalSer service = MedicalSerImpl.instance();
            IllStruct illStruct = null;
            try {
                illStruct = service.getIllInfoStruct(body.getIll().Key_no);
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
            if (illStruct != null) {
                Depart depart = new Depart();
                depart.setName(illStruct.dept);
                body.setDepart(depart);
            }

            /**
             * 保存诊断记录，由于需要查询科室信息，所以不可以提前保存
             */
            if (history == null && isDiagnosis) {
                saveHistoryToLocalAndServer();
            }
            return illStruct;
        }

        @Override
        protected void onPostExecute(IllStruct result) {
            List<Map<String, Object>> inllInfoMapList = parseIllInfoToMap(result);
            ExpandableListAdapter adapter = new MyExpandableListAdapter(illInfoListView.getContext(), inllInfoMapList, R.layout.layout_expandable_group, R.layout.layout_expandable_child,
                R.id.layout_expandable_grouptextview, R.id.layout_expandable_childtextview);
            illInfoListView.setAdapter(adapter);
        }
    }

    /**
     * 保存历史纪录
     */
    private void saveHistoryToLocalAndServer() {
        Body body = AskMobileApplication.instance().getBody();
        history = new HistoryRecord();
        history.setBody(body);

        new Thread(saveHistoryLocalRunnable).start();
        new Thread(uploadHistoryServerRunnable).start();
    }

    /**
     * 通过本地数据库保存记录
     */
    private Runnable saveHistoryLocalRunnable = new Runnable() {
        @Override
        public void run() {
            MedicalSer service = MedicalSerImpl.instance();
            try {
                service.addHistoryRecord(history);
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
        }
    };
    /**
     * 通过webservice提交记录
     */
    private Runnable uploadHistoryServerRunnable = new Runnable() {
        @Override
        public void run() {
            if (history.getServerId() == null) {
                WebServiceProxy websrvice = new WebServiceProxyImpl();
                MedicalSer service = MedicalSerImpl.instance();
                try {
                    String serverId = "";
                    serverId = websrvice.uploadHistoryRecord(history);
                    history.setServerId(serverId);
                    service.updateHistoryRecord(history);
                } catch (Exception e) {
                    Log.e(getClass().toString(), e.getMessage());
                    return;
                }
            }
        }
    };

    /**
     * 保存按钮点击事件
     * @author SipingWork
     */
    private class SaveLocalButtonClickListenr implements OnClickListener {
        @Override
        public void onClick(View v) {
            MedicalSer service = MedicalSerImpl.instance();
            history.setIsWilling(true);
            try {
                service.updateHistoryRecord(history);
                showMessage(R.string.error_successtosavelocal);
                saveButton.setClickable(false);
                saveButton.setEnabled(false);
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
                showMessage(R.string.error_failtosavelocal);
            }
        }
    }

    /**
     * 咨询按钮点击事件
     * @author SipingWork
     */
    private class ConsultButtonClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (history.getServerId() != null) {
                Map<String, Serializable> params = new HashMap<String, Serializable>();
                params.put(MainActivity.PARAMKEY_HISTORYRECORD, history);

                jump2Activity(ChatActivity.class, params);
            } else {
                showMessage(R.string.error_synchronize);
            }
        }
    }

    /**
     * 将body信息转换成Map，适应Adapter显示
     */
    private List<Map<String, Object>> parseBodyToMap(Body body) {
        List<Map<String, Object>> listAdapterMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> infoMap = new HashMap<String, Object>();
        if (body.sex == 1) {
            infoMap.put(MainAdapterInterface.ITEMTEXT, "性别");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, "男");
            listAdapterMap.add(infoMap);
        }
        if (body.sex == 2) {
            infoMap.put(MainAdapterInterface.ITEMTEXT, "性别");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, "女");
            listAdapterMap.add(infoMap);
        }

        infoMap = new HashMap<String, Object>();
        if (body.age == 1) {
            infoMap.put(MainAdapterInterface.ITEMTEXT, "年龄");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, "成人");
        } else {
            infoMap.put(MainAdapterInterface.ITEMTEXT, "年龄");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, "儿童");
        }
        listAdapterMap.add(infoMap);

        if (body.getMainSym() != null) {
            infoMap = new HashMap<String, Object>();
            infoMap.put(MainAdapterInterface.ITEMTEXT, "主症");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, body.getMainSym().toString());
            listAdapterMap.add(infoMap);
        }

        if (body.getFreqSym() != null) {
            infoMap = new HashMap<String, Object>();
            infoMap.put(MainAdapterInterface.ITEMTEXT, "起病");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, body.getFreqSym().toString());
            listAdapterMap.add(infoMap);
        }

        if (body.getSubSyms() != null) {
            infoMap = new HashMap<String, Object>();
            infoMap.put(MainAdapterInterface.ITEMTEXT, "辅症");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, body.getSubSyms().toString());
            listAdapterMap.add(infoMap);
        }
        if (body.getIll() != null) {
            infoMap = new HashMap<String, Object>();
            infoMap.put(MainAdapterInterface.ITEMTEXT, "疾病");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, body.getIll().toString());
            listAdapterMap.add(infoMap);
        }
        if (body.getDepart() != null) {
            infoMap = new HashMap<String, Object>();
            infoMap.put(MainAdapterInterface.ITEMTEXT, "科室");
            infoMap.put(MainAdapterInterface.ITEMSUBTEXT, body.getDepart().toString());
            listAdapterMap.add(infoMap);
        }
        Log.d(this.getClass().toString(), listAdapterMap.toString());
        return listAdapterMap;
    }

    /**
     * 将疾病的XML信息转换成Map显示
     * @param result
     * @return
     */
    private List<Map<String, Object>> parseIllInfoToMap(IllStruct result) {
        String SOURCETAG = "item";
        String SOURCEHEAD = "title";

        List<Map<String, Object>> listAdapterMap = new ArrayList<Map<String, Object>>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String illXml = result.xml;
        try {
            InputStream inStream = new ByteArrayInputStream(illXml.getBytes());
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(inStream);
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName(SOURCETAG);
            for (int i = 0; i < items.getLength(); i++) {
                Element illNode = (Element) items.item(i);
                Map<String, Object> infoNode = new HashMap<String, Object>();
                infoNode.put(MyExpandableListAdapter.TITLE_KEY, illNode.getAttribute(SOURCEHEAD));
                infoNode.put(MyExpandableListAdapter.CONTENT_KEY, illNode.getTextContent());
                listAdapterMap.add(infoNode);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
        return listAdapterMap;
    }

}
