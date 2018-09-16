package com.v5ent.xiubit.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.MyIncomeDetailActivity;
import com.v5ent.xiubit.entity.AccountIncome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccountIncomeAdapter extends BaseQuickAdapter<AccountIncome,BaseViewHolder> {
    List<String> yearList = new ArrayList<>();
    List<Integer> postionList = new ArrayList<>();

    public AccountIncomeAdapter(Context ctx) {
        super(R.layout.item_account_income_list);
        mContext = ctx;
    }


    @Override
    protected void convert(BaseViewHolder helper, final AccountIncome bean) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.text_month_desc,bean.getMonthDesc())
        .setText(R.id.text_month_total_value,bean.getTotalAmount());

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        String year = bean.getMonth().substring(0, 4);
        if (!yearList.contains(year) && !(thisYear+"").equals(year)) {
            yearList.add(year);
            postionList.add(position);

        }
//但使用yearList来判断是滑动过程中条目复现时会出问题，所以这里额外再加上个position判断
        if (postionList.contains(position)) {
            helper.setText(R.id.yearTv,year + "年")
            .setVisible(R.id.top_year_contant,position != 0);
        } else {
            helper.setVisible(R.id.top_year_contant,false);
        }
        
        helper.setOnClickListener(R.id.itemRootLl,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyIncomeDetailActivity.class);
                intent.putExtra("date", bean.getMonth());
                intent.putExtra("monthDesc", bean.getMonthDesc());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void addData(@NonNull AccountIncome data) {
        yearList.clear();
        postionList.clear();
        super.addData(data);
    }

    @Override
    public void setNewData(@Nullable List<AccountIncome> data) {
        yearList.clear();
        postionList.clear();
        super.setNewData(data);
    }

}
