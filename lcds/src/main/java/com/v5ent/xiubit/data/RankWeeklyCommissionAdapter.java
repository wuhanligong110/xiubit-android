package com.v5ent.xiubit.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.utils.PhotoUtil;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.RankWeeklyCommission;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;
import org.xsl781.ui.view.RoundImageView;
import org.xsl781.utils.StringUtils;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:  周佣榜
 *
 * @author qingyechen
 * @time 2017/2/23 13:43
 */
public class RankWeeklyCommissionAdapter extends BaseListAdapter<RankWeeklyCommission> {
    private String month;
    private int rankType;

    public RankWeeklyCommissionAdapter(Context ctx) {
        super(ctx);
    }

    public RankWeeklyCommissionAdapter(Context ctx, List<RankWeeklyCommission> datas, int rankType,String month) {
        super(ctx, datas);
        this.rankType = rankType;
        this.month = month;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_rank_weekly_commission, null);
        }

        TextView textRank = ViewHolder.findViewById(convertView, R.id.text_rank);
        RoundImageView headIV = ViewHolder.findViewById(convertView, R.id.img_head);
        TextView textPhone = ViewHolder.findViewById(convertView, R.id.text_phone);
        TextView textCommission = ViewHolder.findViewById(convertView, R.id.text_commission);
        TextView amountRemindTv = ViewHolder.findViewById(convertView, R.id.amountRemindTv);
        TextView levelNameTv = ViewHolder.findViewById(convertView, R.id.levelNameTv);
        ImageView imgRank = ViewHolder.findViewById(convertView, R.id.img_rank);


        RankWeeklyCommission data = datas.get(position);
        levelNameTv.setText("职级:"+data.getLevelName());
        if (rankType == 0) {
            amountRemindTv.setText(month + "月总收益");
        } else {
            amountRemindTv.setText("本月奖励");
        }
        if (position == 0) {
            textRank.setVisibility(View.GONE);
            imgRank.setBackgroundResource(R.drawable.img_rank_01);
            imgRank.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            textRank.setVisibility(View.GONE);
            textRank.setText("");
            imgRank.setBackgroundResource(R.drawable.img_rank_02);
            imgRank.setVisibility(View.VISIBLE);
        } else if (position == 2) {
            textRank.setVisibility(View.GONE);
            textRank.setText("");
            imgRank.setBackgroundResource(R.drawable.img_rank_03);
            imgRank.setVisibility(View.VISIBLE);
        } else {
            textRank.setVisibility(View.VISIBLE);
            imgRank.setVisibility(View.GONE);

        }
        textRank.setText(((int) StringUtils.toDouble(data.getRank())) + "");
        textPhone.setText(data.getMobile());
        //  RoundImage 使用Glide 无法加载
//        PhotoUtil.loadImageByGlide(ctx, data.getHeadImg(), headIV);
        PhotoUtil.loadImageByImageLoader(data.getHeadImg() + "?f=png", headIV);
        textCommission.setText(data.getTotalProfit());

        return convertView;
    }
}
