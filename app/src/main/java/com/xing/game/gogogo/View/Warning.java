package com.xing.game.gogogo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ant.liao.GifView;
import com.xing.game.gogogo.Method.DensityUtil;
import com.xing.game.gogogo.Method.ScreenMethod;
import com.xing.game.gogogo.R;

/**
 * Created by wangxing on 16/1/25.
 * 设置导弹出现前的警告
 *
 * 本类核心就是组合了两个GifView，通过控制两个view的是否可见，以适配导弹方向
 */
public class Warning extends FrameLayout{

    private GifView gifViewLeft,gifViewRight;

    private Context context;

    private boolean isRight = true;

    public Warning(Context context) {
        super(context,null);
    }

    public Warning(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.warning, this, true);

        gifViewLeft = (GifView)findViewById(R.id.left);
        gifViewRight = (GifView)findViewById(R.id.right);

        gifViewLeft.setGifImage(R.drawable.gif2);
        gifViewRight.setGifImage(R.drawable.gif1);
        gifViewLeft.setGifImageType(GifView.GifImageType.WAIT_FINISH);
        gifViewRight.setGifImageType(GifView.GifImageType.WAIT_FINISH);

        gifViewLeft.setVisibility(View.GONE);
        gifViewRight.setVisibility(View.GONE);
    }


    public void setIsRight(boolean b){
        this.isRight = b;
        if(isRight){
            showRight();
            setX(ScreenMethod.getScreenWidth()- DensityUtil.dip2px(context, 48));
        }else {
            showLeft();
            setX(0);
        }
    }

    private void showLeft(){
        gifViewLeft.setVisibility(View.VISIBLE);
        gifViewRight.setVisibility(View.GONE);
    }

    private void showRight(){
        gifViewLeft.setVisibility(View.GONE);
        gifViewRight.setVisibility(View.VISIBLE);
    }
}
