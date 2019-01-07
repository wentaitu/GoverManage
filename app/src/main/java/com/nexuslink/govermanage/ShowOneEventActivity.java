package com.nexuslink.govermanage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.OneEvent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ShowOneEventActivity extends AppCompatActivity {

    @BindView(R.id.back_one_event) public ImageView back_one_event;
    @BindView(R.id.one_title) public TextView title;
    @BindView(R.id.one_type) public TextView type;
    @BindView(R.id.one_level) public TextView level;
    @BindView(R.id.one_source) public TextView source;
    @BindView(R.id.one_content) public TextView content;
    @BindView(R.id.one_photo) public ImageView photo;
    @BindView(R.id.one_duban) public RadioButton duban;
    @BindView(R.id.one_not_duban) public RadioButton notDuban;
    @BindView(R.id.change_status) public TextView changeStatus;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_event);

        ButterKnife.bind(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        back_one_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final int id = getIntent().getIntExtra("id_id", 0);
        final int status = getIntent().getIntExtra("status", 0);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据获取中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpUtils.post()
                .addHeader("Authorization", pref.getString("token", ""))
                .addParams("id", String.valueOf(id))
                .url(Const.url + "/public/index.php/index/Work/emergencyDetail")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        Toast.makeText(ShowOneEventActivity.this,"数据获取失败", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        Log.d("yyyyyyyy", response);
                        OneEvent oneEvent = new Gson().fromJson(response, OneEvent.class);
                        if (oneEvent.getData().get(0).getStatus() == 2) {
                            changeStatus.setText("已处理");
                            changeStatus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(ShowOneEventActivity.this, "事件已处理",Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (oneEvent.getData().get(0).getStatus() == 1) {
                            changeStatus.setText("处理中");
                        }
                        title.setText(oneEvent.getData().get(0).getTitle());

                        switch (oneEvent.getData().get(0).getEmergency_type()) {
                            case 0:
                                type.setText("民事纠纷");
                                break;
                            case 1:
                                type.setText("市政环卫");
                                break;
                            case 2:
                                type.setText("物业管理");
                                break;
                            case 3:
                                type.setText("隐患排查");
                                break;
                            case 4:
                                type.setText("其它");
                                break;
                        }
                        switch (oneEvent.getData().get(0).getEmergency_level()) {
                            case 0:
                                level.setText("紧急");
                                break;
                            case 1:
                                level.setText("一般");
                        }
                        switch (oneEvent.getData().get(0).getEmergency_source()) {
                            case 0:
                                source.setText("群众反应");
                                break;
                            case 1:
                                source.setText("自己发现");
                                break;
                            case 2:
                                source.setText("上级安排");
                                break;
                        }

                        content.setText(oneEvent.getData().get(0).getContent());

                        if (oneEvent.getData().get(0).getPic().size() > 0 && oneEvent.getData().get(0).getPic() != null)
                            Glide.with(ShowOneEventActivity.this).load(oneEvent.getData().get(0).getPic().get(0)).error(R.drawable.ic_insert_photo).into(photo);

                        if (oneEvent.getData().get(0).getIs_important() == 0) {
                            duban.toggle();
                        } else {
                            notDuban.toggle();
                        }

                    }

                });

        //**************************************************************************************************

        editor = pref.edit();
        final Boolean admin = pref.getBoolean("is_admin", false);
        editor.apply();
        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!admin) {
                    Toast.makeText(ShowOneEventActivity.this,"无管理员权限", Toast.LENGTH_LONG).show();
                    return;
                }

                if (status == 0) {
                    progressDialog.show();
                    progressDialog.setMessage("事件状态修改中...");
                    progressDialog.setCancelable(false);
                    OkHttpUtils.post()
                            .addHeader("Authorization", pref.getString("token", ""))
                            .addParams("id", String.valueOf(id))
                            .url(Const.url + "/public/index.php/index/Work/emergencyAdmit")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ShowOneEventActivity.this ,"事件处理状态修改失败", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ShowOneEventActivity.this,"事件处理状态已修改", Toast.LENGTH_LONG).show();
                                }

                            });
                } else if (status == 1) {
                    progressDialog.show();
                    progressDialog.setMessage("事件状态修改中...");
                    progressDialog.setCancelable(false);
                    OkHttpUtils.post()
                            .addHeader("Authorization", pref.getString("token", ""))
                            .addParams("id", String.valueOf(id))
                            .url(Const.url + "/public/index.php/index/Work/emergencyFinish")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ShowOneEventActivity.this ,"事件处理状态修改失败", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ShowOneEventActivity.this,"事件处理状态已修改", Toast.LENGTH_LONG).show();
                                }

                            });
                } else {
                    Toast.makeText(ShowOneEventActivity.this,"事件处理状态不可修改", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
