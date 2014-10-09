package com.clinix.askmobile.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.activity.ChatActivity;
import com.clinix.askmobile.ui.activity.ConsultHistoryActivity;
import com.clinix.askmobile.ui.activity.MainActivity;

/**
 * 咨询Fragment
 * @author SipingWork
 */
public class ConsultFragment extends MainFragmen {
    TableRow historyRow;
    TableRow createRow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_consult, container, false);
        historyRow = (TableRow) rootView.findViewById(R.id.fragment_consulting_tablerowhistory);
        createRow = (TableRow) rootView.findViewById(R.id.fragment_consulting_tablerowcreate);

        try {
            initHeader();
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }

        historyRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                activity.jump2Activity(ConsultHistoryActivity.class);
            }
        });

        createRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                activity.jump2Activity(ChatActivity.class);
            }
        });
        return rootView;
    }

    public void initHeader() throws Exception {
        super.initHeader();
        this.title.setText(R.string.app_consulting);
        this.preButton.setVisibility(View.INVISIBLE);
    }
}
