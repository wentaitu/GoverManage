package com.nexuslink.govermanage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.GridBean;
import com.nexuslink.govermanage.bean.UploadFaildBean;
import com.nexuslink.govermanage.util.MyOKutilCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.cqupt.loc.permission.CheckPermissionsActivity;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class PushMsgActivity extends CheckPermissionsActivity {

    @BindView(R.id.activity_name) public EditText name;    // 标题
    @BindView(R.id.activity_intro) public EditText intro;   // 内容
    @BindView(R.id.location) public TextView location;      // 事件来源
    @BindView(R.id.people_needs) public TextView needs;     // 事件类型 0-
    @BindView(R.id.choose_activity) public RadioButton chooseActivity;  // 重点督办 2个单选钮
    @BindView(R.id.choose_competition) public RadioButton chooseCompetition;
    @BindView(R.id.choose_radiogroup) public RadioGroup radioGroup;
    @BindView(R.id.upload_msg) public TextView uploadMsg;  // 提交按钮
    @BindView(R.id.start_time) public TextView startTime;  // 事件等级
    @BindView(R.id.upload_photo) public ImageView uploadPhoto;  // 上传图片
    @BindView(R.id.back_post_activity) public ImageView backActivity;
    @BindView(R.id.event_area) public TextView eventArea;
    @BindView(R.id.event_grid) public TextView eventGrid;

    public final static int REQUEST_IMAGE = 2;

    String title = null;
    String content = "无详细内容";
    int level = 1; // 默认事件等级 一般
    int type = 4;  // 默认事件类型 其他
    int source = 1;  // 默认事件来源 自己发现
    int supervise = 1;  // 默认是否督办 否
    double longitude = 0.0;  // 默认经纬度值
    double latitude = 0.0;
    String address = "无法获取地址信息";
    File pic = null;  // 默认图片为空;
    int area = 0;
    int grid = 0;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_msg);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        //final String[] items8 = {"第一网格", "第二网格", "第三网格", "第四网格", "第五网格", "第六网格", "第七网格", "第八网格", };
        eventGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(PushMsgActivity.this);
                progressDialog.setMessage("数据获取中...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                OkHttpUtils.post()
                        .addHeader("Authorization", pref.getString("token", ""))
                        .addParams("page", "1")
                        .addParams("size", "30")
                        .url(Const.url + "/public/index.php/index/System/gridList")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                progressDialog.dismiss();
                                Toast.makeText(PushMsgActivity.this,"数据获取失败,请重试", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                progressDialog.dismiss();
                                GridBean gridBean = new Gson().fromJson(response, GridBean.class);
                                List<GridBean.DataBean> dataBeansGrid = gridBean.getData();
                                final String[] items8 = new String[dataBeansGrid.size()];
                                final int[] items8Id = new int[dataBeansGrid.size()];

                                for (int i = 0; i < dataBeansGrid.size(); i++) {
                                    items8[i] = dataBeansGrid.get(i).getName();
                                    items8Id[i] = dataBeansGrid.get(i).getId();
                                }

                                AlertDialog dialog = new AlertDialog
                                        .Builder(PushMsgActivity.this)
                                        .setTitle("选择所属网格")
                                        .setSingleChoiceItems(items8, 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                eventGrid.setText(items8[which]);
                                                grid = items8Id[which];
//                                                switch (items8[which]) {
//                                                    case "第一网格":
//                                                        grid = 0;
//                                                        break;
//                                                    case "第二网格":
//                                                        grid = 1;
//                                                        break;
//                                                    case "第三网格":
//                                                        grid = 2;
//                                                        break;
//                                                    case "第四网格":
//                                                        grid = 3;
//                                                        break;
//                                                    case "第五网格":
//                                                        grid = 4;
//                                                        break;
//                                                    case "第六网格":
//                                                        grid = 5;
//                                                        break;
//                                                    case "第七网格":
//                                                        grid = 6;
//                                                        break;
//                                                    case "第八网格":
//                                                        grid = 7;
//                                                        break;
//                                                }
                                                dialog.dismiss();
                                            }
                                        }).create();
                                dialog.show();

                            }

                        });
            }
        });

        eventArea.setText("天府镇");
        final String[] items9 = {"天府镇"};//, "区域B", "区域C", "区域D", "区域E", "区域F", "区域G", "区域H"};
        eventArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(PushMsgActivity.this)
                        .setTitle("选择所属区域")
                        .setSingleChoiceItems(items9, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eventArea.setText(items9[which]);
                                switch (items9[which]) {
                                    case "天府镇":
                                        area = 0;
                                        break;
//                                    case "区域B":
//                                        area = 1;
//                                        break;
//                                    case "区域C":
//                                        area = 2;
//                                        break;
//                                    case "区域D":
//                                        area = 3;
//                                        break;
//                                    case "区域E":
//                                        area = 4;
//                                        break;
//                                    case "区域F":
//                                        area = 5;
//                                        break;
//                                    case "区域G":
//                                        area = 6;
//                                        break;
//                                    case "区域H":
//                                        area = 7;
//                                        break;
                                }
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        final String[] items = {"民事纠纷", "市政环卫", "物业管理", "隐患排查", "其它"};
        needs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(PushMsgActivity.this)
                        .setTitle("选择事件类型")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                needs.setText(items[which]);
                                switch (items[which]) {
                                    case "民事纠纷":
                                        type = 0;
                                        break;
                                    case "市政环卫":
                                        type = 1;
                                        break;
                                    case "物业管理":
                                        type = 2;
                                        break;
                                    case "隐患排查":
                                        type = 3;
                                        break;
                                    case "其它":
                                        type = 4;
                                        break;
                                }
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        final String[] items2 = {"紧急", "一般"};
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(PushMsgActivity.this)
                        .setTitle("选择事件等级")
                        .setSingleChoiceItems(items2, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startTime.setText(items2[which]);
                                switch (items2[which]) {
                                    case "紧急":
                                        level = 0;
                                        break;
                                    case "一般":
                                        level = 1;
                                }

                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        final String[] items3 = {"群众反应", "自己发现", "上级安排"};
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(PushMsgActivity.this)
                        .setTitle("选择事件来源")
                        .setSingleChoiceItems(items3, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                location.setText(items3[which]);
                                switch (items3[which]) {
                                    case "群众反应":
                                        source = 0;
                                        break;
                                    case "自己发现":
                                        source = 1;
                                        break;
                                    case "上级安排":
                                        source = 2;
                                        break;
                                }
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType("image/*");
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());

        uploadMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")) {
                    Toast.makeText(PushMsgActivity.this,"标题为空，请输入", Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(PushMsgActivity.this);
                progressDialog.setMessage("活动信息上传中...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                title = name.getText().toString();
                content = intro.getText().toString();
//                if (name.getText().toString().equals(null))
//                    return; // 永远不会执行return???

                if ((TextUtils.isEmpty(content)) && ("null".equalsIgnoreCase(content))) {
                    content = "未提交详细内容";
                }

                editor = pref.edit();
                address = pref.getString("address", "无法获取地址信息");
                longitude = Double.valueOf(pref.getString("longitude", "0.0"));
                latitude = Double.valueOf(pref.getString("latitude", "0.0"));
                editor.apply();

                Log.d("ffffffffff", address + longitude + "|" + latitude);

                Map<String, String> actMsg = new HashMap<>();
//                actMsg.put("title",  title);
//                actMsg.put("content", content);
//                actMsg.put("level", String.valueOf(level));
//                actMsg.put("type", String.valueOf(type));
//                actMsg.put("organizer", "123456");
//                actMsg.put("source", String.valueOf(source));
//                actMsg.put("supervise", String.valueOf(supervise));
//                actMsg.put("longitude", String.valueOf(longitude));
//                actMsg.put("latitude", String.valueOf(latitude));
//                actMsg.put("address", address);

                actMsg.put("emergency_level",  String.valueOf(level));
                actMsg.put("emergency_type", String.valueOf(type));
                actMsg.put("emergency_source", String.valueOf(source));
                actMsg.put("edit_time", String.valueOf(System.currentTimeMillis() / 1000));
                actMsg.put("is_important", String.valueOf(supervise));
                actMsg.put("title", title);
                actMsg.put("content", content);
                actMsg.put("grid", String.valueOf(area));
                actMsg.put("area", String.valueOf(grid));

//                actMsg.put("emergency_level",  "0");
//                actMsg.put("emergency_type", "1");
//                actMsg.put("emergency_source", "2");
//                actMsg.put("edit_time", "5236987412");
//                actMsg.put("is_important", "0");
//                actMsg.put("title", "xxxxxxxxxxx");
//                actMsg.put("content", "xxxxxxxxxxx");
//                actMsg.put("grid", "2");
//                actMsg.put("area", "0");
                // 开始刚注释
                if (pic == null) {
                    OkHttpUtils.post()
                            .params(actMsg)
                            .url(Const.url + "/public/index.php/index/Work/emergencyAdd")
                            .addHeader("Authorization", pref.getString("token", ""))
                            //.addFile("file", pic == null ? null : "photo.jpeg", pic)        // 可扩展至多张图片即多个addFile(),1.2个参数???
                            //.addFile(null, null, null)
                            .build()
                            .execute(new MyOKutilCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PushMsgActivity.this,"信息上传失败,请重试", Toast.LENGTH_LONG).show();
                                    Log.d("PushMsgActivity1", e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    // 上传失败
                                    Log.d("PushMsgActivity8", "稳态" + response);
                                    // 上传成功
                                    // {"state":200,"info":"success","data":[]}
                                    if (response.equals("{\"state\":200,\"info\":\"success\",\"data\":[]}")) {
                                        Toast.makeText(PushMsgActivity.this,"信息上传成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(PushMsgActivity.this,"信息上传失败,请重试", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else { // 图片不为空
                    OkHttpUtils.post()
                            .params(actMsg)
                            .addHeader("Authorization", pref.getString("token", ""))
                            .url(Const.url + "/public/index.php/index/Work/emergencyAdd")
                            //.addFile("file", pic == null ? null : "photo.jpeg", pic)        // 可扩展至多张图片即多个addFile(),1.2个参数???
                            //.addFile(null, null, null)
                            .addFile("pic[]", "photo.jpeg", pic)
                            .build()
                            .execute(new MyOKutilCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PushMsgActivity.this,"信息上传失败,请重试", Toast.LENGTH_LONG).show();
                                    Log.d("PushMsgActivity1", e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    // 上传失败
                                    Toast.makeText(PushMsgActivity.this,"信息上传成功", Toast.LENGTH_LONG).show();
                                    Log.d("PushMsgActivity8", "稳态" + response);
                                    // 上传成功
                                    finish();
                                }
                            });
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE) {
//            Log.i("qqliLog", "GalleryUri:    " + data.getData());
//        }
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    private void showImage(String imaePath) {
        Luban.with(this)
                .load(imaePath)
                .ignoreBy(100)
                .setTargetDir(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).toString())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // 压缩成功后调用，返回压缩后的图片文件
                        pic = file;
                        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Log.d("PushMsgActivity5", pic.getAbsolutePath());
                        uploadPhoto.setImageBitmap(bm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(PushMsgActivity.this,"图片压缩异常", Toast.LENGTH_LONG).show();
                    }
                }).launch();
    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == chooseActivity.getId()){
                //isRace = RequestBody.create(MediaType.parse("multipart/form-data"), "activity");
                supervise = 0;
            } else if (checkedId == chooseCompetition.getId()) {
                //isRace = RequestBody.create(MediaType.parse("multipart/form-data"), "race");
                supervise = 1;
            }
        }
    }

}
