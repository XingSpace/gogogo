package com.xing.game.gogogo.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;

import com.xing.game.gogogo.Method.CloseApp;
import com.xing.game.gogogo.Method.ScreenMethod;
import com.xing.game.gogogo.R;


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
 * ━━━━━━神兽出没━━━━━━by:Xing
 */

/**
 * Created by wangxing on 15/12/11.
 * android 游戏
 * gogogo 项目
 * 程序的欢迎界面
 */
public class GameWelcome extends ActivityBase{

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamewelcome);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScreenMethod.setScreenWidth(size.x);
        ScreenMethod.setScreenHeight(size.y);
        //将欢迎界面的activity加入到自制的activity栈中，以方便之后的退出
        CloseApp.addActivity(this);
    }

    /**
     * 当activity可见后
     * 将开场画面延迟1.5秒，随后打开操作主界面
     */
    @Override
    protected void onStart() {
        super.onStart();
        closeWalcome.sendEmptyMessageDelayed(0, 1500);
    }

    /**
     * 结束欢迎界面
     */
    private Handler closeWalcome = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            Intent intent = new Intent();
            intent.setClass(mContext,ActivityMenu.class);
            startActivity(intent);
        }
    };

    @Override
    protected void findViews() {
    }

}







