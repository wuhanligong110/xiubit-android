package com.toobei.tb.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toobei.common.entity.HighQualityPlatformDetail;
import com.toobei.common.entity.HighQualityPlatformEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.adapter.HighQualityPlatformAdapter;
import com.toobei.tb.adapter.OrgListAdapter;
import com.toobei.tb.entity.OrgInfoDatasDataEntity;
import com.toobei.tb.entity.OrgInfoDetail;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

import static com.toobei.common.utils.ToastUtil.showCustomToast;

/**
 * 公司: tophlc
 * 类说明: 我的理财师 ->推荐机构
 *
 * @author qingyechen
 * @time 2016/12/30 0030 下午 2:58
 */

public class FragmentMyCfpRecommendOrg extends FragmentBase implements IXListViewListener, View.OnClickListener {

    private static final int PAGE_SIZE = 10; // 默认分页大小
    private int pagerIndex = 1;

    private XListView orgListView;
    public OrgListAdapter orgListAdapter;
    private ListBlankLayout rootView;
    public List<OrgInfoDetail> orgList = new ArrayList<OrgInfoDetail>();
    private View headView;
    private RecyclerView recyclerView;
    private HighQualityPlatformAdapter highQualityadapter;
    private boolean hasAddHeadView=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);

            }
            return rootView;
        }
        rootView = (ListBlankLayout) inflater.inflate(R.layout.my_list_blanklayout, container, false);
        initView(rootView);
        getData(false);
        return rootView;
    }


    @SuppressWarnings("unchecked")
    private void initView(ListBlankLayout rootView) {
        orgListView = (XListView) rootView.initContentView(R.layout.list_blank_xlistview_layout);
        orgListView.setXListViewListener(this);
        orgListAdapter = new OrgListAdapter(ctx, orgList, true);
        orgListView.setAdapter(orgListAdapter);


    }


    private void getData(boolean openDialog) {
        new MyNetAsyncTask(ctx, openDialog) {


            private OrgInfoDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().queryPlannerRecommendPlatfrom(MyApp.getInstance().getLoginService().token, pagerIndex + "", PAGE_SIZE + "");
            }

            @Override
            protected void onPost(Exception e) {
                stopLoad();
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        List<OrgInfoDetail> datas = response.getData().getDatas();
                        if (pagerIndex == 1) {
                            orgList.clear();
                            if (datas == null || datas.size() <= 0) {
                                initHeadView();
                                return;
                            }
                        }

                        pagerIndex = response.getData().getPageIndex();
                        orgList.addAll(datas);
                        orgListAdapter.notifyDataSetChanged();

                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <1) {
                            orgListView.setPullLoadEnable(false);
                        } else {
                            orgListView.setPullLoadEnable(true);
                        }

                        pagerIndex++;
                    } else {
                        if (isAdded())
                            ToastUtil.showCustomToast(getActivity(), response.getErrorsMsgStr());
                    }
                } else {
                    if (isAdded())
                        showCustomToast(getActivity(), getString(R.string.pleaseCheckNetwork));
                }
                stopLoad();

            }


        }.execute();
    }

    private void getHighQualityPlatForm() {
        new MyNetAsyncTask(ctx, false) {


            private HighQualityPlatformEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().highQualityPlatform(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                stopLoad();
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && response.getData().getDatas() != null && response.getData().getDatas().size() > 0) {
                        List<HighQualityPlatformDetail> datas = response.getData().getDatas();
                        if (highQualityadapter == null) {
                            recyclerView = (RecyclerView) headView.findViewById(R.id.recycleView);
                            highQualityadapter = new HighQualityPlatformAdapter(ctx, datas);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
                            recyclerView.setAdapter(highQualityadapter);
                        } else {
                            highQualityadapter.refresh(datas);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 显示我的理财师没有推荐机构时的布局
     */
    private void initHeadView() {
        headView = LayoutInflater.from(ctx).inflate(R.layout.head_mycfp_recommend_org, null);
        if(!hasAddHeadView){
            orgListView.addHeaderView(headView);
            hasAddHeadView=true;
            orgListView.setAutoLoadMore(false);
            orgListView.setPullLoadEnable(false);
        }
        getHighQualityPlatForm();

    }

    @Override
    public void onRefresh() {
        pagerIndex = 1;
        getData(true);
    }


    @Override
    public void onLoadMore() {
        getData(true);
    }

    public void stopLoad() {
        if (orgListView != null) {
            orgListView.stopRefresh();
            orgListView.stopLoadMore();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.product_iv_back_to_top:  //listView返回首个item
                orgListView.smoothScrollToPosition(0);
                break;

//            case R.id.product_classify_head_iv:  //跳转产品分类顶部图片URL
//                String urlLink = proClassifyDetail.getUrlLink();
//                if (urlLink != null && !TextUtils.isEmpty(urlLink.trim())) {
//                    WebActivityCommon.showThisActivity((TopBaseActivity) ctx, urlLink, "");
//                }
//                break;

        }
    }
}
