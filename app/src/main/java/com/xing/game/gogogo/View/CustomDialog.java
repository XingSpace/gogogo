package com.xing.game.gogogo.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
 *
 * Created by wangxing on 16/1/4.
 * 自定义的Dialog
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String LeftButtonText;
        private String RightButtonText;
        private View contentView;
        private DialogInterface.OnClickListener LeftButtonClickListener;
        private DialogInterface.OnClickListener RightButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public void setTitle(String s){
            this.title = s;
        }

        public void setMessage(String s){
            this.message = s;
        }

        public void setLeftButtonText(String s){
            this.LeftButtonText = s;
        }

        public void setRightButtonText(String s){
            this.RightButtonText = s;
        }

        public void setLeftButtonClickListener(DialogInterface.OnClickListener onClickListener){
            this.LeftButtonClickListener = onClickListener;
        }

        public void setRightButtonClickListener(DialogInterface.OnClickListener onClickListener){
            this.RightButtonClickListener = onClickListener;
        }

        /**
         * 使用时，需要先调用这个方法来实例化一个dialog
         * @return 返回一个已经实例化的dialog
         */
        public CustomDialog onCreate(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final CustomDialog customDialog = new CustomDialog(context,R.style.Dialog);

            View layout = inflater.inflate(R.layout.dialog_normal_layout,null);

            customDialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ((TextView)layout.findViewById(R.id.title)).setText(title);
            ((TextView)layout.findViewById(R.id.message)).setText(message);

            if (LeftButtonClickListener != null){
                ((Button)layout.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LeftButtonClickListener.onClick(customDialog,DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }

            if (RightButtonClickListener != null){
                ((Button)layout.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RightButtonClickListener.onClick(customDialog,DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }

            //设置为false之后，即便是按下back，点击屏幕，都不会让dialog消失
            customDialog.setCancelable(false);

            return customDialog;
        }

    }
}
