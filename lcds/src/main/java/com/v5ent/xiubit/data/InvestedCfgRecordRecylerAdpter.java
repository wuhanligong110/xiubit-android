package com.v5ent.xiubit.data;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.InvitationCfgRecordData;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/30
 */

public class InvestedCfgRecordRecylerAdpter extends BaseQuickAdapter<InvitationCfgRecordData, BaseViewHolder> {


    public InvestedCfgRecordRecylerAdpter() {
        super(R.layout.item_invite_record_list);
    }


    @Override
    protected void convert(BaseViewHolder helper, InvitationCfgRecordData bean) {
        helper.setText(R.id.nameTv, TextUtil.isEmpty(bean.getUserName()) ? "未认证" : bean.getUserName())
                .setText(R.id.phoneTv, bean.getMobile())
                .setText(R.id.infoTv, "0".equals(bean.getHaveInvitation()) ? "未发展下级" : "已发展下级")
                .setText(R.id.dateTv, bean.getRegisterTime());
    }

}
