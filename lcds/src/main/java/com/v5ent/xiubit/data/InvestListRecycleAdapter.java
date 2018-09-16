package com.v5ent.xiubit.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.toobei.common.data.BaseRecycleViewAdapter;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.toobei.common.entity.InvestedPlatformData;
import com.v5ent.xiubit.activity.OrgUserCenterActivity;
import com.v5ent.xiubit.service.JumpThirdPartyService;
import com.v5ent.xiubit.view.MyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.v5ent.xiubit.service.JumpThirdPartyService.JUMP_TYPE_USER_CENTER;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/29
 */

public class InvestListRecycleAdapter extends BaseRecycleViewAdapter<InvestedPlatformData.PlatformInvestStatisticsListBean> {
    private Context ctx;

    public InvestListRecycleAdapter(Context context) {
        super(context);
        this.ctx = context;
    }

    public InvestListRecycleAdapter(Context context, List<InvestedPlatformData.PlatformInvestStatisticsListBean> datas) {
        super(context, datas);
        this.ctx = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_invest_org_mine, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final InvestedPlatformData.PlatformInvestStatisticsListBean bean = datas.get(position);
        String imageUrl = MyApp.getInstance().getHttpService().getImageUrlFormMd5(bean.platformLogo);
        Glide.with(context).load(imageUrl).into(viewHolder.mOrgLogoIV);
        viewHolder.mInvestingAmtTv.setText(bean.investingAmt);
        viewHolder.mInvestingProfitTv.setText(bean.investingProfit);
        boolean skipNeedBind = !"0".equals(bean.orgProductUrlSkipBindType);

        final boolean finalSkipNeedBind = skipNeedBind;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0-直接跳转第三方 1-跳转本地机构详情页
                switch (bean.orgUrlSkipJumpType) {
                    case "0":
                        JumpThirdPartyService service = new JumpThirdPartyService(
                                JUMP_TYPE_USER_CENTER
                                , finalSkipNeedBind
                                , (Activity) context
                                , bean.orgName
                                , bean.orgNumber, null);
                        service.run();
                        break;
                    case "1":
                        Intent intent = new Intent(ctx, OrgUserCenterActivity.class);
                        intent.putExtra("orgName",bean.orgName);
                        intent.putExtra("orgNumber",bean.orgNumber);
                        ctx.startActivity(intent);
                        break;

                }


            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orgLogoIV)
        ImageView mOrgLogoIV;
        @BindView(R.id.investingAmtTv)
        MyTextView mInvestingAmtTv;
        @BindView(R.id.investingProfitTv)
        MyTextView mInvestingProfitTv;
        View itemView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }
    }
}
