package com.clinix.askmobile.ui.fragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.Subject;
import com.clinix.askmobile.core.bean.UserInfo;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.activity.ChatActivity;
import com.clinix.askmobile.ui.activity.MainActivity;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;
import com.clinix.askmobile.webservice.WebServiceProxy;
import com.clinix.askmobile.webservice.impl.WebServiceProxyImpl;

/**
 * 发病情况页面
 * @author SipingWork
 */
public class ConsultInprogressFragment extends MainFragmen {
    private ListView consultInprogressListView;
    List<Subject> inprogressConsultList = new ArrayList<Subject>();
    private OnItemClickListener itemClickListener = new ItemClickListener();

    List<Map<String, Object>> listAdapterMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_list, container, false);
        consultInprogressListView = (ListView) rootView.findViewById(R.id.activity_list_listview);

        if (AskMobileApplication.instance().isNetWorkAvaliable()) {
            new Thread(getConsultingListRunnable).start();
        }
        return rootView;
    }

    /**
     * ItemList点击调转聊天
     * @author SipingWork
     */
    private class ItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Subject subject = inprogressConsultList.get(position);
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put(MainActivity.PARAMKEY_SUBJECT, subject);

            MainActivity activity = (MainActivity) view.getContext();
            activity.jump2Activity(ChatActivity.class, params);
        }
    }

    /**
     * 掉转前段更新UI的Runnable
     */
    private Handler dataHandler = new Handler();

    /**
     * 通过webservice获取进行中的咨询记录
     */
    private Runnable getConsultingListRunnable = new Runnable() {
        @Override
        public void run() {
            WebServiceProxy webservice = new WebServiceProxyImpl();
            UserInfo user = AskMobileApplication.instance().getUserInfo();
            try {
                inprogressConsultList = webservice.querySubject(user, "INPROGRESS");
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }

            listAdapterMap = new ArrayList<Map<String, Object>>();
            for (Subject question : inprogressConsultList) {
                Map<String, Object> subjectMap = new HashMap<String, Object>();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                subjectMap.put(MainAdapterInterface.ITEMSUBTEXT, format.format(question.getSubmitDate()));
                subjectMap.put(MainAdapterInterface.ITEMTEXT, question.getContent());
                listAdapterMap.add(subjectMap);
            }

            dataHandler.post(updateUIRunnable);
        }
    };

    /**
     * 将记录数据写入UI
     */
    private Runnable updateUIRunnable = new Runnable() {
        @Override
        public void run() {
            SimpleAdapter adapter = new SimpleAdapter(consultInprogressListView.getContext(), listAdapterMap, R.layout.layout_subjectitem, new String[] { MainAdapterInterface.ITEMSUBTEXT,
                    MainAdapterInterface.ITEMTEXT }, new int[] { R.id.layout_subjectitem_date, R.id.layout_subjectitem_content });
            consultInprogressListView.setAdapter(adapter);
            consultInprogressListView.setOnItemClickListener(itemClickListener);
        }
    };
}
