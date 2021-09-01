package com.wj.uidemo;

import android.app.Application;

/**
 * Des
 *
 * @author WangJian on 2021/8/30 11
 */
public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
