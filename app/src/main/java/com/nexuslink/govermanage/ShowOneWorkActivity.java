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
import com.nexuslink.govermanage.bean.OneWork;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ShowOneWorkActivity extends AppCompatActivity {

    @BindView(R.id.back_one_work) public ImageView back_one_work;
    @BindView(R.id.one_work_area) public TextView area;
    @BindView(R.id.one_work_grid) public TextView grid;
    @BindView(R.id.one_work_title) public TextView title;
    @BindView(R.id.one_work_type) public TextView type;
    @BindView(R.id.one_work_endtime) public TextView time;
    @BindView(R.id.one_work_content) public TextView content;
    @BindView(R.id.one_work_photo) public ImageView photo;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_work);

        ButterKnife.bind(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        back_one_work.setOnClickListener(new View.OnClickListener() {
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
                .url(Const.url + "/public/index.php/index/Work/dailyWorkDetail")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        Toast.makeText(ShowOneWorkActivity.this, "数据获取失败", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        Log.d("ShowOneWorkActivity1", "onResponse: " + response);
                        OneWork oneWork = new Gson().fromJson(response, OneWork.class);
                        area.setText(oneWork.getData().getArea() + "");
                        grid.setText(oneWork.getData().getGrid() + "");
                        title.setText(oneWork.getData().getLog_name());
                        switch (oneWork.getData().getLog_type()) {
                            case 1:
                                type.setText("巡查");
                                break;
                            case 2:
                                type.setText("走访");
                                break;
                            case 3:
                                type.setText("宣传");
                                break;
                            case 4:
                                type.setText("处理");
                                break;
                            case 5:
                                type.setText("重点人员寻访");
                                break;
                        }
                        time.setText(oneWork.getData().getEdit_time());
                        content.setText(oneWork.getData().getContent());
                        if (oneWork.getData().getPic().size() > 0 && oneWork.getData().getPic() != null)
                            Glide.with(ShowOneWorkActivity.this).load(oneWork.getData().getPic().get(0)).error(R.drawable.ic_insert_photo).into(photo);

                    }
                });

    }
}
