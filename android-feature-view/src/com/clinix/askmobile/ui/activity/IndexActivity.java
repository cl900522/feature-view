package com.clinix.askmobile.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.clinix.askmobile.res.R;
import com.clinix.askmobile.ui.fragment.ConsultFragment;
import com.clinix.askmobile.ui.fragment.DepartsFragment;
import com.clinix.askmobile.ui.fragment.HistoryFragment;
import com.clinix.askmobile.ui.fragment.SelfCheckFragment;

/**
 * 首页，采用Fragments和RadioButton进行页面切换，效率高
 * @author SipingWork
 */
public class IndexActivity extends NavigationActivity {
    List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initFragments();

        try {
            selfCheckListenr = new SelfCheckListenr();
            departCheckListenr = new DepartListenr();
            historyCheckListenr = new HistoryListenr();
            consultingCheckListenr = new ConsultingListenr();
            initNavigationBar();
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }
    }

    /**
     * 初始化radio button 导航栏对应的fragmetns
     */
    private void initFragments() {
        fragments.add(new SelfCheckFragment());
        fragments.add(new DepartsFragment());
        fragments.add(new HistoryFragment());
        fragments.add(new ConsultFragment());

        FragmentManager manager = this.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_index_fragment, fragments.get(0));
        transaction.commit();
    }

    public void onBackPressed() {
    }

    private class SelfCheckListenr implements OnClickListener {
        @Override
        public void onClick(View view) {
            MainActivity activity = (MainActivity) view.getContext();
            FragmentManager manager = activity.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_index_fragment, fragments.get(0));
            transaction.addToBackStack("Change to Index Fragment");
            transaction.commit();
        }
    }

    private class DepartListenr implements OnClickListener {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) v.getContext();
            FragmentManager manager = activity.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_index_fragment, fragments.get(1));
            transaction.addToBackStack("Change to Depart Fragment");
            transaction.commit();
        }
    }

    private class HistoryListenr implements OnClickListener {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) v.getContext();
            FragmentManager manager = activity.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_index_fragment, fragments.get(2));
            transaction.addToBackStack("Change to Depart Fragment");
            transaction.commit();
        }
    }

    private class ConsultingListenr implements OnClickListener {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) v.getContext();
            FragmentManager manager = activity.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_index_fragment, fragments.get(3));
            transaction.addToBackStack("Change to Depart Fragment");
            transaction.commit();
        }
    }
}
