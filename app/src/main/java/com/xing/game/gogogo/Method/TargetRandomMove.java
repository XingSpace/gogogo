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

import android.content.Context;

import java.util.Random;

/**
 * Created by wangxing on 15/12/30.
 * 随机的给出一个坐标
 * 让目标球有地方着陆
 *
 * 本来是楼上的意思，但是我懒得新建一个类了（取名字秋烦）
 * 于是本类加多一个静态方法，用于给出导弹的随机Y轴值
 */
public class TargetRandomMove {

    /**
     *
     * @return 返回一个数组，对方接收到数组后，【0】为x值，【1】为y值
     */
    public static int[] getXY(Context context){

        int[] ints = new int[2];

        //球可以出现地方的Y轴最大值
        int yMax = ScreenMethod.getScreenHeight()-DensityUtil.dip2px(context, 30);
        //球可以出现的地方的Y轴最小值
        int yMin = DensityUtil.dip2px(context,30);

        int xMax = ScreenMethod.getScreenWidth()-DensityUtil.dip2px(context, 30);
        int xMin = DensityUtil.dip2px(context,30);

        Random random = new Random();

        ints[0] = xMin + random.nextInt(xMax-DensityUtil.dip2px(context,30));
        ints[1] = yMin + random.nextInt(yMax-DensityUtil.dip2px(context,30));

        return ints;
    }

    /**
     *
     * @param context
     * @return 返回导弹的随机Y轴攻击值
     */
    public static int getMissileY(Context context){

        Random random = new Random();

        int i = random.nextInt(ScreenMethod.getScreenHeight() - DensityUtil.dip2px(context,32));

        return i;
    }

    /**
     * @return 随机导弹机制
     */
    public static boolean getMissileGoing(){

        boolean b = false;

        Random random = new Random();

        int i = random.nextInt(10);

        if(i >= 5){
            b = true;
        }

        return b;
    }

    public static int getHalf(){
        Random random = new Random();
        int i = random.nextInt(2);
        return i;
    }

}
