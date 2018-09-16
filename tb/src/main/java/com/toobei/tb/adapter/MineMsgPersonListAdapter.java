package com.toobei.tb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.MsgDetail;
import com.toobei.tb.R;
import com.toobei.tb.activity.MyCfpActivity;
import com.toobei.tb.activity.WebActivityCommon;

import org.xsl781.data.BaseCheckListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明: 通知
 *
 * @author qingyechen
 * @time 2017/1/4 0004 下午 2:31
 */
public class MineMsgPersonListAdapter extends BaseCheckListAdapter<MsgDetail> {

    private boolean isCheckVisible = false;
    private List<MsgDetail> oldCheckedUsers;
    private List<MsgDetail> newCheckedUsers = new ArrayList<MsgDetail>();
    private List<Integer> oldCheckedPosition;
    private OnMyCheckedListener checkedListener;


    public interface OnMyCheckedListener {
        void onCheckedChanged(int position, boolean isChecked);
    }

    public MineMsgPersonListAdapter(Context ctx) {
        super(ctx);
    }

    public MineMsgPersonListAdapter(Context ctx, List<MsgDetail> datas) {
        super(ctx, datas);
    }

    public MineMsgPersonListAdapter(Context ctx, List<MsgDetail> datas, List<MsgDetail> oldCheckedUsers) {
        super(ctx, datas);
        setOldCheckedUsers(oldCheckedUsers);
    }

    private int getPositionByData(MsgDetail MsgDetail) {
        if (datas == null || datas.size() == 0) {
            return -1;
        }
        for (int i = 0; i < datas.size(); i++) {
            //			if (datas.get(i).getObjectId().equals(MsgDetail.getObjectId())) {
            //				return i;
            //			}
        }
        return -1;
    }

    private void initCheckedItems() {
        if (oldCheckedUsers != null && oldCheckedUsers.size() > 0) {
            oldCheckedPosition = new ArrayList<Integer>();
            for (MsgDetail MsgDetail : oldCheckedUsers) {
                int position = getPositionByData(MsgDetail);
                if (position > -1) {
                    oldCheckedPosition.add(position);
                }
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_msg_person_delete_list, null);
        final MsgDetail data = getItem(position);
        CheckBox checkBox = ViewHolder.findViewById(convertView, R.id.checkbox);

        checkBox.setOnCheckedChangeListener(new MyCheckedListener(position));
        if (isCheckVisible) {
            if (oldCheckedPosition != null && oldCheckedPosition.contains(position)) {
                checkBox.setEnabled(false);
            } else {
                checkBox.setEnabled(true);
            }
            setCheckBox(checkBox, position);
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        TextView content = ViewHolder.findViewById(convertView, R.id.text_content);
        TextView textTime = ViewHolder.findViewById(convertView, R.id.text_time);
        //2017/1/4 0004
        TextView textLink = ViewHolder.findViewById(convertView, R.id.text_link);
        ImageView ivReadState = ViewHolder.findViewById(convertView, R.id.img_read_state);
        //   LinearLayout itemLl = ViewHolder.findViewById(convertView, R.id.msg_person_del_item_ll);


        String contentStr = data.getContent();
//        float length = Float.valueOf(contentStr.length()) / 20;
//        itemLl.getLayoutParams().height = PixelUtil.dip2px(ctx, 70 + length * 20);
        content.setText(contentStr);

        //点击跳转
        final String linkBtnTxt = data.getLinkBtnTxt();
        if (!TextUtils.isEmpty(linkBtnTxt)) {
            textLink.setText(Html.fromHtml("<u>" + linkBtnTxt + "</u>"));
            textLink.setVisibility(View.VISIBLE);
            textLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String linkUrlKey = data.getLinkUrlKey();
                    if (TextUtils.isEmpty(linkUrlKey)) {
                        WebActivityCommon.showThisActivity((TopBaseActivity) ctx, linkUrlKey, linkBtnTxt);
                    } else {

                        //  myCfp_product 跳转我的理财师->理财师推荐产品   myCfp_platform->推荐平台

                        Intent intent = new Intent(ctx, MyCfpActivity.class);
                        intent.putExtra("linkUrlKey", linkUrlKey);
                        ctx.startActivity(intent);

                    }
                }
            });
        } else {
            textLink.setVisibility(View.GONE);
        }

        textTime.setText(data.getStartTime());

        int colorGray96 = ContextCompat.getColor(ctx,R.color.text_gray_common);
        int colorLinkBlue = ContextCompat.getColor(ctx,R.color.text_link_blue);
        int colorGray64 = ContextCompat.getColor(ctx,R.color.text_gray_common_title);
        if (data.getRead() != null && data.getRead().equals("1")) {//是否已读 1已读,0未读
            ivReadState.setImageResource(R.drawable.icon_msg_read);
            content.setTextColor(colorGray96);
            //   textLink.setTextColor(colorGray96);

        } else {
            ivReadState.setImageResource(R.drawable.icon_msg_unread);
            //  textLink.setTextColor(colorLinkBlue);
            content.setTextColor(colorGray64);
        }
        return convertView;
    }

    private class MyCheckedListener extends CheckListener {

        public MyCheckedListener(int position) {
            super(position);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            super.onCheckedChanged(buttonView, isChecked);
            if (checkedListener != null) {
                checkedListener.onCheckedChanged(position, isChecked);
            }
        }

    }

    public void setCheckVisible(boolean isCheckVisible) {
        this.isCheckVisible = isCheckVisible;
        notifyDataSetInvalidated();
    }

    public List<MsgDetail> getOldCheckedUsers() {
        return oldCheckedUsers;
    }

    public void setOldCheckedUsers(List<MsgDetail> oldCheckedUsers) {
        this.oldCheckedUsers = oldCheckedUsers;
        initCheckedItems();
    }

    public List<MsgDetail> getNewCheckedUsers() {
        return newCheckedUsers;
    }

    public void setCheckedListener(OnMyCheckedListener checkedListener) {
        this.checkedListener = checkedListener;
    }

    /**
     * 功能：设置全选和全不选
     *
     * @param checked
     */
    public void setAllItemChecked(boolean checked) {
        checkStates.clear();
        for (int i = 0; i < getDatas().size(); i++) {
            checkStates.add(checked);
        }
        notifyDataSetChanged();
    }

}
