package com.toobei.tb.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.toobei.common.TopNetFragmentBase;
import com.toobei.common.entity.HomePagerBanners;
import com.toobei.common.entity.HomePagerBannersDatasDataEntity;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.loopviewpager.AutoScrollViewPager;
import com.toobei.common.view.loopviewpager.indicator.LinePageIndicator;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.MainActivity;
import com.toobei.tb.adapter.HomeBannerAdapter;
import com.toobei.tb.adapter.OrgListAdapter;
import com.toobei.tb.entity.OrgInfoDatasDataEntity;
import com.toobei.tb.entity.OrgInfoDetail;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司:Tophlc 类说明：机构
 *
 * @date 2016年2月15日
 */
public class FragmentOrg extends TopNetFragmentBase<OrgInfoDatasDataEntity> implements IXListViewListener, View.OnClickListener {

    private static final int PAGE_SIZE = 10; // 默认分页大小
    private int orgListPagerIndex = 1;

    private ImageButton backBtn;
    private XListView orgInfoListView;
    private List<OrgInfoDetail> orgInfoList = new ArrayList<OrgInfoDetail>();
    private OrgListAdapter orgAdapter;
    //机构筛选条件(V2.0后不使用)
    private String productDeadLine = "";//产品期限
    private String securityLevel = "";  //安全等级
    private String yearProfit = "";   //年化

    private LinePageIndicator indicator;
    private AutoScrollViewPager vpager;


    @Override
    protected void initSucessViewAndData(View mLoadSucessView) {
        initView(mLoadSucessView);
        getBannersDatas(false);
    }

    @Override
    protected int onGetrootViewViewLayout() {
        return R.layout.fragment_institution;
    }

    @Override
    protected OrgInfoDatasDataEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getOrgInfoListDatas(MyApp.getInstance().getLoginService().token, orgListPagerIndex + "", PAGE_SIZE + "", productDeadLine, securityLevel, yearProfit);
    }

    @Override
    protected void onRefreshDataView(OrgInfoDatasDataEntity response) {
        orgInfoListView.setPullLoadEnable(true);
        List<OrgInfoDetail> orgInfodatas = response.getData().getDatas();
        orgListPagerIndex = response.getData().getPageIndex();
        if (orgListPagerIndex == 1) {
            orgAdapter.refresh(orgInfodatas);
        } else {
            orgAdapter.addAll(orgInfodatas);
        }
        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
            orgInfoListView.setPullLoadEnable(false);
        } else {
            orgInfoListView.setPullLoadEnable(true);
        }

        orgListPagerIndex++;
    }

    @Override
    protected void onRefreshDataViewStart() {
        stopLoad();
        orgInfoListView.setAutoLoadMore(true);
        orgInfoListView.setPullLoadEnable(true);
    }

    private void initView(View rootView) {

        backBtn = (ImageButton) rootView.findViewById(R.id.org_iv_back_to_top); //点击返回顶部
        backBtn.setOnClickListener(this);
        orgInfoListView = (XListView) rootView.findViewById(R.id.pinterHeadLV);
        View headView = LayoutInflater.from(activity).inflate(R.layout.layout_institution_headview, null);
        orgInfoListView.addHeaderView(headView);
        vpager = (AutoScrollViewPager) headView.findViewById(R.id.institution_vpager);
        indicator = (LinePageIndicator) headView.findViewById(R.id.instituion_indicator);

        orgAdapter = new OrgListAdapter(activity, orgInfoList);
        orgInfoListView.setAdapter(orgAdapter);
        orgInfoListView.setDividerHeight(0);
        orgInfoListView.setAutoLoadMore(false);
        orgInfoListView.setPullLoadEnable(false);
        orgInfoListView.setXListViewListener(this);
        orgInfoListView.setOnScrollListener(new AbsListView.OnScrollListener() {  //显示隐藏返回listView顶部的按钮
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                backBtn.setVisibility(firstVisibleItem <= 7 ? View.GONE : View.VISIBLE);
            }
        });
    }


    /**
     * 获取banner 数据
     * advPlacement	广告位置描述	string   pc首页页中：pc_idx_middle (必填),pc端banner：pc_banner,平台banner:platform_banner,产品banner:product_banner
     * appType	端口	number                   理财师1，投资端2 （必填）
     *
     * @param isRefresh
     */
    private void getBannersDatas(final boolean isRefresh) {

        new NetCachAsyncTask<HomePagerBannersDatasDataEntity>("getInstitutionBannersDatas", 60 * 24 * 1, false, isRefresh) {

            @Override
            protected HomePagerBannersDatasDataEntity getData() throws Exception {
                return MyApp.getInstance().getHttpService().homepageAdvs(MyApp.getInstance().getLoginService().token, "platform_banner", "2");

            }

            @Override
            protected void onPost(Exception e, HomePagerBannersDatasDataEntity response, boolean isDataFromServer) {

                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        List<HomePagerBanners> instituitonBannerses = response.getData().getDatas();

                        if (instituitonBannerses != null && instituitonBannerses.size() > 0) {
                            initBannersView(instituitonBannerses);
                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(MyApp.getInstance().getString(R.string.pleaseCheckNetwork));
                }

            }
        }.execute();
    }

    private void initBannersView(final List<HomePagerBanners> datas) {
        HomeBannerAdapter adapter = new HomeBannerAdapter(activity, datas, HomeBannerAdapter.BannerType.INSTITUTUON_BANNER_TYPE);
        vpager.setAdapter(adapter);
//        if (indicator.getViewPager() == null) {
            //indicator修改过后可以给无限轮播的viewpager使用
            indicator.setViewPager(vpager, datas.size());
//        }
        if (datas.size() > 1) {
            vpager.startloop();
        }
        //避免indicator控件不靠右而居中
        indicator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void onRefresh() {
        if (MyApp.getInstance().getLoginService().isCachPhoneExist()) {
            ((MainActivity) activity).getTopMsgCount();
        }
        orgListPagerIndex = 1;
        loadData(true);
        getBannersDatas(true);

    }

    @Override
    public void onLoadMore() {
        loadData(true);
    }

    public void stopLoad() {
        if (orgInfoListView != null) {
            orgInfoListView.stopRefresh();
            orgInfoListView.stopLoadMore();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.org_iv_back_to_top:
                orgInfoListView.smoothScrollToPosition(0);
                break;
        }
    }


}
