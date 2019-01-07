package com.nexuslink.govermanage.task_fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.R;
import com.nexuslink.govermanage.adapter.TaskUndoAdapter;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.TaskRecv;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class AcceptTaskFragment extends Fragment {

    private RecyclerView eventsRecycler;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // 未处理任务列表
    List<TaskRecv.DataBean> tasks1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_undisposed, container, false);
        eventsRecycler = view.findViewById(R.id.recycler_undisposed);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("任务信息获取中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpUtils.post()
                .url(Const.url + "/public/index.php/index/Work/missionAdmitList")
                .addHeader("Authorization", pref.getString("token", ""))
                .addParams("page","1")
                .addParams("size", "15")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"任务信息获取失败", Toast.LENGTH_LONG).show();
                        Log.d("TaskProcessingActivity1", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        Log.d("TaskProcessingActivity1", "稳态2" + response);
                        TaskRecv task = new Gson().fromJson(response, TaskRecv.class);
                        if (!task.getInfo().equals("success")) {
                            Toast.makeText(getActivity(),"任务信息获取失败", Toast.LENGTH_LONG).show();
                        }
                        tasks1 = new ArrayList<>();
//                        for (int i = (task.getData().size() - 1); i >= 0; i--) {
//                            if (task.getData().get(i).getOperate_status() == 1) {
//                                tasks1.add(task.getData().get(i));
//                                Log.d("kkkkkkkkk1", task.getData().get(i).toString());
//                            }
//                        }
                        // if (!(tasks1 != null && tasks1.size() > 0)) Toast.makeText(getActivity(),"您当前无任务信息", Toast.LENGTH_LONG).show();
                        eventsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        eventsRecycler.setAdapter(new TaskUndoAdapter(tasks1));
                    }
                });
        return view;
    }

}
