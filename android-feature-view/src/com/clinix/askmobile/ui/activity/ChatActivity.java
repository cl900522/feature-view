package com.clinix.askmobile.ui.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.clinix.askmobile.AskMobileApplication;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.Reply;
import com.clinix.askmobile.core.bean.Subject;
import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.adapter.MainAdapterInterface;
import com.clinix.askmobile.ui.adapter.MyListViewAdapter;
import com.clinix.askmobile.webservice.WebServiceProxy;
import com.clinix.askmobile.webservice.impl.WebServiceProxyImpl;

/**
 * 聊天记录页面
 * @author SipingWork
 */
public class ChatActivity extends HeaderActivity {
    MyListViewAdapter adapter;
    private ListView chatListView;
    private EditText messageView;
    private Button sendButton;
    private Button imageButton;

    private HistoryRecord history;
    private Subject subject;

    private Date lastRereshDate = new Date();
    /**
     * 存储最新的回复数据，记得添加时清空上次的列表
     */
    private List<Reply> newReplyList = new ArrayList<Reply>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat);
        super.onCreate(savedInstanceState);
        chatListView = (ListView) this.findViewById(R.id.activity_chat_talklist);
        messageView = (EditText) this.findViewById(R.id.activity_chat_messsage);
        sendButton = (Button) this.findViewById(R.id.activity_chat_send);
        imageButton = (Button) this.findViewById(R.id.activity_chat_image);

        sendButton.setOnClickListener(new SendClickListener());
        imageButton.setOnClickListener(new GetImageListenr());
        messageView.addTextChangedListener(new MessageTextWatcher());

        getParamsFromPreActivity();
        initHeaderView();
        initChatListView();
    }

    @Override
    public void initHeaderView() {
        try {
            super.initHeaderView();
            this.title.setText(R.string.app_reply);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
        preButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subject == null) {
                    finish();
                } else {
                    jump2Activity(ConsultHistoryActivity.class);
                }
            }
        });
    }

    /**
     * 初始化聊天记录列表
     */
    private void initChatListView() {
        adapter = new MyListViewAdapter(chatListView, null, R.layout.layout_chatitem, new String[] { MainAdapterInterface.ITEMSUBTEXT, MainAdapterInterface.ITEMTEXT }, new int[] {
                R.id.layout_chatitem_date, R.id.layout_chatitem_content });

        /**
         * 加载聊天头部信息，即subjectInfo
         */
        if (history != null) {

        }
        if (subject != null) {
            createViewForSubject();
            lastRereshDate = subject.getSubmitDate();
            new Thread(getReplysRunnable).start();
        }
        chatListView.setAdapter(adapter);
    }

    @Override
    protected void getParamsFromPreActivity() {
        /**
         * 查询跳转之前的页面是否传入history记录
         */
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        try {
            Serializable tempHistory = bundle.getSerializable(MainActivity.PARAMKEY_HISTORYRECORD);
            if (tempHistory instanceof HistoryRecord) {
                history = (HistoryRecord) tempHistory;
            }
        } catch (Exception e) {
            Log.d(this.getClass().toString(), "Hisroty info is null");
        }
        /**
         * 查询跳转之前的页面是否传入subject记录
         */
        try {
            Serializable tempSubject = bundle.getSerializable(MainActivity.PARAMKEY_SUBJECT);
            if (tempSubject instanceof Subject) {
                subject = (Subject) tempSubject;
            }
        } catch (Exception e) {
            Log.d(this.getClass().toString(), "Subject info is null");
        }
    }

    /**
     * 消息输入框字长变化监听
     */
    private class MessageTextWatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Boolean isEnabledToSubmit = !messageView.getText().toString().trim().isEmpty();
            sendButton.setEnabled(isEnabledToSubmit);
            sendButton.setClickable(isEnabledToSubmit);
        }
    }

    /**
     * 发送消息按钮点击事件
     * @author SipingWork
     */
    private class SendClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            sendButton.setEnabled(false);
            if (AskMobileApplication.instance().isNetWorkAvaliable()) {
                if (subject == null) {
                    new Thread(submitSubjectRunnable).start();
                    lastRereshDate = new Date();
                    new Timer().scheduleAtFixedRate(new RefreshMessageTask(), 10000, 10000);
                } else {
                    new Thread(submitReplyRunnable).start();
                }
            } else {
                showMessage(R.string.error_networkconnect);
            }
        }
    }

    /**
     * 上传照片
     * @author SipingWork
     */
    private class GetImageListenr implements OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    }

    /************************************ 上传Subject *********************************************/
    /**
     * post()方法执行跳转更新ui的Runnable
     */
    private Handler submitHandler = new Handler();

    /**
     * 通过webservice提交记录
     */
    private Runnable submitSubjectRunnable = new Runnable() {
        @Override
        public void run() {
            WebServiceProxy webservice = new WebServiceProxyImpl();
            try {
                subject = new Subject();
                subject.setHistory(history);
                subject.setContent(messageView.getText().toString().trim());
                subject.setAsker(AskMobileApplication.instance().getUserInfo());

                String id = webservice.submitSubject(subject);
                subject.setId(Long.parseLong(id));
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
                submitHandler.post(failSubmitSubjectUiRunnable);
                return;
            }
            submitHandler.post(successSubmitSubjectUIRunnable);
        }
    };

    /**
     * 成功上传，添加Header
     */
    private Runnable successSubmitSubjectUIRunnable = new Runnable() {
        @Override
        public void run() {
            createViewForSubject();
        }
    };

    /**
     * 为Subject主题创建抬头
     */
    private void createViewForSubject() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View headView = inflater.inflate(R.layout.layout_subjectitem, null);
        TextView content = (TextView) headView.findViewById(R.id.layout_subjectitem_content);
        content.setText(subject.getContent());
        TextView date = (TextView) headView.findViewById(R.id.layout_subjectitem_date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date.setText(format.format(subject.getSubmitDate()));

        chatListView.setAdapter(null);
        chatListView.addHeaderView(headView);
        chatListView.setAdapter(adapter);
        messageView.setText("");
    }

    /**
     * 上传失败-显示消息
     */
    private Runnable failSubmitSubjectUiRunnable = new Runnable() {
        @Override
        public void run() {
            showMessage(R.string.error_synchronize);
        }
    };

    /************************************ 上传Reply *********************************************/
    /**
     * post()方法执行跳转更新ui的Runnable
     */
    private Handler submitReplyHandler = new Handler();

    /**
     * 通过webservice提交回复
     */
    private Runnable submitReplyRunnable = new Runnable() {
        @Override
        public void run() {
            reply = new Reply();
            reply.setSubject(subject);
            reply.setContent(messageView.getText().toString().trim());
            reply.setReplyer(AskMobileApplication.instance().getUserInfo());
            reply.setRead(false);
            reply.setReplyDate(new Date());
            reply.setScore(0F);

            WebServiceProxy websrvice = new WebServiceProxyImpl();
            try {
                String idStr = websrvice.submitReply(reply);
                reply.setId(Long.parseLong(idStr));
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
            submitReplyHandler.post(updateUIRunnable);
            refreshNewestMessage();
        }
    };

    private Reply reply;

    /**
     * 上传后处理
     */
    private Runnable updateUIRunnable = new Runnable() {
        @Override
        public void run() {
            if (reply.getId() == null) {
                showMessage(R.string.error_synchronize);
            } else {
                messageView.setText("");
            }
            sendButton.setEnabled(true);
        }
    };

    /************************************ 获取Replys *********************************************/
    /**
     * 获取回复消息的
     */
    private Runnable getReplysRunnable = new Runnable() {
        @Override
        public void run() {
            refreshNewestMessage();
            new Timer().scheduleAtFixedRate(new RefreshMessageTask(), 10000, 10000);
        }
    };

    /**
     * 获取消息后处理
     */
    private Runnable successGetReplysUIRunnable = new Runnable() {
        @Override
        public void run() {
            for (Reply reply : newReplyList) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                dataMap.put(MainAdapterInterface.ITEMSUBTEXT, format.format(reply.getReplyDate()));
                dataMap.put(MainAdapterInterface.ITEMTEXT, reply.getContent());

                adapter.addData(dataMap);
            }
            adapter.notifyDataSetChanged();
            chatListView.setSelection(adapter.getCount() - 1);
        }
    };

    @Override
    public void onBackPressed() {
        if (subject == null) {
            finish();
        } else {
            jump2Activity(ConsultHistoryActivity.class);
        }
    }

    /*********************************** 定时刷新消息 *******************************/

    private synchronized void refreshNewestMessage() {
        if (subject == null || subject.getId() == null) {
            return;
        }
        WebServiceProxy webservice = new WebServiceProxyImpl();
        Date replyDate = lastRereshDate;
        lastRereshDate = new Date();
        try {
            newReplyList = webservice.queryReply(subject, replyDate);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
            return;
        }
        submitHandler.post(successGetReplysUIRunnable);
    }

    private class RefreshMessageTask extends TimerTask {
        public void run() {
            synchronized (this) {
                refreshNewestMessage();
            }
        }
    }

    /***************** 图片处理 ************************/
    Bitmap uploadBitmap;

    /**
     * 获取图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (AskMobileApplication.instance().isNetWorkAvaliable()) {
                Bundle bundle = data.getExtras();
                uploadBitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                new Thread(uploadImageRunable).start();
            } else {
                showMessage(R.string.error_networkconnect);
            }
        }
    }

    /**
     * 上传后处理
     */
    private Runnable uploadImageRunable = new Runnable() {
        @Override
        public void run() {
            WebServiceProxy webservice = new WebServiceProxyImpl();
            String imagePath = "";
            try {
                imagePath = webservice.uploadImage(uploadBitmap);
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
                return;
            }

            /**
             * 更具图片创建回复信息
             */
            reply = new Reply();
            reply.setSubject(subject);
            reply.setContent(imagePath);
            reply.setReplyer(AskMobileApplication.instance().getUserInfo());
            reply.setRead(false);
            reply.setReplyDate(new Date());
            reply.setScore(0F);

            WebServiceProxy websrvice = new WebServiceProxyImpl();
            try {
                String idStr = websrvice.submitReply(reply);
                reply.setId(Long.parseLong(idStr));
            } catch (Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
            submitReplyHandler.post(updateUIRunnable);
            refreshNewestMessage();
        }
    };
}
