package com.toobei.tb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.toobei.tb.R;
import com.toobei.tb.activity.WebActivityCommon;
import com.toobei.tb.entity.PlatformDetail;
import com.toobei.tb.util.C;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;


/**
 * 机构动态 平台动态
 *
 * @author Administrator
 * @time 2016/11/8 0008 下午 3:37
 */
public class OrgInfoNewsAdapter extends BaseListAdapter<PlatformDetail.OrgDynamicListBean> {

    public OrgInfoNewsAdapter(Activity ctx, List<PlatformDetail.OrgDynamicListBean> datas) {
        super(ctx, datas);
        this.datas = datas;
    }

    public OrgInfoNewsAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_org_detail_news_list, null);
        }
        final PlatformDetail.OrgDynamicListBean data = getItem(position);
        TextView nameTV = ViewHolder.findViewById(convertView, R.id.text_org_detail_news_name);
        TextView timeTV = ViewHolder.findViewById(convertView, R.id.text_org_detail_news_time);

        nameTV.setText(data.getOrgTitle()); //公告内容
        timeTV.setText(data.getCreateTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put(ctx.getString(R.string.umeng_institution_detail_key), ctx.getString(R.string.umeng_institution_detail_news_item));
//                MobclickAgent.onEvent(ctx, "platform_module", map);


                // 2016/11/15 0015  informationDetailUrl	机构动态 、资讯 、课堂详情链接
                String orgDynamicUrl = data.getOrgDynamicUrl();
                if (TextUtil.isEmpty(orgDynamicUrl)) {
                    orgDynamicUrl = C.URL_ORG_ORGANIZATION_NEWS + "?id=" + data.getId();
                   // orgDynamicUrl = MyApp.getInstance().getDefaultSp().getInformationDetailUrl() +"?type="+ C.INFOMATION_URL_TYPE_ORG_DYNAMIC+ "&id=" + data.getId();
                }
                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, orgDynamicUrl, "");
            }
        });

        return convertView;
    }

}
