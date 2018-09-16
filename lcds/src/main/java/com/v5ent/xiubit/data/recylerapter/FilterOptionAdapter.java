package com.v5ent.xiubit.data.recylerapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.entity.FundBaseDefinedData;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.event.FundFilterRefreshEvent;
import com.v5ent.xiubit.view.popupwindow.FundFilterPopupWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/18
 */

public class FilterOptionAdapter extends BaseQuickAdapter<FundBaseDefinedData.FundTypeListBean, BaseViewHolder> {
    private FundFilterPopupWindow window;
    private boolean isSingleSelect;
    public List<String> selectTypeKeyList = new ArrayList<>();

    public FilterOptionAdapter(FundFilterPopupWindow window,String defaultkey, boolean isSingleSelect) {
        super(R.layout.item_filter_fund_option);
        this.isSingleSelect = isSingleSelect;
        this.window = window;
        String[] split = defaultkey.split(",");
        for (String str : split) {
            selectTypeKeyList.add(str);
        }
    }
    
    
    public void refreshView(String defaultkey){
        String[] split = defaultkey.split(",");
        selectTypeKeyList.clear();
        for (String str : split) {
            selectTypeKeyList.add(str);
        }
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, final FundBaseDefinedData.FundTypeListBean item) {
        boolean isSelected = selectTypeKeyList.contains(item.fundTypeKey);
        helper.setText(R.id.valueTv, item.fundTypeValue)
                .setVisible(R.id.selectedIconIv, isSelected);

        final TextView textView = helper.getView(R.id.valueTv);
        final ImageView imageView = helper.getView(R.id.selectedIconIv);
        textView.setTextColor(isSelected ? ContextCompat.getColor(mContext,R.color.text_blue_common)
                : ContextCompat.getColor(mContext,R.color.text_black_common));

        helper.getView(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSingleSelect) {
                    textView.setTextColor(ContextCompat.getColor(mContext,R.color.text_blue_common));
                    imageView.setVisibility(View.VISIBLE);
                    selectTypeKeyList.clear();
                    selectTypeKeyList.add(item.fundTypeKey);
                    notifyDataSetChanged();
                    FundFilterRefreshEvent event = new FundFilterRefreshEvent();
                    event.type = FundFilterPopupWindow.TYPE_PERIODTYPE;
                    event.typeKeyString = selectTypeKeyList.get(0);
                    event.periodName = item.fundTypeName;
                    EventBus.getDefault().post(event);
                    window.dismiss();
                    return;
                }

                if (selectTypeKeyList.contains(item.fundTypeKey)) {
                    textView.setTextColor(ContextCompat.getColor(mContext,R.color.text_black_common));
                    imageView.setVisibility(View.GONE);
                    selectTypeKeyList.remove(item.fundTypeKey);
                } else {
                    textView.setTextColor(ContextCompat.getColor(mContext,R.color.text_blue_common));
                    imageView.setVisibility(View.VISIBLE);
                    selectTypeKeyList.add(item.fundTypeKey);
                }
            }
        });

    }
}
