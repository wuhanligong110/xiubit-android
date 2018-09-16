package com.v5ent.xiubit.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.toobei.common.data.BaseRecycleViewAdapter;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.view.photoview.PhotoView;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.ViewPagerActivity;
import com.v5ent.xiubit.entity.PlatformDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台详情简介图片
 *
 * @author Administrator
 * @time 2016/11/8 0008 下午 4:38
 */
public class OrgInfoDetailDesPicAdapter extends BaseRecycleViewAdapter<PlatformDetail.OrgImageBean> {


    private final List<PlatformDetail.OrgImageBean> OrgHonorListBean;
    private View rootview;
    private MyItemClickListener listener;

    public OrgInfoDetailDesPicAdapter(Context context, List<PlatformDetail.OrgImageBean> datas) {
        super(context, datas);
        this.OrgHonorListBean = datas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        rootview = inflater.inflate(R.layout.item_org_detail_pic, null);


        return new HomeHotInstitutionViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HomeHotInstitutionViewHolder viewHolder = (HomeHotInstitutionViewHolder) holder;

        String imgUrl = datas.get(position).getOrgPicture();

        PhotoUtil.loadImageByGlide(context, imgUrl + "?f=png&w=300", viewHolder.orgDesIV);
        viewHolder.orgDesIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<String> imgUrlstrings = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    imgUrlstrings.add(datas.get(i).getOrgPicture());

                }
                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putStringArrayListExtra("imgUrlstrings", imgUrlstrings);
                intent.putExtra("imgPosition", position);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    viewHolder.orgDesIV.setTransitionName(imgUrlstrings.toString());
                    ActivityOptionsCompat qing = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,//
                            new Pair<View, String>( viewHolder.orgDesIV,
                                    context.getString(R.string.transition_scale_photoview)));
                    ActivityCompat.startActivity( context, intent, qing.toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });
    }


    class HomeHotInstitutionViewHolder extends RecyclerView.ViewHolder {


        public PhotoView orgDesIV;

        public HomeHotInstitutionViewHolder(View itemView) {
            super(itemView);
            orgDesIV = (PhotoView) itemView.findViewById(R.id.desIv);
            orgDesIV.disenable();

        }
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    public interface MyItemClickListener {
        void onItemClick(PhotoView orgDesIV, String imgUrl);
    }

}
