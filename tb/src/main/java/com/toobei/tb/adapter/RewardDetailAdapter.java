package com.toobei.tb.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.view.dialog.PromptDialogMsg;
import com.toobei.tb.R;
import com.toobei.tb.entity.RewardDetail;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

/**
 * Created by hasee-pc on 2017/2/16.
 */
public class RewardDetailAdapter extends BaseListAdapter<RewardDetail> {
    private String dataType; //奖励类型(1=奖励收入明细|2=奖励支出明细)

    public RewardDetailAdapter(Activity ctx, List<RewardDetail> datas, String dataType) {
        super(ctx, datas);
        this.dataType = dataType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_reward_detail_list, null);
        }
        final RewardDetail bean = getItem(position);
        if (bean == null) {
            convertView.setVisibility(View.INVISIBLE);
            return convertView;
        }
        convertView.setVisibility(View.VISIBLE);

        TextView tranNameTv = ViewHolder.findViewById(convertView, R.id.tranNameTv);
        TextView tranTimeTv = ViewHolder.findViewById(convertView, R.id.tranTimeTv);
        TextView amountTv = ViewHolder.findViewById(convertView, R.id.amountTv);
        TextView userTypeTv = ViewHolder.findViewById(convertView, R.id.userTypeTv);
        TextView remarkTv = ViewHolder.findViewById(convertView, R.id.remarkTv);
        TextView withdrawRemarkTv = ViewHolder.findViewById(convertView, R.id.withdrawRemarkTv);
        ImageView remarkQuesIv = ViewHolder.findViewById(convertView, R.id.remarkQuesIv);

        tranNameTv.setText(bean.getTranName());
        tranTimeTv.setText(bean.getTranTime());
        if (Float.parseFloat(bean.getAmount()) < 0){
            amountTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_green_common_06b71a));
        }else {
            amountTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_red_common));
        }
        amountTv.setText(bean.getAmount() + "元");
        remarkTv.setText(bean.getRemark());

        if (!TextUtils.isEmpty(bean.getUserType())) {
            userTypeTv.setVisibility(View.VISIBLE);
            userTypeTv.setText("(" + bean.getUserType() + ")");
        } else {
            userTypeTv.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(bean.getWithdrawRemark())) {
            withdrawRemarkTv.setVisibility(View.VISIBLE);
            withdrawRemarkTv.setText(bean.getWithdrawRemark());
        } else {
            withdrawRemarkTv.setVisibility(View.INVISIBLE);
        }

        if (! TextUtils.isEmpty(bean.getStatus())){
            switch (bean.getStatus()){

                case "6":
                case "7":
                    remarkQuesIv.setVisibility(View.VISIBLE);
                    break;
                default:
                    remarkQuesIv.setVisibility(View.GONE);
                    break;
            }
        }else {
            remarkQuesIv.setVisibility(View.GONE);
        }

        remarkQuesIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptDialogMsg promptDialogMsg = new PromptDialogMsg(ctx, bean.getFailureCause() == null ? "" : bean.getFailureCause(), "知道了");
                promptDialogMsg.show();
            }
        });
        return convertView;
    }

}
