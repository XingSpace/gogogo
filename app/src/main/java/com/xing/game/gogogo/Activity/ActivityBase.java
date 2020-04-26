package com.xing.game.gogogo.Activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
 * Created by wangxing on 15/12/14.
 * 基础类用于实现接口
 */
public abstract class ActivityBase extends FragmentActivity{

    protected Context mContext;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        this.mContext = ActivityBase.this;

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.main);
        linearLayout.addView(getLayoutInflater().inflate(layoutResID, null),
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        findViews();
    }

    /**
     * 绑定控件的抽象方法
     */
    protected abstract void findViews();

}
