package com.htaka.higherlower;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.MobileAds;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class MenuActivity extends Activity {
    private MediaPlayer mp = null;
    SurfaceView mSurfaceView=null;
    final private UnityAdsListener unityAdListener=new UnityAdsListener();
    InterstitialAd mInterstitialAd;
    Button startBtn,displayBtn;
    TextView scoreTxt,waitTxt,commentTxt;
    Intent i;
    int score = 0;
    int videoTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startBtn=(Button)findViewById(R.id.button);
        displayBtn=(Button)findViewById(R.id.rewardedBtn);
        commentTxt=(TextView)findViewById(R.id.scoreText);
        waitTxt=(TextView)findViewById(R.id.waitText);
        scoreTxt=(TextView)findViewById(R.id.commenTxt);
        mp = new MediaPlayer();
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);

        LoadAllAds();

        LoadContentComments();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadInterstitialAd();
            }
        });

        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardedVideoAd();
            }
        });
    }

    private void LoadContentVideo() {
        VideoView mVid= (VideoView) findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://com.htaka.higherlower/"+videoTxt);
        mVid.setVideoURI(video);
        mVid.requestFocus();
        mVid.start();
    }

    private void LoadContentComments() {
        i = getIntent();
        score=i.getIntExtra("Score",score);
        commentTxt.setText(""+score);

        if(score >= 0 && score < 5) {
            scoreTxt.setText("ولك يا خرااااا");
            videoTxt = R.raw.yakhara;
        }
         if(score >= 5 && score < 16) {
            scoreTxt.setText("مش عاطل , يعني لو قالولك شو عملت بحياتك, قول وصلت ");
            videoTxt = R.raw.soalthane;
        }
        else if(score >= 16 && score < 25) {
            scoreTxt.setText("لااااا هيك كثير تقريبا ١٪ من الناس يوصلو النتيجة ");
            videoTxt = R.raw.dancing;
        }
        else if(score >= 25 && score < 37) {
            scoreTxt.setText("الرجاء الاتصال بالاطفاء لان هذا الانسان ولعها");
            videoTxt = R.raw.takhmes;
        }
        else if(score >= 37 && score < 49) {
            scoreTxt.setText("صيد موفق هل انت من الصيادين المعروفين بلعل الالعاب");
            videoTxt = R.raw.jono;
        }
        else if(score >= 49 && score < 56) {
            scoreTxt.setText("هل انت من فريق المحظوظين أم انك فقط بارع بالالعاب");
            videoTxt = R.raw.happy;
        }
        else if(score >= 56 && score < 64) {
            scoreTxt.setText("الى النصف النهائي هذا غير معقول أظن انك سمارت بوي");
            videoTxt = R.raw.cashvid;
        }
        else if(score >= 64 && score < 73) {
            scoreTxt.setText("اللعبة نفسها لا تصدق, تقول انك غشاش, لكن انا اصدق بانك وصلت الى هنا");
            videoTxt = R.raw.koldance;
        }
        else if(score >= 73 && score < 85) {
            scoreTxt.setText("لديك مستقبل باهر في هذه اللعبة ");
            videoTxt = R.raw.koldancea;
        }
        else if(score >= 92 && score < 100) {
            scoreTxt.setText("لا بد من أنك عبقري يا اخي العزيز لقد ربحت معنا كيس مليء بالأكياس");
            videoTxt = R.raw.koldanceb;
        }
        else if(score >= 100 && score < 111) {
            scoreTxt.setText("لاااااااااااا القليل فقط وكنت أنهيت اللعبة حاول لا تيأس أنك لها");
            videoTxt = R.raw.dancingfail;
        }
        else if(score == 111) {
            scoreTxt.setText("لقد أنهيت اللعبة بنجاح تهاني لك, أرجو أنك أحببت اللعبة وأمضيت فيها وقت سعيد");
            videoTxt = R.raw.kaseda;
        }
        LoadContentVideo();
    }

    private void LoadAllAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3120105269514394/6538917461");
        //Rewarded Ad from unity using Admob
        UnityAds.initialize(this,"1132400",unityAdListener);

        //Interstitial Ad from AdMob
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3120105269514394/8015650661");
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                beginPlayingGame(0);
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void loadRewardedVideoAd() {
        if(UnityAds.isReady())
        {
            UnityAds.show(this);
        }else{
            displayBtn.setEnabled(false);
        }
    }

    private void loadInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            beginPlayingGame(0);
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void beginPlayingGame(int sc){
        i=new Intent(MenuActivity.this,MainActivity.class);
        i.putExtra("Score",sc);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
    }

    private class UnityAdsListener implements IUnityAdsListener{
        @Override
        public void onUnityAdsReady(String s) {
            displayBtn.setText("شاهد فيديو وأكمل اللعب");
            waitTxt.setText(" ");
            displayBtn.setEnabled(true);
        }

        @Override
        public void onUnityAdsStart(String s) {

        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
            if(finishState != UnityAds.FinishState.SKIPPED){
                beginPlayingGame(score);
            }
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
            Log.d("Ads Unity",s);
        }
    }
}
