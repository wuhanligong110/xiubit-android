package com.v5ent.xiubit.data.recylerapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.v5ent.xiubit.R;
import com.toobei.common.entity.FundInvestorHoldingsData;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/21
 */

public class MineFundListAdapter extends BaseQuickAdapter<FundInvestorHoldingsData.FundInvestorDetialDataBean,BaseViewHolder>{
    public MineFundListAdapter() {
        super(R.layout.item_mine_fund_detial_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, FundInvestorHoldingsData.FundInvestorDetialDataBean item) {
        helper.setText(R.id.fundNameTv,item.fundName+ " " + item.fundCode)
                .setText(R.id.investmentAmountTv,item.currentValue)
                .setText(R.id.previousProfitNLossTv,item.previousProfitNLoss)
                .setText(R.id.profitNLossTv,item.profitNLoss);

//        MyTextView view = helper.getView(R.id.profitNLossTv);
//        if (item.profitNLoss.startsWith("-")){
//            view.setTextColor(ContextCompat.getColor(mContext,R.color.text_red_common));
//        }else {
//            view.setTextColor(ContextCompat.getColor(mContext,R.color.text_black_common));
//        }
    }
}
