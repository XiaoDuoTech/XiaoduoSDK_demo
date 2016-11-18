package com.xiaoduotech.xiaoduosdkdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.xiaoduotech.sdk.cvdframework.CVDManager;

import im.fir.sdk.FIR;


/**
 * Created by zhangchong on 16/7/22.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
        LeakCanary.install(this);
        CVDManager.getInstance().init(this,"16",11,
                "b5b1f884faf0ac251403435183ee5b140cb83ac28d62abab30cfe6145004ded4",
                MyConversationActivity.class,LoginActivity.class
        );
       

    }
}
