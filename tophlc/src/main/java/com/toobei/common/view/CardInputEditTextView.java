package com.toobei.common.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.Logger;

/**
  * 类说明: 银行卡输入的EditTextView
  *@author Administrator
  *@time 2016/11/10 0010 下午 2:12
  */
public class CardInputEditTextView extends ClearEditText {

    public CardInputEditTextView(Context ctx) {
        this(ctx, null);
    }

    public CardInputEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(watcher);
    }

    public CardInputEditTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs,defStyleAttr);
        addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Logger.d("s:" + s + ",start:" + start + ",before:" + before + ",count:" + count);
            if (s == null) {
                return;
            }
            //判断是否是在中间输入，需要重新计算
            boolean isMiddle = (start + count) < (s.length());
            //在末尾输入时，是否需要加入空格
            boolean isNeedSpace = false;
            if (!isMiddle && s.length() > 0 && s.length() % 5 == 0) {
                isNeedSpace = true;
            }
            if (isMiddle || isNeedSpace) {
                String newStr = s.toString();
                newStr = newStr.replace(" ", "");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < newStr.length(); i += 4) {
                    if (i > 0) {
                        sb.append(" ");
                    }
                    if (i + 4 <= newStr.length()) {
                        sb.append(newStr.substring(i, i + 4));
                    } else {
                        sb.append(newStr.substring(i, newStr.length()));
                    }
                }
                removeTextChangedListener(watcher);
                setText(sb);
                //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
                if(!isMiddle || count > 1){
                    setSelection(sb.length());
                } else if (isMiddle) {
                    //如果是删除
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        if ((start - before + 1) % 5 == 0) {
                            setSelection((start - before) > 0 ? start - before : 0);
                        } else {
                            setSelection((start - before + 1) > sb.length() ? sb.length() : (start - before + 1));
                        }
                    }
                    //如果是增加
                    else {
                        if ((start - before + count) % 5 == 0) {
                            setSelection((start + count - before + 1) < sb.length() ? (start + count - before + 1) : sb.length());
                        } else {
                            setSelection(start + count - before);
                        }
                    }
                }
                addTextChangedListener(watcher);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public String getTextWithoutSpace(){
        return super.getText().toString().replace(" ","");
    }
}
