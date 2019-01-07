package com.nexuslink.govermanage.nav_fragment;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.nexuslink.govermanage.AboutActivity;
import com.nexuslink.govermanage.LoginActivity;
import com.nexuslink.govermanage.R;
import com.nexuslink.govermanage.util.OpenFileTipDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class UserFragment extends Fragment {

    private ImageView blurImageView;
    private ImageView avatarImageView;
    private TextView username;
    private TextView userphone;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        blurImageView = view.findViewById(R.id.iv_blur);
        avatarImageView = view.findViewById(R.id.iv_avatar);

        username = view.findViewById(R.id.user_name_fra);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "功能尚未开发", Toast.LENGTH_SHORT).show();
            }
        });
        userphone = view.findViewById(R.id.user_phone_fra);
        userphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "功能尚未开发", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.me_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "信息待完善", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.update_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("数据获取...");
                progressDialog.setCancelable(true);
                progressDialog.show();

                // 更新包下载***********************************************************************
                OkHttpUtils.get()
                        .url("http://119.23.252.121/test/intelligent_community2.apk")
                        .build()
                        .execute(new FileCallBack(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).toString(),"intelligent_community.apk") {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"应用已经是最新版", Toast.LENGTH_LONG).show();
                                Log.e("info:xxxxxxxxx", "onError :" + e.getMessage());
                            }

                            @Override
                            public void inProgress(float progress, long total, int id) {
                                super.inProgress(progress, total, id);
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"开始下载", Toast.LENGTH_LONG).show();
                                Log.e("infoxxxxxxxxx","inProgress"+(int)(100*progress));
                            }

                            @Override
                            public void onResponse(File downloadFile, int id) {
                                OpenFileTipDialog.openFiles(downloadFile.getAbsolutePath(),getActivity());
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"下载成功，已存入Download目录下", Toast.LENGTH_LONG).show();
                                Log.e("infoxxxxxxxxx", "onResponse :" + downloadFile.getAbsolutePath());
                            }
                        });

                // *****************************************************************************************************
            }
        });
        view.findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
        view.findViewById(R.id.log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = pref.edit();
                editor.clear();
                editor.apply();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(avatarImageView);
        return view;
    }

}
