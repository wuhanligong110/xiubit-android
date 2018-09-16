package com.toobei.tb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.tb.R;
import com.toobei.tb.activity.OrgUserCenterWebActivity;
import com.toobei.tb.entity.AccountManageDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的投资页面，已绑定账户ListAdapter
 * Created by hasee-pc on 2016/12/29.
 */
public class AccountBindOrgListAdapter extends BaseAdapter {
    private List<AccountManageDetail> mBindDatas = new ArrayList<>();
    private Context mContext;

    public AccountBindOrgListAdapter(Context context, List<AccountManageDetail> bindDatas) {
        mBindDatas = bindDatas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBindDatas.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bind_org_account, null, true);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AccountManageDetail bean = mBindDatas.get(position);
        final String orgNumber = bean.getOrgNumber();
        final String orgName = bean.getOrgName();
        holder.mOrgNameTv.setText(bean.getOrgName());   //机构名
        holder.mOrgAccountTv.setText(bean.getOrgAccount());
        holder.mBindDateTv.setText(bean.getBindDate());
        holder.mInvestAmountTv.setText(bean.getInvestAmount());
        holder.mInvestCountTv.setText(bean.getInvestCount());
        holder.mTotalProfixTv.setText(bean.getTotalProfix());
        holder.mGotoRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAccountdetail(orgNumber, orgName);
            }
        });

        return convertView;
    }

    /**
     * 跳转机构账户详情
     */
    private void gotoAccountdetail(String orgNo, String orgName) {
        Intent intent = new Intent(mContext, OrgUserCenterWebActivity.class);
        intent.putExtra("orgNo",orgNo);
        intent.putExtra("title", orgName);
        intent.putExtra("orgName",orgName);
        mContext.startActivity(intent);
    }




    class ViewHolder {

        public TextView mOrgNameTv;
        public TextView mOrgAccountTv;
        public TextView mBindDateTv;
        public TextView mInvestAmountTv;
        public TextView mInvestCountTv;
        public TextView mTotalProfixTv;
        public LinearLayout mGotoRight;

        public ViewHolder(View convertView) {
            mOrgNameTv = (TextView) convertView.findViewById(R.id.orgNameTV);
            mOrgAccountTv = (TextView) convertView.findViewById(R.id.orgAccountTv);
            mBindDateTv = (TextView) convertView.findViewById(R.id.bindDateTv);
            mInvestAmountTv = (TextView) convertView.findViewById(R.id.investAmountTv);
            mInvestCountTv = (TextView) convertView.findViewById(R.id.investCountTv);
            mTotalProfixTv = (TextView) convertView.findViewById(R.id.totalProfixTv);
            mGotoRight = (LinearLayout) convertView.findViewById(R.id.gotoRightLi);
        }
    }
}
