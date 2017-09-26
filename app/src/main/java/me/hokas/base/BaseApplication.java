package me.hokas.base;

import android.app.Application;
import android.graphics.Color;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.shyms.faren.R;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.loader.FrescoImageLoader;
import me.hokas.utils.SPUtil;


/**
 * 全局
 * Created by Administrator on 2015/6/24.
 */
public class BaseApplication extends Application {


    public boolean isLogin = false;

    private static BaseApplication baseApplication;

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
//        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
//        LeakCanary.install(this);
        initFresco();

        SPUtil.init(baseApplication);
    }

    public void initFresco() {
        Fresco.initialize(this);
        //gallay final
        //设置主题
//ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.parseColor("#75CCA0"))
                .setIconBack(R.mipmap.icon_back)//设置返回按钮
                .setFabPressedColor(Color.parseColor("#98FB98"))
                .build();

//配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        FrescoImageLoader imageLoader = new FrescoImageLoader(this);
        CoreConfig coreConfig = new CoreConfig.Builder(baseApplication, imageLoader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

}
