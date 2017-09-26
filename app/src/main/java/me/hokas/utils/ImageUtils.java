package me.hokas.utils;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import me.hokas.base.BaseApplication;

/**
 * Created by Weimin on 4/13/2016.
 */
public class ImageUtils {

    public static void downloadFile(DownloadManager downloadManager, String url, String saveName) {
        if (!Util.existSDCard()) {
            ToastUtil.shortShowText("SD卡不存在");
            return;
        }
        String prefix = "." + url.substring(url.lastIndexOf(".") + 1);
//        Log.d("文件后缀=====  ", prefix);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(saveName);
        request.setDescription(saveName + "正在下载...");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalFilesDir(BaseApplication.getInstance(), Environment.DIRECTORY_DOWNLOADS, saveName + prefix);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        long nameId = downloadManager.enqueue(request);
        SPUtil.put(nameId + "", saveName);
    }
    public static Bitmap stringToBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    public static String bitmapToString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    public static Bitmap covertToBitmap(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }


}
