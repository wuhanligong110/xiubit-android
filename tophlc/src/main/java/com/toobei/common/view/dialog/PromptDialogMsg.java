package com.toobei.common.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.utils.SystemFunctionUtil;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.view.timeselector.Utils.TextUtil;

public class PromptDialogMsg extends Dialog implements View.OnClickListener {
    private Context ctx;
    private String title;
    private String content;

    private String btnNegativeText;
    private DialogBtnOnClickListener listener;

    public PromptDialogMsg(Context context,String title, String content,
                           String btnNegativeText) {
        super(context, R.style.customDialog);
        this.ctx = context;
        this.title = title;
        this.content = content;
        this.btnNegativeText = btnNegativeText;
    }

    public PromptDialogMsg(Context context,String content,
                           String btnNegativeText) {
        super(context, R.style.customDialog);
        this.ctx = context;
        this.content = content;
        this.btnNegativeText = btnNegativeText;
    }
    public interface DialogBtnOnClickListener {
        void onClicked(PromptDialogMsg dialog, boolean isCancel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_prompt_dialog_msg, null);
        this.setContentView(view);
        TextView titleTv = (TextView) findViewById(R.id.text_title);
        TextView textContent = (TextView) findViewById(R.id.text_context);
        Button btnNegative = (Button) findViewById(R.id.negativeButton);

        if (TextUtil.isEmpty(title)){
            titleTv.setVisibility(View.GONE);
        }else{
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(title);
        }

        if (btnNegativeText == null) {
            btnNegative.setVisibility(View.GONE);
        } else {
            btnNegative.setText(btnNegativeText);
        }
        String str = content.toString();
        if (str.contains("400-888-6987")) {
            TextDecorator.decorate(textContent, str).makeTextClickable(new TextDecorator.OnTextClickListener() {
                @Override
                public void onClick(View view, String text) {
                    SystemFunctionUtil.INSTANCE.CallServicePhone((Activity) ctx, "4008886987");
                }
            }, false,"400-888-6987").setTextColor(R.color.text_blue_common,"400-888-6987").build();
            dismiss();
        } else {
            textContent.setText(str);
        }
        btnNegative.setOnClickListener(this);
    }

    public void setListener(DialogBtnOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.positiveButton) {
            if (listener != null) {
                listener.onClicked(this, false);
            }
        } else if (v.getId() == R.id.negativeButton) {
            if (listener != null) {
                listener.onClicked(this, true);
            }
        }
        dismiss();
    }

}