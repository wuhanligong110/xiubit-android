package com.toobei.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.toobei.common.R;

public class PromptForThirdBindDialog extends Dialog implements View.OnClickListener {
    private Context ctx;

    private DialogBtnOnClickListener listener;

    public PromptForThirdBindDialog(Context context) {
        super(context, R.style.customDialog);
        this.ctx = context;
    }

    public interface DialogBtnOnClickListener {
        void onClicked(PromptForThirdBindDialog dialog, boolean isCancel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_prompt_dialog_third_bind, null);
        this.setContentView(view);
        Button btnNegative = (Button) findViewById(R.id.negativeButton);

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