package com.v5ent.xiubit.data.recylerapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.utils.TextDecorator;
import com.v5ent.xiubit.R;
import com.toobei.common.entity.FundSiftData;
import com.v5ent.xiubit.service.JumpFundService;
import com.umeng.analytics.MobclickAgent;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */

public class FundListAdapter extends BaseQuickAdapter<FundSiftData, BaseViewHolder> {
    private String period;
    private String periodTypeName;

    public FundListAdapter() {
        super(R.layout.item_fund_list_sort);
    }

    @Override
    protected void convert(BaseViewHolder helper, final FundSiftData item) {
        helper.setText(R.id.fundFullNameTv, item.fundName + " " + item.fundCode)
                .setText(R.id.typeTv, item.fundTypeMsg)
                .setText(R.id.periodTv, periodTypeName)
                .setOnClickListener(R.id.rootView, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MobclickAgent.onEvent(mContext,"T_3_2");
                        try {
                            new JumpFundService(JumpFundService.JUMP_TYPE_PRODUCT_DETIAL
                                    , (TopBaseActivity) mContext, item.fundCode).jump();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        try {
            String str = item.getYield(period == null ? "year1" : period);
            if (Double.compare(0,Double.parseDouble(str.replace("%",""))) == 0) {
                str = "--";
            }
            TextView textView = helper.getView(R.id.month3Tv);
            TextDecorator.decorate(textView, str)
                    .setAbsoluteSize(15, true, "%")
                    .build();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    //设置产品显示收益的类型
    public void setDateType(String period, String periodTypeName) {
        this.period = period;
        this.periodTypeName = periodTypeName;
    }
}
