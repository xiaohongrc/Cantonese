package hong.cantonese;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.commonsdk.UMConfigure;

import static java.lang.System.currentTimeMillis;

/**
 * Created by hongenit on 2017/1/14.
 *
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        long st = currentTimeMillis();
        System.out.println("st=========="+st);

        // 讯飞语音初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5879bd38");
        initUmeng();

        long et = System.currentTimeMillis();
        System.out.println(et+"et=========="+(et-st));

    }

    private void initUmeng() {
        UMConfigure.init(this, ConstantValue.UMENG_APP_KEY, ConstantValue.UMENG_CHANNEL, 0, null);
    }


}
