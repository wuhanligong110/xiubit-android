package com.toobei.common.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.view.timeselector.Utils.TextUtil;

/**
 * 公司: tophlc
 * 类说明：标题-内容-确认-取消
 *
 * @date 2015-10-27
 */
public class PromptDialogCommon extends Dialog implements View.OnClickListener {
    private String positiveBtnStr;
    private String negativeBtnStr = "取消";
    private Activity mActivity;
    private String title;
    private String content;
    private Button btnPositive, btnNegative;
    private int btnTextColor = 0;
    private int negativebtnTextColor = 0;
    private PositiveClicklistener positiveClicklistener;
    private NegativeClicklistener negativeClicklistener;
    private boolean isNoButtom;

    public PromptDialogCommon(Activity context, String title, String content, String positiveBtnStr) {
        super(context, R.style.customDialog);
        this.mActivity = context;
        this.content = content;
        this.positiveBtnStr = positiveBtnStr;
        this.title = title;

    }

    public PromptDialogCommon(Activity context, String title, String content,boolean isNoButtom) {
        super(context, R.style.customDialog);
        this.mActivity = context;
        this.content = content;
//        this.positiveBtnStr = positiveBtnStr;
        this.title = title;
        this.isNoButtom = isNoButtom;
    }

    public PromptDialogCommon(Activity context, String title, String content, String positiveBtnStr,String negativeBtnStr) {
        super(context, R.style.customDialog);
        this.mActivity = context;
        this.content = content;
        this.positiveBtnStr = positiveBtnStr;
        this.negativeBtnStr = negativeBtnStr;
        this.title = title;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mActivity).inflate(
                R.layout.layout_prompt_dialog_common, null);
        this.setContentView(view);
        TextView textTitle = (TextView) findViewById(R.id.text_title);
        TextView textServicePhone = (TextView) findViewById(R.id.text_content);
        View bottomLl = findViewById(R.id.bottomLl);
        textTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        textTitle.setText(title);
        textServicePhone.setText(content);

        if (isNoButtom){
            bottomLl.setVisibility(View.GONE);
            return;
        }else {
            bottomLl.setVisibility(View.VISIBLE);
        }

        btnPositive = (Button) findViewById(R.id.positiveButton);
        if (!TextUtils.isEmpty(positiveBtnStr)) {
            btnPositive.setText(positiveBtnStr);
            btnPositive.setOnClickListener(this);
        }else {
            btnPositive.setVisibility(View.GONE);
            findViewById(R.id.lineCenter).setVisibility(View.GONE);
        }

        btnNegative = (Button) findViewById(R.id.negativeButton);
        if (!TextUtil.isEmpty(negativeBtnStr)){
            btnNegative.setText(negativeBtnStr);
            btnNegative.setOnClickListener(this);
        }else {
            btnNegative.setVisibility(View.GONE);
            findViewById(R.id.lineCenter).setVisibility(View.GONE);
        }

        if (btnTextColor > 0) {
            btnPositive.setTextColor(ContextCompat.getColor(mActivity,btnTextColor));
        }

        if (negativebtnTextColor > 0) {
            btnNegative.setTextColor(ContextCompat.getColor(mActivity,negativebtnTextColor));
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.positiveButton) {
            if (this.positiveClicklistener != null) {
                positiveClicklistener.onPositiveClick();
            }

        } else if (v.getId() == R.id.negativeButton) {
            if (this.negativeClicklistener != null){
                negativeClicklistener.onNegativeClick();
            }
        }
        dismiss();
    }


    public PromptDialogCommon setBtnPositiveClickListener(PositiveClicklistener positiveClicklistener) {
        this.positiveClicklistener = positiveClicklistener;
        return this;
    }

    public PromptDialogCommon setBtnNegativeClickListener(NegativeClicklistener negativeClicklistener) {
        this.negativeClicklistener = negativeClicklistener;
        return this;
    }

    public interface PositiveClicklistener {

        void onPositiveClick();
    }

    public interface NegativeClicklistener {

        void onNegativeClick();
    }


    /**
     * 功能：设置按键字体颜色 在show前調用
     *
     * @param btnTextColorResource
     */
    public PromptDialogCommon setBtnPositiveColor(int btnTextColorResource) {
        btnTextColor = btnTextColorResource;
        return this;
    }

    public PromptDialogCommon setBtnNegativeColor(int btnTextColorResource) {
        negativebtnTextColor = btnTextColorResource;
        return this;
    }

    public void setBtnNegativeText(String text){
        btnNegative.setText(text);
    }

    @Override
    public void show() {
        if (mActivity.isFinishing()) return;
        super.show();
        
    }
}