package com.nexuslink.govermanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.nav_fragment.IndexFragment;
import com.nexuslink.govermanage.nav_fragment.ServicesFragment;
import com.nexuslink.govermanage.nav_fragment.UserFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.edu.cqupt.loc.permission.CheckPermissionsActivity;
import okhttp3.Call;


public class NavigationActivity extends CheckPermissionsActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new IndexFragment());
                    return true;
                case R.id.navigation_dashboard:
                    replaceFragment(new ServicesFragment());
                    return true;
                case R.id.navigation_notifications:
                    replaceFragment(new UserFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        replaceFragment(new IndexFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登陆中...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Map<String, String> actMsg = new HashMap<>();
        actMsg.put("username",  pref.getString("id", ""));
        actMsg.put("password", pref.getString("pwd", ""));

        OkHttpUtils.post()
                .params(actMsg)
                .url(Const.url + "/public/index.php/index/User/login")
                .build()
                .execute(
                        new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        LoginActivity.LoginModel model =  new Gson().fromJson(response, LoginActivity.LoginModel.class);
                        if (model.getInfo().equals("success")) {
                            Log.d("success2", model.getData().getToken());
                            editor.putString("token", model.getData().getToken());
                            editor.apply();
                        }
                    }

                });

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        scheduledExecutorService.scheduleAtFixedRate(run, 10, 3000, TimeUnit.MILLISECONDS);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            OkHttpUtils.post()
                    .addHeader("Authorization", pref.getString("token", ""))
                    .addParams("lan", pref.getString("latitude", "29.53832"))
                    .addParams("lon", pref.getString("longitude", "106.613922"))
                    .url(Const.url + "/public/index.php/index/User/uploadLanLon")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.d("uploadLanLon2", "error | " + e.toString());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.d("uploadLanLon2", response);
                        }

                    });
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.commit();
    }

}
