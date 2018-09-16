package com.v5ent.xiubit.data.recylerapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.entity.InsuranceDetialData;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.TextDecorator;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.service.JumpInsuranceService;
import com.v5ent.xiubit.view.MyTextView;
import com.umeng.analytics.MobclickAgent;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/13
 */

public class InsuranceAdapter extends BaseQuickAdapter<InsuranceDetialData,BaseViewHolder>{
    public static final int TYPE_SELECTED = 1 ;//甄选保险-保险首页
    public static int type ;
    public InsuranceAdapter() {
        super(R.layout.item_insurance_list);
    }
    public InsuranceAdapter(int type) {
        super(R.layout.item_insurance_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, final InsuranceDetialData item) {
        helper.getView(R.id.divideV).setVisibility(helper.getAdapterPosition() == (getItemCount()-1) ? View.GONE : View.VISIBLE);
        helper.setText(R.id.insurance_name_tv,item.productName)
                .setText(R.id.insurance_description_tv,item.fullDescription);
                
        MyTextView priceTv = helper.getView(R.id.insurance_price_tv);
        TextDecorator.decorate(priceTv,item.priceString + "元起")
                .setAbsoluteSize((int)(mContext.getResources().getDimension(R.dimen.w12)),false,"元起")
                .setTextColor(R.color.text_gray_common,"元起")
                .build();

        MyTextView feeRatioTv = helper.getView(R.id.feeRatioTv);
        TextDecorator.decorate(feeRatioTv,item.feeRatio + "%佣金率" )
                .setAbsoluteSize((int)(mContext.getResources().getDimension(R.dimen.w21)),false,item.feeRatio + "%")
                .setTextColor(R.color.text_red_common,item.feeRatio + "%")
                .build();

        ImageView iv = helper.getView(R.id.insurance_iv);
        PhotoUtil.loadImageByGlide(mContext,item.productBakimg,iv,R.drawable.empty_photo);
        helper.getView(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_SELECTED) MobclickAgent.onEvent(mContext,"T_4_1_5");
                else MobclickAgent.onEvent(mContext,"T_4_2_1");
                new JumpInsuranceService(mContext,item.caseCode,JumpInsuranceService.TAG_PRODUCT_DETIAL).run();
            }
        });
        
    }
}
