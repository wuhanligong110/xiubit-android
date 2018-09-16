package com.v5ent.xiubit.data.recylerapter;

import android.graphics.Typeface;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.FundSiftData;
import com.toobei.common.utils.TextDecorator;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.service.JumpFundService;
import com.v5ent.xiubit.view.MyTextView;
import com.umeng.analytics.MobclickAgent;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */

public class FundHomeAdapter extends BaseQuickAdapter<FundSiftData, BaseViewHolder> {

    public FundHomeAdapter() {
        super(R.layout.item_fund_home_sift);
    }

    @Override
    protected void convert(BaseViewHolder helper, final FundSiftData item) {
        
        helper
//                .setText(R.id.fund_yield_tv, item.sevenDaysAnnualizedYield)
                .setText(R.id.fund_description_tv, "近七日年化 ("+ item.fundName+ item.fundCode + ")")
                .setOnClickListener(R.id.rootView, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MobclickAgent.onEvent(mContext, "S_4_1");
                            new JumpFundService(JumpFundService.JUMP_TYPE_PRODUCT_DETIAL
                                    , (TopBaseActivity) mContext, item.fundCode).jump();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        String sevenYieldStr = item.sevenDaysAnnualizedYield + (item.sevenDaysAnnualizedYield.contains("%")? "":"%");
        MyTextView sevenYieldTv = helper.getView(R.id.fund_yield_tv);
        TextDecorator.decorate(sevenYieldTv,sevenYieldStr)
                .setAbsoluteSize(15,true,"%")
                .setTextStyle(Typeface.NORMAL,"%")
                .build();

    }
}
