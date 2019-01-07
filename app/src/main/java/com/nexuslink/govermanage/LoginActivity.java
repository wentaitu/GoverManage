package com.nexuslink.govermanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.bean.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_id) public EditText loginId;
    @BindView(R.id.login_pwd) public EditText loginPwd;
    @BindView(R.id.login_forget_pwd) public TextView loginForget;
    private TextView registerBtn;
    private Button loginBtn;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @BindView(R.id.radio_resident) public RadioButton resident;  // 重点督办 2个单选钮
    @BindView(R.id.radio_admin) public RadioButton admin;
    @BindView(R.id.radio_user_group) public RadioGroup radio_user_group;

    int user_id = 0;

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == resident.getId()){
                //isRace = RequestBody.create(MediaType.parse("multipart/form-data"), "activity");
                user_id = 0;
            } else if (checkedId == admin.getId()) {
                //isRace = RequestBody.create(MediaType.parse("multipart/form-data"), "race");
                user_id = 1;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        ButterKnife.bind(this);
        boolean isRemember = pref.getBoolean("is_remember_pwd", false);

        radio_user_group.setOnCheckedChangeListener(new RadioGroupListener());


        if (isRemember) {
            startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
            finish();
        }

        loginForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"当前密码无法找回", Toast.LENGTH_LONG).show();
            }
        });

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginId.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this,"账号为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (loginPwd.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this,"密码为空", Toast.LENGTH_LONG).show();
                    return;
                }
                // 判断管理员客户端操作****************************************
                String user = loginId.getText().toString();
                String pass = loginPwd.getText().toString();
                if (user_id == 1) {
                    if (user.equals("15023061556") && pass.equals("123456") ||
                            user.equals("123456a") && pass.equals("123456") ||
                            user.equals("123456b") && pass.equals("123456") ||
                            user.equals("123456c") && pass.equals("123456") ||
                            user.equals("123456d") && pass.equals("123456") ||
                            user.equals("123456e") && pass.equals("123456") ||
                            user.equals("123456f") && pass.equals("123456") ||
                            user.equals("123456g") && pass.equals("123456") ||
                            user.equals("123456h") && pass.equals("123456") ||
                            user.equals("123456i") && pass.equals("123456") ||
                            user.equals("123456j") && pass.equals("123456")) {
                            editor.putBoolean("is_admin", true);
                    } else {
                        Toast.makeText(LoginActivity.this,"账号或密码错误", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    if (user.equals("15023061556") && pass.equals("123456") ||
                            user.equals("123456a") && pass.equals("123456") ||
                            user.equals("123456b") && pass.equals("123456") ||
                            user.equals("123456c") && pass.equals("123456") ||
                            user.equals("123456d") && pass.equals("123456") ||
                            user.equals("123456e") && pass.equals("123456") ||
                            user.equals("123456f") && pass.equals("123456") ||
                            user.equals("123456g") && pass.equals("123456") ||
                            user.equals("123456h") && pass.equals("123456") ||
                            user.equals("123456i") && pass.equals("123456") ||
                            user.equals("123456j") && pass.equals("123456")) {
                        Toast.makeText(LoginActivity.this,"请选择管理员选项登陆", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                // *********************************************************

                editor.putBoolean("is_remember_pwd", true);
                editor.putString("id", loginId.getText().toString());
                editor.putString("pwd", loginPwd.getText().toString());
                editor.apply();

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("登陆中...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Map<String, String> actMsg = new HashMap<>();
                actMsg.put("username",  loginId.getText().toString());
                actMsg.put("password", loginPwd.getText().toString());

                OkHttpUtils.post()
                        .params(actMsg)
                        .url(Const.url + "/public/index.php/index/User/login")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,"登陆失败,请重试", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                progressDialog.dismiss();
                                Log.d("LoginActivity1", response);
                                LoginModel model =  new Gson().fromJson(response, LoginModel.class);
                                Log.d("LoginActivity2", "onResponse: " + model.getInfo());
                                Log.d("LoginActivity20", model.getData().getToken());
                                if (model.getInfo().equals("success")) {
                                    editor.putString("token", model.getData().getToken());
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this,"用户不存在或密码错误,请重试", Toast.LENGTH_LONG).show();
                                }
                                // 登陆成功：{"state":200,"info":"success","data":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC93ZWIuY3F1cHQuc3RvcmUiLCJpZCI6NywidXNlcm5hbWUiOiIxNTAyMzA2MTU1NiIsImV4cCI6MTU0NDg1MDk0OH0.sh_yEi0D3YxQGxHhRDYt0X1jUr1hJgb1Kkbd1AEDuhw"}}
                                // 登陆失败：{"state":403,"info":"not exist user or invalid password"}
                            }

                        });
            }
        });
        registerBtn = findViewById(R.id.login_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    class LoginModel {

        /**
         * state : 200
         * info : success
         * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC93ZWIuY3F1cHQuc3RvcmUiLCJpZCI6NywidXNlcm5hbWUiOiIxNTAyMzA2MTU1NiIsImV4cCI6MTU0NDg1MDk0OH0.sh_yEi0D3YxQGxHhRDYt0X1jUr1hJgb1Kkbd1AEDuhw"}
         */

        private int state;
        private String info;
        private DataBean data;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public class DataBean {
            /**
             * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC93ZWIuY3F1cHQuc3RvcmUiLCJpZCI6NywidXNlcm5hbWUiOiIxNTAyMzA2MTU1NiIsImV4cCI6MTU0NDg1MDk0OH0.sh_yEi0D3YxQGxHhRDYt0X1jUr1hJgb1Kkbd1AEDuhw
             */

            private String token;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }


}
