package com.v5ent.xiubit.data.recylerapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.GoodTransData;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/27
 */

public class GoodReportAdapter extends BaseQuickAdapter<GoodTransData, BaseViewHolder> {
    public static String currentSelectId;
    private GoodTransData selectBean;

    public GoodReportAdapter() {
        super(R.layout.item_good_report);
    }

    public GoodTransData getSelectBean() {
        return selectBean;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GoodTransData item) {
        final int position = helper.getAdapterPosition();
        boolean isSelected = false;
        if ((0 == position && currentSelectId == null) || item.getBillId().equals(currentSelectId)) {
            isSelected = true;
            currentSelectId = item.getBillId();
            selectBean = item;
        }

        final boolean finalIsSelected = isSelected;
        helper.setText(R.id.nameTv, item.getUserName())
                .setText(R.id.amountTv, item.getAmount())
                .setText(R.id.dataTv, item.getInvestTime())
                .setImageResource(R.id.selectIv, isSelected ? R.drawable.select_image_box : R.drawable.unselect_image_box)
                .getView(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getBillId().equals(currentSelectId)) {
                    currentSelectId = "";
                    helper.setImageResource(R.id.selectIv, R.drawable.unselect_image_box);
                    selectBean = null;
                    helper.setVisible(R.id.alphabgIv, false);
                } else {
                    currentSelectId = item.getBillId();
                    selectBean = item;
                    helper.setImageResource(R.id.selectIv, R.drawable.select_image_box);
                    helper.setVisible(R.id.alphabgIv, true);
                    notifyDataSetChanged();
                }
            }
        });

        helper.setVisible(R.id.alphabgIv, finalIsSelected ? true : false);

    }
}
