package com.nexuslink.govermanage.event_fragment;

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
import com.nexuslink.govermanage.bean.EventModel;
import com.nexuslink.govermanage.R;
import com.nexuslink.govermanage.adapter.EventUndisposedRecycler;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.EventBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ProcessedFragment extends Fragment {

    private List<EventModel> events;
    private RecyclerView eventsRecycler;
    private List<EventBean.DataBean> eventList;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_undisposed, container, false);
        events = new ArrayList<>();
        eventList = new ArrayList<>();

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //********************************************************************************************
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据获取中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpUtils.post()
                .addParams("page", "1")
                .addParams("size", "15")
                .addHeader("Authorization", pref.getString("token", ""))
                .url(Const.url + "/public/index.php/index/Work/emergencyList")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"数据获取失败,请重试", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        Log.d("UndisposedFragment2", response);
                        Gson gson = new Gson();
                        EventBean eventBean = gson.fromJson(response, EventBean.class);
                        eventList = eventBean.getData();
                        for (int i = eventList.size() - 1; i >= 0 ; i--) {
                            if (eventList.get(i).getStatus() == 3) {
                                events.add(new EventModel(String.valueOf(eventList.get(i).getStatus()), eventList.get(i).getId(), eventList.get(i).getTitle(), eventList.get(i).getIs_important(), Integer.parseInt(eventList.get(i).getEmergency_source()), eventList.get(i).getCreated_at(), eventList.get(i).getContent(), Integer.parseInt(eventList.get(i).getEmergency_type())));
                            }
                        }
                        if (events.size() == 0) Toast.makeText(getActivity(),"当前无此类型事件", Toast.LENGTH_LONG).show();
                        eventsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        eventsRecycler.setAdapter(new EventUndisposedRecycler(events));
                    }

                });
        //********************************************************************************************
        eventsRecycler = view.findViewById(R.id.recycler_undisposed);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventsRecycler.setAdapter(new EventUndisposedRecycler(events));
        return view;
    }

}
