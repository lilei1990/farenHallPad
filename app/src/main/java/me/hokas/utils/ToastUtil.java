package me.hokas.utils;

import android.content.Context;
import android.widget.Toast;

import me.hokas.base.BaseApplication;

/**
 * toast工具
 * Created by Hokas on 2016/1/26.
 */
public class ToastUtil {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void shortShowText(CharSequence text) {
        Toast.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

}
