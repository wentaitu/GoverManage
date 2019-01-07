package com.nexuslink.govermanage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nexuslink.govermanage.bean.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_id) public EditText id;
    @BindView(R.id.register_pwd) public EditText pwd;
    @BindView(R.id.register_txt) public EditText pwdAgain;
    @BindView(R.id.register_tverify_code) public EditText verify_code_txt;
    @BindView(R.id.register_btn) public Button loginBtn;
    @BindView(R.id.btn_verify_code) public Button verify_code;
    @BindView(R.id.btn_verify_code2) public Button verify;

    ProgressDialog progressDialog2;

    boolean isCodeRight = false;

    String name = "用户未填写姓名";
    String area = "用户未填写地区";
    String identity = "6";
    String user_state = "1";
    String communication_phone = "00000000000";
    String office_phone = "00000000";


    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            Toast.makeText(RegisterActivity.this,"验证码已发送", Toast.LENGTH_LONG).show();

                        } else {
                            // TODO 处理错误的结果
                            // ((Throwable) data).printStackTrace();
                            Toast.makeText(RegisterActivity.this,"验证码未发送", Toast.LENGTH_LONG).show();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            isCodeRight = true;
                            progressDialog2.dismiss();
                        } else {
                            // TODO 处理错误的结果
                            //((Throwable) data).printStackTrace();
                            Toast.makeText(RegisterActivity.this,"验证码错误", Toast.LENGTH_LONG).show();
                            progressDialog2.dismiss();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        progressDialog2 = new ProgressDialog(RegisterActivity.this);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 提交验证码，其中的code表示验证码，如“1357”
                if (verify_code_txt.getText().toString().trim().equals("")) {
                    Toast.makeText(RegisterActivity.this,"验证码为空", Toast.LENGTH_LONG).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", id.getText().toString(), verify_code_txt.getText().toString());
                progressDialog2.setMessage("正在验证...");
                progressDialog2.setCancelable(true);
                progressDialog2.show();
            }
        });

        verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().trim().equals("")) {
                    Toast.makeText(RegisterActivity.this,"手机号为空", Toast.LENGTH_LONG).show();
                    return;
                }
                String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
                Pattern p = Pattern.compile(PHONE_NUMBER_REG);
                Matcher m = p.matcher(id.getText().toString());
                if(!m.matches()) {
                    Toast.makeText(RegisterActivity.this,"手机号格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }

                // 采用短信系统自带UI
//                    RegisterPage page = new RegisterPage();
//                    //如果使用我们的ui，没有申请模板编号的情况下需传null
//                    page.setTempCode(null);
//                    page.setRegisterCallback(new EventHandler() {
//                        public void afterEvent(int event, int result, Object data) {
//                            if (result == SMSSDK.RESULT_COMPLETE) {
//                                // 处理成功的结果
//                                HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
//                                String country = (String) phoneMap.get("country"); // 国家代码，如“86”
//                                String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
//                                // TODO 利用国家代码和手机号码进行后续的操作
//                            } else{
//                                // TODO 处理错误的结果
//                            }
//                        }
//                    });
//                    page.show(RegisterActivity.this);

                //***************************************************************************
                // 在尝试读取通信录时以弹窗提示用户（可选功能）
                //SMSSDK.setAskPermisionOnReadContact(true);
                // 注册一个事件回调，用于处理SMSSDK接口请求的结果
                SMSSDK.registerEventHandler(eventHandler);
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                SMSSDK.getVerificationCode("86", id.getText().toString());

                //****************************************************************************

            }
        });

//        if ((TextUtils.isEmpty(id.getText().toString())) && ("null".equalsIgnoreCase(id.getText().toString()))) {
//            Toast.makeText(RegisterActivity.this,"账号为空", Toast.LENGTH_LONG).show();
//        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().trim().equals("")) {
                    Toast.makeText(RegisterActivity.this,"账号为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pwd.getText().toString().trim().equals("")) {
                    Toast.makeText(RegisterActivity.this,"密码为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (verify_code_txt.getText().toString().trim().equals("")) {
                    Toast.makeText(RegisterActivity.this,"验证码为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!isCodeRight) {
                    Toast.makeText(RegisterActivity.this,"验证码错误", Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("正在注册用户信息...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Map<String, String> actMsg = new HashMap<>();
                actMsg.put("username",  id.getText().toString());
                actMsg.put("passwd", pwd.getText().toString());
                if (!pwdAgain.getText().toString().equals(pwd.getText().toString())) {
                    Toast.makeText(RegisterActivity.this,"密码输入不一致", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                OkHttpUtils.post()
                        .url(Const.url + "/public/index.php/index/User/registerForWeb")
                        //.params(actMsg)
                        .addParams("username", id.getText().toString())
                        .addParams("password", pwd.getText().toString())
                        .addParams("name", name)
                        .addParams("area", area)
                        .addParams("identity", identity)
                        .addParams("user_state", user_state)
                        .addParams("communication_phone", communication_phone)
                        .addParams("office_phone", office_phone)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "注册失败,请重试", Toast.LENGTH_LONG).show();
                                Log.d("RegisterActivity1", e.toString());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "注册成功，跳转至登录界面", Toast.LENGTH_SHORT).show();
                                Log.d("RegisterActivity1", "稳态" + response);
                                // 注册成功：
                                // {"state":200,"info":"success","data":{"id":1}}

                                finish();
                            }

                        });

            }
        });
    }

    protected void onDestroy() {
        // 使用完EventHandler需注销，否则可能出现内存泄漏
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
