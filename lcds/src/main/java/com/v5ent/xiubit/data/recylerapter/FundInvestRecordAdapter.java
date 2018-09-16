package com.v5ent.xiubit.data.recylerapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.utils.TextDecorator;
import com.v5ent.xiubit.R;
import com.toobei.common.entity.FundInvestorOrderInfoData;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/22
 */

public class FundInvestRecordAdapter extends BaseQuickAdapter<FundInvestorOrderInfoData,BaseViewHolder>{
    
    public FundInvestRecordAdapter() {
        super(R.layout.item_fund_invest_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, FundInvestorOrderInfoData item) {
        helper.setText(R.id.fundNameTv,item.fundName+" "+ item.fundCode)
                .setText(R.id.transactionDateTv,item.transactionDate)
                .setText(R.id.transactionAmounTv,item.transactionAmount);
                boolean isBad = false;
                if (item.transactionStatus.equals("failure") || item.transactionStatus.equals("void")){
                    isBad = true;
                }
        TextDecorator.decorate((TextView) helper.getView(R.id.transactionStatusTv)
                ,item.transactionTypeMsg + "-" +item.transactionStatusMsg)
                .setTextColor(isBad? R.color.text_red_common:R.color.text_blue_common,item.transactionStatusMsg)
                .build();
    }
}
