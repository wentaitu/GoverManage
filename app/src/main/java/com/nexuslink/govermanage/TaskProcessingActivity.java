package com.nexuslink.govermanage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.adapter.TaskFragmentPagerAdapter;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.TaskRecv;
import com.nexuslink.govermanage.task_fragment.AcceptTaskFragment;
import com.nexuslink.govermanage.task_fragment.UndoTaskFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class TaskProcessingActivity extends AppCompatActivity {

    @BindView(R.id.task_process_back) public ImageView back;
    @BindView(R.id.tablayout_task) public TabLayout tabLayout;
    @BindView(R.id.viewPager_task) public ViewPager viewPager;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // 未处理任务列表
    List<TaskRecv.DataBean> tasks1;
    // 已受理任务列表
    List<TaskRecv.DataBean> tasks2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_processing);

        ButterKnife.bind(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        ArrayList<String> titles = new ArrayList<>();
//        titles.add("待处理任务");
//        titles.add("已处理任务");
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
//        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));

        ArrayList<String> titleDatas=new ArrayList<>();
        titleDatas.add("待处理任务");
        titleDatas.add("任务已受理");
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new UndoTaskFragment());
        fragmentList.add(new AcceptTaskFragment());
        TaskFragmentPagerAdapter myViewPageAdapter = new TaskFragmentPagerAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        viewPager.setAdapter(myViewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myViewPageAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }


}
