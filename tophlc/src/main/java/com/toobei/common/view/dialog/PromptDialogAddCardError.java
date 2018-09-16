package com.toobei.common.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.toobei.common.R;

/**
 * 公司: tophlc
 * 类说明：标题-内容-确认-取消
 *
 * @date 2015-10-27
 */
public class PromptDialogAddCardError extends Dialog implements View.OnClickListener {
    private String lastTimes;
    private Activity mActivity;
    private String title;
    private String content;


    public PromptDialogAddCardError(Activity context, String title, String content,String lastTimes) {
        super(context, R.style.customDialog);
        this.mActivity = context;
        this.content = content;
        this.lastTimes = lastTimes;
        this.title = title;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mActivity).inflate(
                R.layout.layout_prompt_dialog_bind_card_error, null);
        this.setContentView(view);
        TextView textTitle = (TextView) findViewById(R.id.text_title);
        TextView contantTv = (TextView) findViewById(R.id.text_content);
        TextView timesTv = (TextView) findViewById(R.id.times_end_tv);
        findViewById(R.id.negativeButton).setOnClickListener(this);
        textTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        textTitle.setText(title);
        contantTv.setText(content);
        timesTv.setText(lastTimes);
        
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void show() {
        if (mActivity.isFinishing()) return;
        super.show();
        
    }
}