package com.xing.game.gogogo.Activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.appx.BDInterstitialAd;
import com.xing.game.gogogo.Method.CloseApp;
import com.xing.game.gogogo.Method.MathCyclePath;
import com.xing.game.gogogo.R;

import java.util.List;
/**
 *
 *----------Dragon be here!----------/
 * ┏┛   ┻━━━┛    ┻┓
 * ┃ ｜｜｜｜｜｜｜ ┃
 * ┃       -      ┃
 * ┃   ┳┛    ┗┳   ┃
 * ┃              ┃
 * ┃       ┻      ┃
 * ┃              ┃
 * ┗━┓          ┏━┛
 *   丨    神   丨
 *   丨    兽   丨
 *   丨    保   丨
 *   丨    佑   丨
 *   丨　　　    ┗━━━┓
 *　　┃代码无ＢＵＧ　　┣┓
 *　　┃！！！！！！　　┃
 *　　┗┓ ┓　　　┏━┳┓┏┛
 *　 　┃┫┫　　　┃┫┫
 *　　 ┗┻┛　　　┗┻┛
 * ━━━━━━亦余心之所善兮，虽九死其犹未悔━━━━━━by:Xing
 * Created by wangxing on 16/1/5.
 * 游戏的菜单界面:
 *
 * start 开始按钮
 */
public class ActivityMenu extends ActivityBase implements View.OnClickListener{

    private ImageView ball,ball2,contact;

    private Button start;

    /**
     * 为按钮设置音效
     */
    private SoundPool soundPool;
    private int soundID;

    private MathCyclePath mathCyclePath;

    private List<float[]> list;

    /**
     * 两个球的运转起始位置
     */
    private int where = 0;
    private int where2;

    private boolean b = true;

    private BDInterstitialAd appxInterstitialAdView;

    private String TAG = "advertisement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mathCyclePath = new MathCyclePath();
        list = mathCyclePath.getPath();
        where2 = list.size()/2;

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (b){
                    handler.sendMessage(new Message());
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            doit();
        }
    };
    private synchronized void doit(){

        if (where < list.size()-1){

        }else {
            where = 0;
        }

        ball.setX(list.get(where)[0]-ball.getHeight()/2);
        ball.setY(list.get(where)[1]-ball.getHeight()/2);
        where++;

        if(where2 < list.size()-1){

        }else {
            where2 = 0;
        }
        ball2.setX(list.get(where2)[0]-ball.getHeight()/2);
        ball2.setY(list.get(where2)[1]-ball.getHeight()/2);
        where2++;

    }

    @Override
    protected void findViews() {
        contact = (ImageView)findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID,1,1,0,0,1);
                Intent intent = new Intent();
                intent.setClass(mContext,ActivityContact.class);
                startActivity(intent);
            }
        });
        ball = (ImageView)findViewById(R.id.menu_ball);
        ball2 = (ImageView)findViewById(R.id.menu_ball2);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,5);
        soundID = soundPool.load(mContext,R.raw.btn,1);
        start = (Button)findViewById(R.id.btn_start);
        start.setOnClickListener(this);

        appxInterstitialAdView = new BDInterstitialAd(this,
                "nbFn3QLwrTnV5bDV86kms55z1j7sYRKd", "nsF3THBIKaG1G5iGwGBZHQaG");

        appxInterstitialAdView.setAdListener(new BDInterstitialAd.InterstitialAdListener() {

            @Override
            public void onAdvertisementDataDidLoadFailure() {
                Log.e(TAG, "load failure");
            }

            @Override
            public void onAdvertisementDataDidLoadSuccess() {
                Log.e(TAG, "load success");
            }

            @Override
            public void onAdvertisementViewDidClick() {
                Log.e(TAG, "on click");
            }

            @Override
            public void onAdvertisementViewDidHide() {
                Log.e(TAG, "on hide");
            }

            @Override
            public void onAdvertisementViewDidShow() {
                Log.e(TAG, "on show");
            }

            @Override
            public void onAdvertisementViewWillStartNewIntent() {
                Log.e(TAG, "leave");
            }

        });
        appxInterstitialAdView.loadAd();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (appxInterstitialAdView.isLoaded()) {
                appxInterstitialAdView.showAd();
            } else {
                Log.i(TAG, "AppX Interstitial Ad is not ready");
                appxInterstitialAdView.loadAd();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CloseApp.exit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CloseApp.exit();
    }

    @Override
    public void onClick(View view) {
        soundPool.play(soundID, 1, 1, 0, 0, 1);
        Intent intent = new Intent();
        intent.setClass(mContext,GameMain.class);
        startActivityForResult(intent,1);
    }

}
