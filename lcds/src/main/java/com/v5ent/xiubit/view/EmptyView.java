package com.v5ent.xiubit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/10
 */

public class EmptyView extends RelativeLayout {
    private  Context ctx;
    private View contantView;
    private View mRootView;
    private ImageView mImg;
    private TextView mText;
    private Button mBtn;

    public EmptyView(Context context) {
        super(context);
        ctx = context;
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, null, false);
        initView();
        addView(mRootView);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, null, false);
        initView();
        addView(mRootView);
    }

    private void initView() {
        mImg = (ImageView) mRootView.findViewById(R.id.img);
        mText = (TextView) mRootView.findViewById(R.id.text);
        mBtn = (Button) mRootView.findViewById(R.id.btn);
    }

    public void setContantView(View view){
        contantView = view;
    }
    
    private void setVisibility(boolean imgV,boolean textV,boolean btnText){
        mImg.setVisibility(imgV?VISIBLE:GONE);
        mText.setVisibility(textV?VISIBLE:GONE);
        mBtn.setVisibility(btnText?VISIBLE:GONE);
    }

    public void showContant(){
        setVisibility(false,false,false);
        if (contantView != null){
            contantView.setVisibility(VISIBLE);
        }
    }
    
    public EmptyView showEmpty(int imgResId,String text,String btnText,OnClickListener btnListener){
        if (contantView != null){
            contantView.setVisibility(GONE);
        }
        mImg.setImageResource(imgResId);
        mText.setText(text);
        mBtn.setText(btnText);
        mBtn.setOnClickListener(btnListener);
        setVisibility(true,true,true);
        return this;
    }
    
   

    public EmptyView showEmpty(int imgResId,String text){
        if (contantView != null){
            contantView.setVisibility(GONE);
        }
        mImg.setImageResource(imgResId);
        mText.setText(text);
        setVisibility(true,true,false);
        return this;
    }

    public EmptyView showEmpty(String text){
        if (contantView != null){
            contantView.setVisibility(GONE);
        }
        mText.setText(text);
        setVisibility(false,true,false);
        return this;
    }
    
    public EmptyView  setHeight(int height){
        mRootView.getLayoutParams().height = height;
        return this;
    }
}
