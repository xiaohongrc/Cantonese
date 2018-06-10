package hong.cantonese.voicereco;

import android.content.Context;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;

import hong.cantonese.GloabalParams;

/**
 * Created by hongenit on 2017/1/14.
 * desc:
 */

public class VoiceReco {


    private SpeechRecognizer mIat;

    private Context mContext;

    public VoiceReco(Context mContext) {
        this.mContext = mContext;
        initVoiceRecorgnize();
    }

    // 开始听写
    public void startListening(RecognizerListener mRecoListener){
        mIat.startListening(mRecoListener);
    }

    // 停止听写
    public void stopListening(){
        mIat.stopListening();
    }



    // 语音听写
    public void initVoiceRecorgnize() {
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, GloabalParams.RecorgnizeLanguage);
        mIat.setParameter(SpeechConstant.ACCENT, GloabalParams.RecorgnizeAccent);

    }


    InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int i) {
            System.out.println("onInit" + i);
        }
    };






}
