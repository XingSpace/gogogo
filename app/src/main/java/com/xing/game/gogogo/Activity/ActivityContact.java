package com.xing.game.gogogo.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
 * ━━━━━━亦余心之所善兮，虽九死其犹未悔━━━━━━by:Xing
 * Created by wangxing on 16/1/22.
 * 关于或者说。。。联系我的界面，除了一个长按复制。。。
 */
public class ActivityContact extends ActivityBase {

    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    @Override
    protected void findViews() {
        email = (TextView)findViewById(R.id.email);
        email.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm =(ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                //将文本数据复制到剪贴板
                cm.setPrimaryClip(ClipData.newPlainText(null,email.getText()));
                Toast.makeText(mContext,"已成功复制",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
