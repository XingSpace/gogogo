package com.xing.game.gogogo.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xing.game.gogogo.Method.DensityUtil;
import com.xing.game.gogogo.Method.ScreenMethod;
import com.xing.game.gogogo.R;

/**
 * Created by admin on 2015/12/16.
 * 球的对象，游戏中的球都是这个类
 */
@SuppressLint("AppCompatCustomView")
public class Ball extends ImageView {

    /**
     * 球能跳到的最高位置
     */
    private int moveConstant = 0;

    /**
     * 判断向左还是向右，0为左，1为右
     */
    private int i;

    /**
     * 判断ball能否左右移动，false为不可移动，true为可以移动
     */
    private boolean isMove = false;
    /**
     * 即为该对象能够跳动的最高位置
     */
    private float MaxJumpConstant;

    /**
     * 本对象在布局中的初始Y轴位置
     */
    private float beforeY;

    /**
     * 判断对象是在升起过程中还是降落过程
     * true为上升过程，false为下降过程
     */
    private boolean isJump = true;

    private int imageResource;

    private int SpeedStage = 6;

    private Context context;

    public Ball(Context context) {
        super(context);
        this.context = context;
        setupView();
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setupView();
    }

    public Ball(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setupView();
    }

    private void setupView(){
        setImageResource(R.drawable.ball);
        moveConstant = ScreenMethod.getScreenHeight();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        imageResource = resId;
    }

    public int getImageResource(){
        return imageResource;
    }

    public int getMoveConstant(){
        return moveConstant;
    }

    public void setMoveConstant(Context context, int moveConstant){
        //将球所能到达的最高处，设置为屏幕的最高处
        this.moveConstant = moveConstant - DensityUtil.dip2px(context,60);
    }

    //加速
    public void addSpeed(){
        SpeedStage++;
    }

    /**
     * 实例化对象后，必须先将对象的getY值传入
     * @param Y 对象的getY值
     */
    public void setMaxJumpConstant(float Y){
        this.beforeY = Y;
        this.MaxJumpConstant = Y - moveConstant;
    }

    /**
     * 让Ball跳动的代码，每调用一次就使Y轴变化一个单位长度
     * 最大跳跃高度为100，基本控制：每 10-17 毫秒调用一次即可
     */
    public void jump(){
        if(isJump){
            //当状态对上升过程时，Y轴发生改变减小
            if ( (getY()-(1+SpeedStage/2)) >= MaxJumpConstant ){
                setY(getY()-(1+SpeedStage/2));
            }else {
                //当上升达到极限时，开始转向下降过程
                isJump = false;
            }
        }else {
            if ((getY()+(1+SpeedStage/2)) <= beforeY){
                setY(getY()+(1+SpeedStage/2));
            }else {
                isJump = true;
            }
        }
    }

    /**
     * 游戏循环中，开启此方法用于移动对象
     */
    public void move(){
        if(isMove){
            if (i == 0){
                //只有当球没有越出左边界时才能向左
                if (getX() > 0){
                    //当i为0表示向左移动一个单位
                    setX(getX()-(1+SpeedStage/2));
                }
            }else if(i == 1){
                //只有当球没有越出右边界时向右
                if (getX() < ScreenMethod.getScreenWidth()-DensityUtil.dip2px(context,60)){
                    //当i为1表示向右移动一个单位
                    setX(getX()+(1+SpeedStage/2));
                }
            }
        }
    }

    /**
     * @param i 当i为0时表示向左移动，为1时表示向右移动
     */
    public void setI(int i){
        this.i = i;
    }

    /**
     * @param b 只有当传入值为true时，才会相应移动事件，否则不会触发
     */
    public void setIsMove(boolean b){
        this.isMove = b;
    }

}
