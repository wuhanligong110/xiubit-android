package com.toobei.common.data;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.entity.WithdrawRecord;
import com.toobei.common.view.dialog.PromptDialogMsg;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;
import org.xsl781.utils.TimeUtils;

import java.util.Date;
import java.util.List;

public class WithdrawRecordAdapter extends BaseListAdapter<WithdrawRecord> {

    private int color;
    private int questImgId;
    private String appKind;
    private String shouxufeui;

    public WithdrawRecordAdapter(Context ctx, List<WithdrawRecord> datas) {
        super(ctx, datas);
        this.datas = datas;

    }

    public WithdrawRecordAdapter(Context ctx, int color, int imageResId) {
        super(ctx);
        this.color = color;
        this.questImgId = imageResId;
        appKind = TopApp.getInstance().getHttpService().getAppKind();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.layout_withdraw_record_list_item, null);
        }
        final WithdrawRecord data = getItem(position);

        TextView textMoney = ViewHolder.findViewById(convertView, R.id.text_withdraw_money);
        TextView textTime = ViewHolder.findViewById(convertView, R.id.text_time);
        TextView textStatus = ViewHolder.findViewById(convertView, R.id.text_status);
        TextView textToAccountTime = ViewHolder.findViewById(convertView, R.id.text_toaccount_time);
        TextView userTypeTv = ViewHolder.findViewById(convertView, R.id.userTypeTv);
        TextView tranNameTv = ViewHolder.findViewById(convertView, R.id.tranNameTv);
        ImageView questIv = ViewHolder.findViewById(convertView,R.id.quest_iv);

        tranNameTv.setText(data.getBisName());
        textMoney.setText(data.getAmount()+"元");
        textTime.setText(data.getTransDate());
//        userTypeTv.setText("("+data.getUserType()+")");
        String status = data.getStatus();
        //		提现处理状态
        //		("0",  "提现记录"), @"提现中",
        //		("1",  "可以审核提现资金已冻结"),@"提现中"
        //		("2",  "审核通过，要查询支付平台打款结果"),@"提现中"
        //		("3",  "审核不通过，解冻"), "提现失败，请重新申请"
        //		("4",  "冻结失败"), ,@"提现失败，请重新申请"
        //		("5",  "提现成功"), @"提现成功"
        //		("6",  "打款失败，需要解冻"),@"提现失败，请重新申请"
        //		("7",  "打款失败，已处理解冻")@"提现失败，请重新申请"
        //		("8", 	"打款处理中")
        if (!TextUtils.isEmpty(status)) {
            if (status.equals("0") || status.equals("1") || status.equals("2") || status.equals("8")) {
                //提现中
                textStatus.setText("提现中");
                textStatus.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
                //	Date date = TimeUtils.getDate(data.getBisTime(), TimeUtils.FORMAT_DATE_TIME1);
                textToAccountTime.setText(data.getPaymentDate());
                textToAccountTime.setVisibility(View.VISIBLE);
                textToAccountTime.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
                questIv.setVisibility(View.GONE);
            } else if (status.equals("5")) {
                //提现成功
                textStatus.setText("成功");
                textStatus.setTextColor(ContextCompat.getColor(ctx,color));
                if ("investor".equals(appKind)) {
                    textToAccountTime.setTextColor(ContextCompat.getColor(ctx,color));
                    textToAccountTime.setText("手续费："+data.getFee()+"元");
                } else {
                    textToAccountTime.setVisibility(View.GONE);
                }
                questIv.setVisibility(View.GONE);
            } else if (status.equals("3") || status.equals("4") || status.equals("6") || status.equals("7")) {
                //提现失败
                textStatus.setText("提现失败，请重新申请");
                textStatus.setTextColor(ContextCompat.getColor(ctx,R.color.text_red_common));
                textToAccountTime.setVisibility(View.GONE);
                questIv.setImageResource(questImgId);
                questIv.setVisibility(View.VISIBLE);
            }

            questIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromptDialogMsg promptDialogMsg = new PromptDialogMsg(ctx, data.getRemark() == null ? "" : data.getRemark(), "知道了");
                    promptDialogMsg.show();
                }
            });
        }

        return convertView;
    }

    @SuppressWarnings("deprecation")
    private String getToAccountTime(Date date) {
        String str = "";
        if (date.getHours() < 15) {
            str = "预计" + TimeUtils.getCurrentDate(TimeUtils.FORMAT_DATE) + " 24点前到账";
        } else {
            str = "预计" + TimeUtils.getDateStr(TimeUtils.getDateAfter(date, 1), TimeUtils.FORMAT_DATE) + " 24点前到账";
        }
        return str;
    }

}
