package hong.cantonese.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import hong.cantonese.R;

/**
 * Created by hongenit on 2017/1/15.
 */

public class MyVoiceDialog extends AlertDialog {


    private ImageView ivVoiceTip;

    public MyVoiceDialog(Context context) {
        super(context);
    }

    public MyVoiceDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MyVoiceDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_voice_tip);

        ivVoiceTip = (ImageView)findViewById(R.id.iv_voice_tip);
    }


    public void setImageTipByVolume(int volume){
       if (volume > 90) {
            ivVoiceTip.setImageResource(R.drawable.speaking_tips_4);
       }else if (volume > 60){
           ivVoiceTip.setImageResource(R.drawable.speaking_tips_3);
       }else if (volume > 30){
           ivVoiceTip.setImageResource(R.drawable.speaking_tips_2);
       }else{
           ivVoiceTip.setImageResource(R.drawable.speaking_tips_1);
       }

    }





}
