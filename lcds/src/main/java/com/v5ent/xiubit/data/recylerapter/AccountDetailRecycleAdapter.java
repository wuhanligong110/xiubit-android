package com.v5ent.xiubit.data.recylerapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.data.BaseRecycleViewAdapter;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.IncomeAndOutDetail;

import org.xsl781.ui.ViewHolder;

import java.util.List;

/**
 * Created by hasee-pc on 2017/2/14.
 * 收支明细列表
 */
public class AccountDetailRecycleAdapter extends BaseRecycleViewAdapter<IncomeAndOutDetail> {
    private String dataType; //收支类型(0=全部1=收入|2=支出)
    private View rootView;


    public AccountDetailRecycleAdapter(Context context, List<IncomeAndOutDetail> datas, String dataType) {
        super(context, datas);
        this.dataType = dataType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = inflater.inflate(R.layout.item_account_detail_list, null);
        return new AccountDetailViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AccountDetailViewHolder viewHolder = (AccountDetailViewHolder) holder;
        final IncomeAndOutDetail bean = datas.get(position);
        viewHolder.tranNameTv.setText(bean.getTranName());
        viewHolder.typeSuffixTv.setText("(" + bean.getUserType() + ")");

        viewHolder.tranTime.setText(bean.getTranTime());
        viewHolder.amountTv.setText(bean.getAmount() + "元");
        viewHolder.remarkTv.setText(bean.getRemark());

        if (!TextUtils.isEmpty(bean.getWithdrawRemark())) {
            viewHolder.withdrawRemarkTv.setVisibility(View.VISIBLE);
            viewHolder.withdrawRemarkTv.setText(bean.getWithdrawRemark());
        } else {
            viewHolder.withdrawRemarkTv.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(bean.getStatus())) {
            switch (bean.getStatus()) {

                case "6":
                case "7":
                    viewHolder.img_false_question.setVisibility(View.VISIBLE);
                    break;
                default:
                    viewHolder.img_false_question.setVisibility(View.GONE);
                    break;
            }
        } else {
            viewHolder.img_false_question.setVisibility(View.GONE);
        }

        viewHolder.img_false_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptDialogMsg promptDialogMsg = new PromptDialogMsg(context, bean.getFailureCause() == null ? "" : bean.getFailureCause(), "知道了");
                promptDialogMsg.show();
            }
        });
    }

    class AccountDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tranNameTv;
        TextView typeSuffixTv;
        TextView tranTime;
        TextView amountTv;
        TextView remarkTv;
        TextView withdrawRemarkTv;
        ImageView img_false_question;

        public AccountDetailViewHolder(View convertView) {
            super(convertView);
            tranNameTv = ViewHolder.findViewById(convertView, R.id.tranNameTv);
            typeSuffixTv = ViewHolder.findViewById(convertView, R.id.typeSuffixTv);
            tranTime = ViewHolder.findViewById(convertView, R.id.tranTime);
            amountTv = ViewHolder.findViewById(convertView, R.id.amountTv);
            remarkTv = ViewHolder.findViewById(convertView, R.id.remarkTv);
            withdrawRemarkTv = ViewHolder.findViewById(convertView, R.id.withdrawRemarkTv);
            img_false_question = ViewHolder.findViewById(convertView, R.id.img_false_question);
        }
    }
}
