package com.shyms.farendating.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.shyms.faren.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 自动更新管理类
 * Created by Administrator on 2015/8/11.
 */
public class UpdateAppManager {
    private static final String FILE_SEPARATOR = "/";
    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "yms" + FILE_SEPARATOR;
    public static final String FILE_NAME = FILE_PATH + "yms.apk";
    private static final int UPDARE_TOKEN = 0x29;
    private static final int INSTALL_TOKEN = 0x31;
    private Context context;
    private String message = "检测到本程序有新版本发布，建议您更新！";
    private String spec = "http://19.134.148.17/yms.apk";
    private Dialog dialog;
    private ProgressBar progressBar;
    private int curProgress;
    private boolean isCancel;

    public UpdateAppManager(Context context) {
        this.context = context;
//        message = "当前版本" +  getVersionName(context) + "检测到本程序有新版本" + ;
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDARE_TOKEN:
                    progressBar.setProgress(curProgress);
                    break;
                case INSTALL_TOKEN:
                    installApp();
                    break;
            }
        }
    };


    /**
     *
     */
    public void checkUpdateInfo(String code) {
        showNoticeDialog(code);
    }

    /**
     *
     */
    private void showNoticeDialog(String code) {
        new AlertDialog.Builder(context)
                .setTitle("软件版本更新")
                .setMessage("检测到本程序有新版本\n" + "当前版本:" + getVersionName(context) + "\n新的版本:" + code + "建议您更新！")
                .setPositiveButton("下载", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showDownloadDialog();

                    }
                }).setNegativeButton("以后再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void showDownloadDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.update_progress_bar, null);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("软件版本更新");
        builder.setView(view);
        builder.setNegativeButton("取消", (dialog1, which) -> {
            dialog1.dismiss();
            isCancel = true;
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        downloadApp();
    }

    public void seturl(String url) {
        spec = url;
    }

    private void downloadApp() {
        new Thread(() -> {
            URL url = null;
            InputStream in = null;
            FileOutputStream out = null;
            HttpURLConnection conn = null;
            try {
                url = new URL(spec);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                long fileLength = conn.getContentLength();
                in = conn.getInputStream();
                File filePath = new File(FILE_PATH);
                if (!filePath.exists()) {
                    filePath.mkdir();
                }
                out = new FileOutputStream(new File(FILE_NAME));
                byte[] buffer = new byte[1024];
                int len = 0;
                long readedLength = 0l;
                while ((len = in.read(buffer)) != -1) {
                    if (isCancel) {
                        break;
                    }
                    out.write(buffer, 0, len);
                    readedLength += len;
                    curProgress = (int) (((float) readedLength / fileLength) * 100);
                    handler.sendEmptyMessage(UPDARE_TOKEN);
                    if (readedLength >= fileLength) {
                        dialog.dismiss();
                        handler.sendEmptyMessage(INSTALL_TOKEN);
                        break;
                    }
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }).start();
    }


    private void installApp() {
        File appFile = new File(FILE_NAME);
        if (!appFile.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(appFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }
}
