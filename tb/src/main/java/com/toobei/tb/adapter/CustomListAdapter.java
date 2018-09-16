package com.toobei.tb.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toobei.common.entity.Custom;
import com.toobei.tb.R;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

public class CustomListAdapter extends BaseListAdapter<Custom> {

    public CustomListAdapter(Context ctx, List<Custom> datas) {
        super(ctx, datas);
        this.datas = datas;
    }

    public CustomListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.layout_customer_list_item, null);
        }
        final Custom data = getItem(position);
        TextView name = ViewHolder.findViewById(convertView, R.id.text_customer_name);
        TextView phone = ViewHolder.findViewById(convertView, R.id.text_customer_phone);
        TextView registerDate = ViewHolder.findViewById(convertView, R.id.text_customer_register);
        TextView investFlag = ViewHolder.findViewById(convertView, R.id.text_customer_invest_flag);

        registerDate.setText(data.getRegisterDate() + "注册");
        String customerName = data.getCustomerName();
        String temp = customerName.replace(" ", "");
        if (TextUtils.isEmpty(temp)) {
            name.setText("未认证");
        } else {
            name.setText(customerName);
        }
        phone.setText(data.getCustomerMobile());

        if (data.getInvestFlag() != null && Integer.parseInt(data.getInvestFlag()) > 0) {
            investFlag.setText("已投资");
            investFlag.setTextColor(ContextCompat.getColor(ctx,R.color.text_red_common));
        } else {
            investFlag.setText("未投资");
            investFlag.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
        }
        return convertView;
    }

}
