package hong.cantonese;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.debug.UMLogUtils;

import java.util.ArrayList;

import hong.cantonese.adapter.SentenceAdapter;
import hong.cantonese.cache.CacheManager;
import hong.cantonese.utils.LogUtil;
import hong.cantonese.utils.PermissionUtil;
import hong.cantonese.voicereco.Synthesizer;
import hong.cantonese.voicereco.VoiceReco;
import hong.cantonese.voicereco.bean.ClearWord;
import hong.cantonese.voicereco.bean.RecResult;
import hong.cantonese.voicereco.bean.Word;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_CODE_SEETTING = 1002;
    private VoiceReco voiceReco;
    private EditText etVoiceText;
    public StringBuilder stringBuilder = new StringBuilder();
    private ImageView myVoiceDialog;
    private ArrayList<String> voiceTextList = new ArrayList<>();
    private ListView lvVoiceTexts;
    private SentenceAdapter sentenceAdapter;
    private Synthesizer synthesizer;
    private boolean isRecording = false;
    private ImageButton btVoiceReco;
    private Button btSpeak;
    public static int currentIndex = 0;
    private InputMethodManager imm;
    private Handler handler = new Handler();
    private long mLastExitTime;
    private DrawerLayout drawer;
    private AdView adView_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long st = currentTimeMillis();
        System.out.println("st=onCreate=========" + st);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> listFromFile = CacheManager.getInstance().getSentenceListFromFile();
        if (listFromFile != null && listFromFile.size() > 0 && !TextUtils.isEmpty(listFromFile.get(0))) {
            voiceTextList = listFromFile;
        }
        lvVoiceTexts = findViewById(R.id.lv_voicetext);
        sentenceAdapter = new SentenceAdapter(voiceTextList, this);
        lvVoiceTexts.setAdapter(sentenceAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        etVoiceText = findViewById(R.id.et_voicetext);
        btVoiceReco = findViewById(R.id.bt_voice_reco);
        adView_main = findViewById(R.id.adView_main);
        btSpeak = findViewById(R.id.bt_speak);

        myVoiceDialog = findViewById(R.id.iv_voice_tip);
        MyClickListener recogListener = new MyClickListener();
        btVoiceReco.setOnClickListener(recogListener);
        btSpeak.setOnClickListener(recogListener);

        lvVoiceTexts.setOnItemClickListener(mItemListener);

        voiceReco = new VoiceReco(MainActivity.this);
        synthesizer = new Synthesizer(MainActivity.this);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        long et = System.currentTimeMillis();
        System.out.println(et + "et=onCreate=========" + (et - st));

        PermissionUtil.INSTANCE.requestPermission(this, new String[]{Manifest.permission.RECORD_AUDIO}, ConstantValue.PERMISSION_CODE_MIC);

    }


    private AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String sentence = voiceTextList.get(position);
            synthesizer.startSynthesize(sentence);
            etVoiceText.setText(sentence);
            currentIndex = position;

        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cantonese) {
            GloabalParams.SynthesizerLanguage = ConstantValue.CANTONESE;
            GloabalParams.RecorgnizeLanguage = ConstantValue.LANGUAGE_CN;
            GloabalParams.RecorgnizeAccent = ConstantValue.ACCENT_MANDARIN;
            synthesizer.initSynthesizer();
            voiceReco.initVoiceRecorgnize();
        } else if (id == R.id.nav_english) {
            GloabalParams.SynthesizerLanguage = ConstantValue.ENGLISH;
            GloabalParams.RecorgnizeLanguage = ConstantValue.LANGUAGE_EN;
            GloabalParams.RecorgnizeAccent = ConstantValue.ACCENT_NULL;
            synthesizer.initSynthesizer();
            voiceReco.initVoiceRecorgnize();
            Toast.makeText(this, R.string.en_mode_tip, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_sichuan) {
            GloabalParams.SynthesizerLanguage = ConstantValue.SICHUAN;
            GloabalParams.RecorgnizeLanguage = ConstantValue.LANGUAGE_CN;
            GloabalParams.RecorgnizeAccent = ConstantValue.ACCENT_MANDARIN;
            synthesizer.initSynthesizer();
            voiceReco.initVoiceRecorgnize();

        } else if (id == R.id.nav_mandarin) {
            GloabalParams.SynthesizerLanguage = ConstantValue.MANDARIN;
            GloabalParams.RecorgnizeLanguage = ConstantValue.LANGUAGE_CN;
            GloabalParams.RecorgnizeAccent = ConstantValue.ACCENT_MANDARIN;
            synthesizer.initSynthesizer();
            voiceReco.initVoiceRecorgnize();

        } else if (id == R.id.nav_share) {
            share();

        } else if (id == R.id.nav_send) {
            marketComment();
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 分享
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "粤语自己学");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐一个练习粤语和英语的应用（粤语自己学），下载请到应用商店或戳此链接：http://a.app.qq.com/o/simple.jsp?pkgname=hong.cantonese");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    // 调起应用市场评论
    private void marketComment() {
        try {
            Uri uri = Uri.parse("market://details?id="
                    + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "手机没有安装应用市场！", Toast.LENGTH_SHORT).show();
        }
    }


    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_voice_reco:
                    if (!PermissionUtil.INSTANCE.requestPermission(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, ConstantValue.PERMISSION_CODE_MIC)) {
                        return;
                    }

                    if (!isRecording) {
                        isRecording = true;
                        myVoiceDialog.setVisibility(View.VISIBLE);
                        btVoiceReco.setImageResource(R.drawable.bg_voice_recognize_unpre);
                        stringBuilder.delete(0, stringBuilder.capacity());
                        etVoiceText.setText(stringBuilder);
                        if (voiceReco != null) {
                            voiceReco.startListening(mRecoListener);
                        }

                    } else {

                        isRecording = false;
                        voiceReco.stopListening();
                        btVoiceReco.setImageResource(R.drawable.bg_voice_recognize_pre);
                        myVoiceDialog.setVisibility(View.GONE);
                        if (stringBuilder.capacity() > 2 && !voiceTextList.contains(stringBuilder.toString()) && !TextUtils.isEmpty(stringBuilder.toString())) {
                            voiceTextList.add(0, stringBuilder.toString());
                            sentenceAdapter.setmVoiceTextList(voiceTextList);
                            synthesizer.startSynthesize(stringBuilder.toString());
                        }

                    }
                    break;

                case R.id.bt_speak:
                    String textString = etVoiceText.getText().toString();
                    if (textString.isEmpty()) {
                        return;
                    }
                    synthesizer.startSynthesize(textString);
                    if (voiceTextList.size() > currentIndex) {
                        if (!voiceTextList.contains(textString)) {
                            voiceTextList.set(currentIndex, textString);
                        }
                    } else {
                        voiceTextList.add(textString);
                    }
                    sentenceAdapter.setmVoiceTextList(voiceTextList);
                    imm.hideSoftInputFromWindow(etVoiceText.getWindowToken(), 0);
                    break;

            }
        }
    }


    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {


        //听写结果回调接口(返回Json格式结果，用户可参见附录14.1)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        //关于解析Json的代码可参见Demo中JsonParser类；
        //isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            Gson gson = new Gson();
            RecResult recResult = gson.fromJson(results.getResultString(), RecResult.class);
            ArrayList<Word> ws = recResult.getWs();
            for (Word w : ws) {
                ArrayList<ClearWord> cw = w.getCw();
                for (ClearWord clearWord : cw) {
                    String cw1 = clearWord.getW();
                    if (!TextUtils.isEmpty(cw1) && !cw1.equals("。"))
                        stringBuilder.append(cw1);
                }
            }
            etVoiceText.setText(stringBuilder);
            currentIndex = 0;

            if (isLast && voiceReco != null) {
                isRecording = false;
                myVoiceDialog.setVisibility(View.GONE);
                btVoiceReco.setImageResource(R.drawable.bg_voice_recognize_pre);
                if (stringBuilder.capacity() > 2 && !voiceTextList.contains(stringBuilder.toString()) && !TextUtils.isEmpty(stringBuilder.toString())) {
                    voiceTextList.add(0, stringBuilder.toString());
                    sentenceAdapter.setmVoiceTextList(voiceTextList);
                    synthesizer.startSynthesize(stringBuilder.toString());
                }


            }

        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            System.out.println(error.getPlainDescription(true));
            //获取错误码描述
        }

        //开始录音
        public void onBeginOfSpeech() {
            System.out.println("onBeginOfSpeech");
        }

        //volume音量值0~30，data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            if (myVoiceDialog.getVisibility() == View.VISIBLE) {
                setImageTipByVolume(myVoiceDialog, volume);
            }
        }

        private void setImageTipByVolume(ImageView ivVoiceTip, int volume) {
            if (volume > 20) {
                ivVoiceTip.setImageResource(R.drawable.speaking_tips_4);
            } else if (volume > 10) {
                ivVoiceTip.setImageResource(R.drawable.speaking_tips_3);
            } else if (volume > 5) {
                ivVoiceTip.setImageResource(R.drawable.speaking_tips_2);
            } else {
                ivVoiceTip.setImageResource(R.drawable.speaking_tips_1);
            }

        }

        //结束录音
        public void onEndOfSpeech() {
            System.out.println("onEndOfSpeech");
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
        loadAdView();
    }


    private void loadAdView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_main.loadAd(adRequest);
        adView_main.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                LogUtil.d(this, "onAdLoaded()");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ArrayList<String> arrayList = sentenceAdapter.getmVoiceTextList();
        if (arrayList != null) {
            CacheManager.getInstance().saveSentenceListToFile(arrayList);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }


        if (System.currentTimeMillis() - mLastExitTime < 2000) {
            super.onBackPressed();
        } else {
            mLastExitTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.tip_click_again_exist, Toast.LENGTH_LONG)
                    .show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = false;
        for (int result : grantResults) {
            LogUtil.d(this, "result = " + result);
            if (result == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
            }
        }
        LogUtil.d(this, "requestCode = " + requestCode);
        if (!isGranted) {
            boolean showExplain = PermissionUtil.INSTANCE.shouldShowExplain(this, permissions);
            LogUtil.d(this, "showExplain = " + showExplain);
            if (showExplain) {
                Toast.makeText(this, R.string.request_permission_mic, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.request_permission_mic, Toast.LENGTH_LONG).show();
                PermissionUtil.INSTANCE.forwardSetting(this, PERMISSION_CODE_SEETTING);
            }
        }

    }
}
