package com.v5ent.xiubit.data.recylerapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.utils.PhotoUtil;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.GrowthClassListActivity;
import com.v5ent.xiubit.entity.GrowthHandbookData;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/31
 */

public class GrowthTopAdapter extends BaseQuickAdapter<GrowthHandbookData,BaseViewHolder>{
    
    public GrowthTopAdapter() {
        super(R.layout.item_growth_top);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GrowthHandbookData item) {
        helper.setText(R.id.titleTv,item.getDescription());
        PhotoUtil.loadImageByGlide(mContext,item.getIcon(), (ImageView) helper.getView(R.id.imageIv));
        helper.getView(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GrowthClassListActivity.class);
                intent.putExtra("typeCode",item.getId());
                mContext.startActivity(intent);
            }
        });
    }
}
