package com.v5ent.xiubit.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toobei.common.utils.PhotoUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.TeamInfosBean;

import java.util.List;

/**
 * 机构团队成员adapter
 */
public class OrgInfoTeamAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TeamInfosBean> list;

    private String curRecommendProductName;


    public OrgInfoTeamAdapter(Activity activity, List<TeamInfosBean> list) {
        super();
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    private class ViewHolder {

        ImageView headIV;
        public TextView nameTV;
        public TextView positionTV;
        public TextView desTV;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        // 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_orginfo_team, null);
            viewHolder = new ViewHolder();
            viewHolder.headIV = (ImageView) convertView.findViewById(R.id.headIV);
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.positionTV = (TextView) convertView.findViewById(R.id.text_position);
            viewHolder.desTV = (TextView) convertView.findViewById(R.id.text_des);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final TeamInfosBean data = list.get(position);

        ImageLoader.getInstance().displayImage(MyApp.getInstance().getHttpService().getImageServerBaseUrl()+data.getOrgIcon()+"?f=png",viewHolder.headIV, PhotoUtil.normalImageOptions);
        viewHolder.nameTV.setText(data.getOrgMemberName());
        viewHolder.positionTV.setText(data.getOrgMemberGrade());
        viewHolder.desTV.setText(data.getOrgDescribe());



        return convertView;
    }


}
