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
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.GridBean;
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

public class WorkUpActivity extends CheckPermissionsActivity {

    public final static int REQUEST_IMAGE = 2;

    @BindView(R.id.work_up_back) public ImageView back;
    @BindView(R.id.work_activity_upload_msg) public TextView uploadMsg;
    @BindView(R.id.work_activity_area) public TextView workArea;
    @BindView(R.id.work_activity_content) public TextView workContent;
    @BindView(R.id.work_activity_grid) public TextView workGrid;
    @BindView(R.id.work_activity_title) public TextView workTitle;
    @BindView(R.id.work_activity_type) public TextView workType;
    @BindView(R.id.work_activity_upload_photo) public ImageView photo;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    int area = 0;
    int grid = 1;
    String log_name = "无标题";
    int log_type = 1;
    String content = "用户未填写内容";
    File pic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_up);

        ButterKnife.bind(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType("image/*");
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        //final String[] items8 = {"第一网格", "第二网格", "第三网格", "第四网格", "第五网格", "第六网格", "第七网格", "第八网格", };
        workGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(WorkUpActivity.this);
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
                                Toast.makeText(WorkUpActivity.this,"数据获取失败,请重试", Toast.LENGTH_LONG).show();
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
                                        .Builder(WorkUpActivity.this)
                                        .setTitle("选择所属网格")
                                        .setSingleChoiceItems(items8, 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                workGrid.setText(items8[which]);
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

        final String[] items9 = {"天府镇"};//, "区域B", "区域C", "区域D", "区域E", "区域F", "区域G", "区域H"};
        workArea.setText("天府镇");
        workArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(WorkUpActivity.this)
                        .setTitle("选择所属区域")
                        .setSingleChoiceItems(items9, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                workArea.setText(items9[0]);
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

        final String[] items10 = {"巡查", "走访", "宣传", "处理", "重点人员寻访"};
        workType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(WorkUpActivity.this)
                        .setTitle("选择工作类型")
                        .setSingleChoiceItems(items10, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                workType.setText(items10[which]);
                                switch (items10[which]) {
                                    case "巡查":
                                        log_type = 0;
                                        break;
                                    case "走访":
                                        log_type = 1;
                                        break;
                                    case "区域C":
                                        log_type = 2;
                                        break;
                                    case "宣传":
                                        log_type = 3;
                                        break;
                                    case "处理":
                                        log_type = 4;
                                        break;
                                    case "重点人员寻访":
                                        log_type = 5;
                                        break;
                                }
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        uploadMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(WorkUpActivity.this);
                progressDialog.setMessage("任务信息上传中...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                log_name = workTitle.getText().toString();
                content = workContent.getText().toString();

                Map<String, String> actMsg = new HashMap<>();
                actMsg.put("area", String.valueOf(area));
                actMsg.put("grid", String.valueOf(grid));
                actMsg.put("log_name", log_name);
                actMsg.put("log_type", String.valueOf(log_type));
                actMsg.put("content", content);

                // *************************************************************
                if (pic == null) {
                    OkHttpUtils.post()
                            .url(Const.url + "/public/index.php/index/Work/dailyWorkAdd")
                            .addHeader("Authorization", pref.getString("token", ""))
                            //.addFile("pic[]", "work.jpeg", pic)
                            .params(actMsg)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(WorkUpActivity.this,"工作信息上传失败,请重试", Toast.LENGTH_LONG).show();
                                    Log.d("WorkUpActivity1", e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    if (response.equals("{\"state\":200,\"info\":\"success\",\"data\":[]}")) {
                                        Toast.makeText(WorkUpActivity.this,"工作信息上传成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                } else {
                    OkHttpUtils.post()
                            .url(Const.url + "/public/index.php/index/Work/dailyWorkAdd")
                            .addHeader("Authorization", pref.getString("token", ""))
                            .addFile("pic[]", "work.jpeg", pic)
                            .params(actMsg)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    Toast.makeText(WorkUpActivity.this,"工作信息上传失败,请重试", Toast.LENGTH_LONG).show();
                                    Log.d("WorkUpActivity1", e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    if (response.equals("{\"state\":200,\"info\":\"success\",\"data\":[]}")) {
                                        Toast.makeText(WorkUpActivity.this,"工作信息上传成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
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
                        photo.setImageBitmap(bm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(WorkUpActivity.this,"图片压缩异常", Toast.LENGTH_LONG).show();
                    }
                }).launch();
    }

}
