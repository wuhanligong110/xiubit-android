package com.v5ent.xiubit.data.recylerapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.PhotoUtil;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.entity.GrowthClassifyData;
import com.v5ent.xiubit.util.C;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/31
 */

public class GrowthManualAdapter extends BaseQuickAdapter<GrowthClassifyData, BaseViewHolder> {
    public GrowthManualAdapter() {
        super(R.layout.item_growth_manual);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GrowthClassifyData item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.titleTv, item.getTitle())
                .setText(R.id.readNumTv, "阅读量" + item.getReadingAmount())
                .setVisible(R.id.footDividView, position == getItemCount() - 1);
        PhotoUtil.loadImageByGlide(mContext, item.getImg(), (ImageView) helper.getView(R.id.imageIv));
        helper.getView(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivityCommon.class);
                String url;
                if (TextUtils.isEmpty(item.getLinkUrl())){
                    url = C.GROWTH_HAND_BOOK + item.getId();
                }else {
                    url = item.getLinkUrl();
                }
                ShareContent shareContent = new ShareContent(item.getTitle()
                        , item.getSummary()
                        , url
                        , item.getShareIcon());
                intent.putExtra("shareContent", shareContent);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });
    }

}
