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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nexuslink.govermanage.bean.Const;
import com.nexuslink.govermanage.bean.GridBean;
import com.nexuslink.govermanage.bean.GridManagerBean;
import com.nexuslink.govermanage.bean.UploadFaildBean;
import com.nexuslink.govermanage.util.MyOKutilCallback;
import com.nexuslink.govermanage.util.Time2Stamp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.feezu.liuli.timeselector.TimeSelector;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class TaskPubActivity extends CheckPermissionsActivity {

    public final static int REQUEST_IMAGE = 2;

    @BindView(R.id.task_activity_back) public ImageView back;
    @BindView(R.id.task_activity_upload_task) public TextView uploadTask;
    @BindView(R.id.task_activity_area) public TextView task_activity_area;
    @BindView(R.id.task_activity_grid) public TextView task_activity_grid;
    @BindView(R.id.task_activity_title) public EditText task_activity_title;
    @BindView(R.id.task_activity_level) public TextView task_activity_level;
    @BindView(R.id.task_activity_endtime) public TextView task_activity_endtime;
    @BindView(R.id.task_activity_type) public TextView task_activity_type;
    @BindView(R.id.task_activity_peo) public TextView task_activity_peo;
    @BindView(R.id.task_activity_intro) public EditText task_activity_intro;
    @BindView(R.id.task_activity_upload_photo) public ImageView task_activity_pic;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    int area = 0;
    String mission_title = "无标题";
    String mission_level = "1";
    String not_before = String.valueOf((System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000 + 2 * 60 * 60 * 1000) / 1000);
    String mission_type = "1";
    String mission_people = "[6]";
    String content = "用户未填写内容";
    int grid = 1;
    File pic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pub);

        ButterKnife.bind(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

//        if (!token.equals("")) {
//            startActivity(new Intent(TaskPubActivity.this, NavigationActivity.class));
//            finish();
//        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        task_activity_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType("image/*");
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        task_activity_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                //To get local formatting use getDateInstance(), getDateTimeInstance(), or getTimeInstance(),
                // or use new SimpleDateFormat(String template, Locale locale) with for example Locale.US for ASCII dates. less... (Ctrl+F1)
                //Almost all callers should use getDateInstance(), getDateTimeInstance(), or getTimeInstance() to get a ready-made instance
                // of SimpleDateFormat suitable for the user's locale. The main reason you'd create an instance this class directly is because
                // you need to format/parse a specific machine-readable format, in which case you almost certainly want to explicitly ask for US
                // to ensure that you get ASCII digits (rather than, say, Arabic digits).  Therefore, you should either use the form of the
                // SimpleDateFormat constructor where you pass in an explicit locale, such as Locale.US, or use one of the get instance methods,
                // or suppress this error if really know what you are doing.  Issue id: SimpleDateFormat  More info:
                Log.d("timeselector1", df.format(date) + " | " + Time2Stamp.getTimeString((Long.valueOf(Time2Stamp.getTimeStamp(df.format(date))) / 1000) + (60 * 24 * 60 * 60L) + ""));
                TimeSelector timeSelector = new TimeSelector(TaskPubActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        task_activity_endtime.setText(time);
                        not_before = Long.valueOf(Time2Stamp.getTimeStamp(time)) / 1000 + "";
                        Log.d("timeselector1", time + " | " + Time2Stamp.getTimeStamp(time));
                    }
                }, df.format(date).substring(0, 15), Time2Stamp.getTimeString((Long.valueOf(Time2Stamp.getTimeStamp(df.format(date))) / 1000) + (60 * 24 * 60 * 60L) + ""));
                timeSelector.setIsLoop(true);
                timeSelector.setTitle("请选择任务结束时间");
                timeSelector.show();

            }
        });

        //final String[] items3 = {"第一网格:黎明", "第二网格：张武", "第三网格：刘琦", "第四网格", "第五网格", "第六网格", "第七网格", "第八网格", };
        task_activity_peo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(TaskPubActivity.this);
                progressDialog.setMessage("数据获取中...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                OkHttpUtils.post()
                        .addHeader("Authorization", pref.getString("token", ""))
                        .addParams("page", "1")
                        .addParams("size", "10")
                        .url(Const.url + "/public/index.php/index/System/gridAdminList")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                progressDialog.dismiss();
                                Toast.makeText(TaskPubActivity.this,"数据获取失败,请重试", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                progressDialog.dismiss();
                                GridManagerBean gridBean = new Gson().fromJson(response, GridManagerBean.class);
                                List<GridManagerBean.DataBean> dataBeansGrid = gridBean.getData();
                                final String[] items8 = new String[dataBeansGrid.size()];
                                final int[] items8Id = new int[dataBeansGrid.size()];

                                for (int i = 0; i < dataBeansGrid.size(); i++) {
                                    items8[i] = dataBeansGrid.get(i).getServer_area() + ":" + dataBeansGrid.get(i).getName();
                                    items8Id[i] = dataBeansGrid.get(i).getId();
                                }

                                AlertDialog dialog = new AlertDialog
                                        .Builder(TaskPubActivity.this)
                                        .setTitle("选择网格管理员")
                                        .setSingleChoiceItems(items8, 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                task_activity_peo.setText(items8[which]);
                                                grid = items8Id[which];
                                                dialog.dismiss();
                                            }
                                        }).create();
                                dialog.show();

                            }

                        });
            }
        });

        //final String[] items8 = {"第一网格", "第二网格", "第三网格", "第四网格", "第五网格", "第六网格", "第七网格", "第八网格", };
        task_activity_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(TaskPubActivity.this);
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
                                Toast.makeText(TaskPubActivity.this,"数据获取失败,请重试", Toast.LENGTH_LONG).show();
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
                                        .Builder(TaskPubActivity.this)
                                        .setTitle("选择所属网格")
                                        .setSingleChoiceItems(items8, 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                task_activity_grid.setText(items8[which]);
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
        task_activity_area.setText("天府镇");
        task_activity_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(TaskPubActivity.this)
                        .setTitle("选择所属区域")
                        .setSingleChoiceItems(items9, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                task_activity_area.setText(items9[which]);
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

        final String[] items7 = {"一级", "二级", "三级"};
        task_activity_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(TaskPubActivity.this)
                        .setTitle("选择任务等级")
                        .setSingleChoiceItems(items7, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                task_activity_level.setText(items7[which]);
                                switch (items7[which]) {
                                    case "一级":
                                        area = 0;
                                        break;
                                    case "二级":
                                        area = 1;
                                        break;
                                    case "三级":
                                        area = 2;
                                        break;
                                }
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        final String[] items6 = {"群体活动", "社区交流", "文化表演"};
        task_activity_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog
                        .Builder(TaskPubActivity.this)
                        .setTitle("选择任务类型")
                        .setSingleChoiceItems(items6, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                task_activity_type.setText(items6[which]);
                                switch (items6[which]) {
                                    case "群体活动":
                                        area = 0;
                                        break;
                                    case "社区交流":
                                        area = 1;
                                        break;
                                    case "文化表演":
                                        area = 2;
                                        break;
                                }
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        uploadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(TaskPubActivity.this);
                progressDialog.setMessage("任务信息上传中...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                mission_title = task_activity_title.getText().toString();
                mission_level = task_activity_level.getText().toString();
                mission_type = task_activity_type.getText().toString();
                content = task_activity_intro.getText().toString();

                Map<String, String> actMsg = new HashMap<>();
                actMsg.put("area", String.valueOf(area));
                actMsg.put("mission_title", mission_title);
                actMsg.put("mission_level", mission_level);
                actMsg.put("not_before", not_before);
                actMsg.put("mission_type", mission_type);
                actMsg.put("mission_people", mission_people);
                actMsg.put("content", content);
                actMsg.put("grid", String.valueOf(grid));

                // *************************************************************
                if (pic == null) {
                    OkHttpUtils.post()
                            .url(Const.url + "/public/index.php/index/Work/missionAdd")
                            .addHeader("Authorization", pref.getString("token", ""))
                            .params(actMsg)
                            .build()
                            .execute(new MyOKutilCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    if (e.toString().equals("{\"state\":403,\"info\":\"no enough power\",\"data\":[]}")) {
                                        Toast.makeText(TaskPubActivity.this,"您无任务发布权限", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(TaskPubActivity.this,"任务信息上传失败,请重试", Toast.LENGTH_LONG).show();
                                    }
                                    Log.d("TaskPubActivity1", e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    Log.d("TaskPubActivity1", "稳态" + response);
                                    if (response.equals("{\"state\":200,\"info\":\"success\",\"data\":[]}")) {
                                        Toast.makeText(TaskPubActivity.this, "任务信息上传成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                } else {
                    OkHttpUtils.post()
                            .url(Const.url + "/public/index.php/index/Work/missionAdd")
                            .addHeader("Authorization", pref.getString("token", ""))
                            .addFile("pic[]", "work.jpeg", pic)
                            .params(actMsg)
                            .build()
                            .execute(new MyOKutilCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    progressDialog.dismiss();
                                    if (e.toString().equals("{\"state\":403,\"info\":\"no enough power\",\"data\":[]}")) {
                                        Toast.makeText(TaskPubActivity.this,"您无任务发布权限", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(TaskPubActivity.this,"任务信息上传失败,请重试", Toast.LENGTH_LONG).show();
                                    }
                                    Log.d("TaskPubActivity1", e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    progressDialog.dismiss();
                                    Log.d("TaskPubActivity1", "稳态" + response);
                                    if (response.equals("{\"state\":200,\"info\":\"success\",\"data\":[]}")) {
                                        Toast.makeText(TaskPubActivity.this, "任务信息上传成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                }

                // *************************************************************
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
                        task_activity_pic.setImageBitmap(bm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(TaskPubActivity.this,"图片压缩异常", Toast.LENGTH_LONG).show();
                    }
                }).launch();
    }
}
