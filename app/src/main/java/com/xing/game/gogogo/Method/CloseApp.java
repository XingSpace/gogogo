package com.xing.game.gogogo.Method;


import android.app.Activity;

import java.util.ArrayList;
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
 * ━━━━━━神兽出没━━━━━━by:Xing
 *
 * Created by wangxing on 16/1/4.
 * 用于关闭已经打开的所有activity
 */
public class CloseApp {

    private static List<Activity> list = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        list.add(activity);
    }

    public static void exit(){
        for (Activity activity:list){
            activity.finish();
        }
        System.exit(0);
    }

}
