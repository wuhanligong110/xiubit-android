package com.v5ent.xiubit.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.ObjectCallBack;
import com.toobei.common.utils.PhotoUtil;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.entity.HomepageNewActivityData;

/**
 * Created by hasee-pc on 2017/2/22.
 * 首页活动弹窗
 */

public class HomePageNewActivityDialog extends Dialog implements View.OnClickListener {
    private Context ctx;
    private HomepageNewActivityData data;
    private ImageView activityIv;
    private ImageView closedIv;


    public HomePageNewActivityDialog(Context ctx, HomepageNewActivityData data) {
        super(ctx, com.toobei.common.R.style.customDialog);
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_homepager_new_activity, null);
        setContentView(view);
        setCancelable(false);
        initView();
        initData();
    }

    private void initData() {
        try {
            PhotoUtil.getBitmapFromUrl(ctx,data.getImgUrl(), new ObjectCallBack<Bitmap>() {
                @Override
                public void backObject(Bitmap bitmap) {
                    int imageRatio = bitmap.getHeight() / bitmap.getWidth();
                    activityIv.getLayoutParams().height = activityIv.getLayoutParams().width * imageRatio;
                    activityIv.setImageBitmap(bitmap);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        activityIv = (ImageView) findViewById(R.id.activityIv);
        closedIv = (ImageView) findViewById(R.id.closedIv);
        activityIv.setOnClickListener(this);
        closedIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activityIv) {
            Intent intent = new Intent(ctx, WebActivityCommon.class);
            intent.putExtra("url", data.getLinkUrl());
            intent.putExtra("title", data.getShareTitle());
            intent.putExtra("shareContent", new ShareContent(data.getShareTitle(), data.getShareDesc(), data.getLinkUrl(), data.getShareIcon()));
            ctx.startActivity(intent);
        }
        dismiss();
    }
}
