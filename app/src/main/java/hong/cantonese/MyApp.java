package hong.cantonese;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import hong.cantonese.utils.MyPref;

import static java.lang.System.currentTimeMillis;

/**
 * Created by hongenit on 2017/1/14.
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        MyPref.INSTANCE.initPref(this);
        long st = System.currentTimeMillis();
        System.out.println("st==========" + st);

        // 讯飞语音初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5879bd38");
        initUmeng();
        initAdMob();

        long et = System.currentTimeMillis();
        System.out.println("st==========" + (et - st));

    }

    public static Context getContext() {
        return appContext;
    }


    private static Context appContext;

    private void initAdMob() {
        MobileAds.initialize(this, ConstantValue.ADMOB_APP_ID);
    }


    private void initUmeng() {
        UMConfigure.init(this, ConstantValue.UMENG_APP_KEY, ConstantValue.UMENG_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMConfigure.setLogEnabled(true);
    }


}
