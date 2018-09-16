package com.toobei.tb.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.entity.MsgDetail;
import com.toobei.tb.R;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：我的消息 公告
 * @date 2015-10-27
 */
public class MineMsgNoticeListAdapter extends BaseListAdapter<MsgDetail> {

	public MineMsgNoticeListAdapter(Context ctx, List<MsgDetail> datas) {
		super(ctx, datas);
		this.datas = datas;
	}

	public MineMsgNoticeListAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(ctx, R.layout.item_msg_notice_list, null);
		}
		final MsgDetail data = getItem(position);
		TextView content = ViewHolder.findViewById(convertView, R.id.text_content);
		TextView time = ViewHolder.findViewById(convertView, R.id.text_time);
		ImageView ivReadState = ViewHolder.findViewById(convertView, R.id.img_read_state);
		content.setText(data.getMessage()); //公告内容
		time.setText(data.getStartTime());// 时间
		View rootView = ViewHolder.findViewById(convertView, R.id.mine_msg_notice_item_ll);
		if (data.getRead() != null && data.getRead().equals("1")) {//是否已读 1已读,0未读
			ivReadState.setImageResource(R.drawable.icon_msg_read);
			content.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
		} else {
			ivReadState.setImageResource(R.drawable.icon_msg_unread);
			content.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common_title));
		}
		return convertView;
	}

}
