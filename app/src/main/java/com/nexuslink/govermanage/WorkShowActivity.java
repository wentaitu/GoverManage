package com.nexuslink.govermanage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.adapter.WorkShowAdapter;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.EventModel;
import com.nexuslink.govermanage.bean.WorkShowBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class WorkShowActivity extends AppCompatActivity {

    @BindView(R.id.work_show_back) public ImageView back;
    @BindView(R.id.work_recycler) public RecyclerView workRecycler;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private List<EventModel> events = new ArrayList<>();   // 展示简要事件列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_show);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(WorkShowActivity.this);
        progressDialog.setMessage("工作信息获取中...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Map<String, String> actMsg = new HashMap<>();
        actMsg.put("page", "1");
        actMsg.put("size", "15");

        // *************************************************************
        OkHttpUtils.post()
                .url(Const.url + "/public/index.php/index/Work/dailyWorkList")
                .addHeader("Authorization", pref.getString("token", ""))
                .params(actMsg)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        Toast.makeText(WorkShowActivity.this,"工作信息获取失败", Toast.LENGTH_LONG).show();
                        Log.d("WorkShowActivity1", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        Log.d("WorkShowActivity1", response);
                        Gson gson = new Gson();
                        WorkShowBean workShowBean = gson.fromJson(response, WorkShowBean.class);

                        List<WorkShowBean.DataBean> workList = workShowBean.getData();
                        for (int i = workList.size() - 1; i >= 0 ; i--) {
                            events.add(new EventModel("0", workList.get(i).getId(), workList.get(i).getLog_name(), 0, 0, workList.get(i).getEdit_time(), workList.get(i).getContent(), workList.get(i).getLog_type()));
                        }
                        if (events.size() == 0) Toast.makeText(WorkShowActivity.this,"当前无工作事件", Toast.LENGTH_LONG).show();
                        workRecycler.setLayoutManager(new LinearLayoutManager(WorkShowActivity.this));
                        workRecycler.setAdapter(new WorkShowAdapter(events));
                    }
                });

    }
}
