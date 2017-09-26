package com.shyms.farendating.home.guide.details.details_list;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


import com.shyms.farendating.utils.FileUtil;
import com.shyms.farendating.utils.LogcatUtil;

import java.io.File;

import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;

/**
 * Created by Weimin on 4/21/2016.
 */

public class DownLoadCompleteReceiver extends BroadcastReceiver {

    private static final String TAG = "DownLoad";

    @Override
    public void onReceive(Context context, Intent intent) {
//        LogcatUtil.d(TAG, "进入下载广播");
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String name = (String) SPUtil.get(id + "", System.currentTimeMillis() + "");
            ToastUtil.shortShowText(name + "的下载任务已经完成！请在通知栏点击打开");
            ToastUtil.shortShowText("需要第三方软件打开查看");
            FindFilePath(context, intent);
        } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            ToastUtil.shortShowText("正在下载中");
        }
    }

    public void FindFilePath(Context context, Intent intent) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Bundle extras = intent.getExtras();
        long doneDownloadId = extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
        //查询下载文件信息：查询存储路径
        Cursor cursor = manager.query(new DownloadManager.Query().setFilterById(doneDownloadId));
        if (cursor != null) {
            cursor.moveToFirst();
//            LogcatUtil.d("fileName==", cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME)));
        }
        cursor.close();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            DownloadManager.Query query = new DownloadManager.Query();
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor c = manager.query(query);
            if (c.moveToFirst()) {
                //获取文件下载路径
                String filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));//COLUMN_LOCAL_FILENAME
                //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                if (filename != null) {
                    //ToastUtil.shortShowText("这个也行");
                    LogcatUtil.d("DownLoad", "下载成功====" + filename);
                    File file = new File(filename);
                    if (file.exists()) {
                        LogcatUtil.d("DownLoad", "文件存在可打开" + file.getName());
                        FileUtil.openFiles(filename);
                    } else {
                        LogcatUtil.d("DownLoad", "文件不存在");
                    }
                }
            }
        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
            long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
            //点击通知栏取消下载
            manager.remove(ids);
            ToastUtil.shortShowText("已经取消下载");
        }
    }

    /*@Override
    public void onReceive(Context context, Intent intent) {

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            DownloadManager.Query query = new DownloadManager.Query();
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor c = manager.query(query);
            if (c.moveToFirst()) {
                //获取文件下载路径
                String filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                if (filename != null) {
                   ToastUtil.shortShowText("这个也行");
               }
           }
        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
            long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
         //点击通知栏取消下载
           manager.remove(ids);
            ToastUtil.shortShowText("已经取消下载");
        }

    }*/
}
