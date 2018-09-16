//package com.toobei.common.data;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.toobei.common.R;
//import com.toobei.common.entity.AccountDetail;
//
//import org.xsl781.data.BaseListAdapter;
//import org.xsl781.ui.ViewHolder;
//
//import java.util.List;
//
//public class AccountDetailAdapter extends BaseListAdapter<AccountDetail> {
//
//	private int textColor;
//	public AccountDetailAdapter(Context ctx, List<AccountDetail> datas) {
//		super(ctx, datas);
//		this.datas = datas;
//
//	}
//
//	public AccountDetailAdapter(Context ctx,int textColor) {
//		super(ctx);
//		this.textColor = textColor;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			convertView = View.inflate(ctx, R.layout.layout_account_detail_list_item, null);
//		}
//		final AccountDetail data = getItem(position);
//
//		TextView name = ViewHolder.findViewById(convertView, R.id.text_customer_name);
//		TextView time = ViewHolder.findViewById(convertView, R.id.text_time);
//		TextView count = ViewHolder.findViewById(convertView, R.id.text_account_count);
//		TextView content = ViewHolder.findViewById(convertView, R.id.text_account_content);
//		TextView textFee = ViewHolder.findViewById(convertView, R.id.text_account_fee);
//		float amt = 0;
//		try {
//			amt = Float.parseFloat(data.getTransAmount());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (amt > 0) {
//			count.setText(data.getTransAmount());
//			count.setTextColor(ContextCompat.getColor(ctx,textColor));
//		} else {
//			count.setText(data.getTransAmount());
//			count.setTextColor(ContextCompat.getColor(ctx,R.color.text_my_green));
//		}
//
//		String fee = data.getFee();
//		if (fee==null||"0.00".equals(fee)||"".equals(fee)) { // 手续费
//			textFee.setVisibility(View.GONE);
//		} else {
//			textFee.setText("(手续费 "+fee+")");
//			textFee.setVisibility(View.VISIBLE);
//		}
//
//		name.setText(data.getTypeName());
//		time.setText(data.getTransDate());
//		content.setText(data.getRemark());
//
//		return convertView;
//	}
//
//}
