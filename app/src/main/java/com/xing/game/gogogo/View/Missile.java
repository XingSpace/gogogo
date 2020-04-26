package com.xing.game.gogogo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xing.game.gogogo.Method.DensityUtil;
import com.xing.game.gogogo.Method.ScreenMethod;
import com.xing.game.gogogo.Method.TargetRandomMove;
import com.xing.game.gogogo.R;

/**
 * Created by wangxing on 16/1/24.
 * 游戏用的导弹类
 */
public class Missile extends ImageView {

    /**
     * 首先判断这枚导弹是从那个方向来的
     * 默认从右边飞来
     * true为右向左，false为左向右
     */
    private boolean isRight = true;

    /**
     * 导弹飞行速度
     */
    private int speed;

    public static int SPEED_FAST = 4;
    public static int SPEED_SLOW = 2;

    /**
     * 是否开始攻击
     */
    private boolean isGoing = false;

    /**
     * 监听一次攻击的结束
     */
    private OverListenner overListenner;

    private Context context;

    public Missile(Context context) {
        super(context);
        this.context = context;
        setup();
    }

    public Missile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();
    }

    private void setup(){
        setImageResource(R.drawable.missileright);
        speed = DensityUtil.dip2px(context, 1);
    }

    /**
     * 进攻代码
     * 但是必须要先将isGoing改为true才能执行
     */
    public void move(){
        synchronized ("") {
            if (isGoing) {
                if (isRight) {
                    //如果是从右往左飞行
                    //只要x值还大于-72dp就继续飞（目的是飞过屏幕然后消失）
                    if (getX() > (-DensityUtil.dip2px(context, 72))) {
                        setX(getX() - speed);
                    } else {
                        //如果已经飞出屏幕边界
                        setX(-DensityUtil.dip2px(context, 72));//固定位置
                        setIsGoing(false);//让自己停下来
                        setIsRight(false);//让自己转一下方向（改成从左至右）
                        RandomY();
                        over();
                    }
                } else {
                    //如果是从左往右

                    if (getX() < ScreenMethod.getScreenWidth()) {
                        setX(getX() + speed);
                    } else {
                        setX(ScreenMethod.getScreenWidth());//
                        setIsGoing(false);
                        setIsRight(true);
                        RandomY();
                        over();
                    }
                }
            }
        }
    }

    private void over(){
        if (! (overListenner==null)){
            overListenner.Over();
        }
    }

    public void setOverListenner(OverListenner overListenner){
        this.overListenner = overListenner;
    }

    public interface OverListenner{
        public void Over();
    }

    /**
     * 设置一下导弹的出现位置
     */
    public void RandomY(){
        setY(TargetRandomMove.getMissileY(context));
    }

    public void setIsGoing(boolean isGoing) {
        this.isGoing = isGoing;
    }

    public boolean getIsGoing() {
        return isGoing;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
        if (isRight){
            setImageResource(R.drawable.missileright);
        }else {
            setImageResource(R.drawable.missileleft);
        }
    }

    public boolean getIsRight() {
        return isRight;
    }
}
