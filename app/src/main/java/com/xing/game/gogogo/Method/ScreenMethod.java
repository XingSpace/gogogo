package com.xing.game.gogogo.Method;

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
 * Created by wangxing on 15/12/30.
 * 关于屏幕信息的公共方法
 *
 * 本类中的所有数据都在GameWelcome中声明过了，所以使用时无需担心空指针
 */
public class ScreenMethod {

    /**
     * 屏幕的宽高
     */
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;


    public static void setScreenWidth(int screenWidth){
        SCREEN_WIDTH = screenWidth;
    }

    public static void setScreenHeight(int screenHeight){
        SCREEN_HEIGHT = screenHeight;
    }

    public static int getScreenWidth(){
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight(){
        return SCREEN_HEIGHT;
    }

}
