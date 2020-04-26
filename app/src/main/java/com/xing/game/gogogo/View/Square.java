package com.xing.game.gogogo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xing.game.gogogo.Method.DensityUtil;
import com.xing.game.gogogo.Method.ScreenMethod;
import com.xing.game.gogogo.R;

/**
 * Created by wangxing on 16/1/4.
 * 游戏中的矩形障碍
 *
 * 已被Missile代替
 */
public class Square extends ImageView {

    /**
     * 障碍物的移动方向，false为向右移动，true为左
     */
    private boolean moveDirection = false;

    private int MaxMoveConstant;

    private Context context;

    public Square(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setupView();
    }

    public Square(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setupView();
    }

    public Square(Context context) {
        super(context);
        this.context = context;
        setupView();
    }

    private void setupView(){
        setImageResource(R.drawable.square);
        MaxMoveConstant = ScreenMethod.getScreenWidth()- DensityUtil.dip2px(context,80);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(100,32);
//    }

    public void move(){
        if(moveDirection){
            if (getX() > 0){
                setX(getX() - 1);
            }else {
                moveDirection = false;
            }
        }else {
            if (getX() < MaxMoveConstant){
                setX(getX() + 1);
            }else {
                moveDirection = true;
            }
        }
    }
}
