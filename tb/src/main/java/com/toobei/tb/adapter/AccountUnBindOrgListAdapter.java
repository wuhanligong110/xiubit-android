package com.toobei.tb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.tb.R;
import com.toobei.tb.entity.AccountManageDetail;
import com.toobei.tb.service.JumpOrgService;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的投资页面，未绑定账户列表Adapter
 * Created by hasee-pc on 2016/12/29.
 */
public class AccountUnBindOrgListAdapter extends BaseAdapter {
    private long lastClickTime;
    private List<AccountManageDetail> mUnBindDatas = new ArrayList<>();
    private Context mContext;

    public AccountUnBindOrgListAdapter(Context ctx, List<AccountManageDetail> unBindDatas) {
        mUnBindDatas = unBindDatas;
        mContext = ctx;
    }

    @Override
    public int getCount() {
        return mUnBindDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccountUnBindOrgListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_unbind_org_account, null, true);
            holder = new AccountUnBindOrgListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AccountUnBindOrgListAdapter.ViewHolder) convertView.getTag();
        }
        final AccountManageDetail bean = mUnBindDatas.get(position);
        final String orgName = bean.getOrgName();
        final String orgNumber = bean.getOrgNumber();
        holder.mOrgNameTv.setText(orgName); //机构名
        holder.mOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long currentClickTime = System.currentTimeMillis();
                if ((currentClickTime - lastClickTime) > 500) {
                    lastClickTime = currentClickTime;
                    //开通账户
                    JumpOrgService jumpOrgService = new JumpOrgService();
                    jumpOrgService.openOrgAccount(orgName, orgNumber, mContext);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {

        public TextView mOrgNameTv;
        public Button mOpenBtn;

        public ViewHolder(View convertView) {
            mOrgNameTv = (TextView) convertView.findViewById(R.id.orgNameTV);
            mOpenBtn = (Button) convertView.findViewById(R.id.openBtn);
        }
    }
}
