package com.xiaoduotech.xiaoduosdkdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.xiaoduotech.sdk.cvdsdk.CVDManager;


/**
 * Created by zhangchong on 16/7/22.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CVDManager.getInstance(this).init("16",11,"b5b1f884faf0ac251403435183ee5b140cb83ac28d62abab30cfe6145004ded4");
        LeakCanary.install(this);

    }
}
