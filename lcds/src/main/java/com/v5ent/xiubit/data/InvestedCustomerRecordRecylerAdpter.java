package com.v5ent.xiubit.data;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.InvitationCustomerRecordData;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InvestedCustomerRecordRecylerAdpter extends BaseQuickAdapter<InvitationCustomerRecordData,BaseViewHolder> {

    public InvestedCustomerRecordRecylerAdpter() {
        super(R.layout.item_invite_record_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitationCustomerRecordData bean) {
        helper.setText(R.id.nameTv,TextUtils.isEmpty(bean.getUserName())?"未认证":bean.getUserName())
        .setText(R.id.phoneTv,bean.getMobile())
        .setText(R.id.infoTv,"0".equals(bean.getIsInvest())? "未投资": "已投资")
        .setText(R.id.dateTv,bean.getRegisterTime());
    }


}
