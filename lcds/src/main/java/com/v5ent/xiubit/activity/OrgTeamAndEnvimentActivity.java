package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.toobei.common.view.listView.MyListView;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.OrgInfoDetailDesPicAdapter;
import com.v5ent.xiubit.data.OrgInfoTeamAdapter;
import com.v5ent.xiubit.entity.PlatformDetail;
import com.v5ent.xiubit.entity.TeamInfosBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/3
 */

public class OrgTeamAndEnvimentActivity extends MyTitleBaseActivity {

    @BindView(R.id.hightTeamTitleTv)
    TextView mHightTeamTitleTv;
    @BindView(R.id.teamListView)
    MyListView mTeamListView;
    @BindView(R.id.eviShootTitle)
    TextView mEviShootTitle;
    @BindView(R.id.orgEviRv)
    RecyclerView mOrgEviRv;
    private PlatformDetail mData;
    private List<PlatformDetail.OrgImageBean> orgEnvironmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mData = (PlatformDetail) getIntent().getSerializableExtra("data");
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headerLayout.showLeftBackButton();
        headerLayout.showTitle("高管团队及现场实拍");
        //团队成员
        List<TeamInfosBean> teamList = mData.getTeamInfos();
        if (teamList != null && teamList.size() > 0) {
            OrgInfoTeamAdapter teamAdapter = new OrgInfoTeamAdapter(ctx, teamList);
            mTeamListView.setAdapter(teamAdapter);
            mHightTeamTitleTv.setVisibility(View.VISIBLE);
        }else {
            mHightTeamTitleTv.setVisibility(View.GONE);
        }

        //现场实拍
       
        orgEnvironmentList = mData.getOrgEnvironmentList();
        mEviShootTitle.setVisibility(orgEnvironmentList.size() == 0 ? View.GONE: View.VISIBLE);
        OrgInfoDetailDesPicAdapter orgEnvironmentAdapter = new OrgInfoDetailDesPicAdapter(ctx, orgEnvironmentList);
        mOrgEviRv.setLayoutManager(new GridLayoutManager(ctx, 3));
        mOrgEviRv.setAdapter(orgEnvironmentAdapter);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_org_team_and_envi;
    }
}
