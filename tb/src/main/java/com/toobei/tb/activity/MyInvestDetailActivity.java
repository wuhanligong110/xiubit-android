package com.toobei.tb.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.TextFormatUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.SlidingTabsTitlesPageAdapter;
import com.toobei.tb.entity.InvestProfitData;
import com.toobei.tb.entity.InvestProfitEntity;
import com.toobei.tb.entity.InvestRecordCountsData;
import com.toobei.tb.entity.InvestRecordCountsEntity;
import com.toobei.tb.entity.InvestRecordDetail;
import com.toobei.tb.entity.InvestRecordEntity;
import com.toobei.tb.entity.UnRecordInvestDetail;
import com.toobei.tb.entity.UnRecordInvestEntity;
import com.toobei.tb.view.InvestDetialChartPieLayout;
import com.toobei.tb.view.MyPagerSlidingTabStrip;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.xlist.XListView;
import org.xsl781.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 投资明细or我的投资
 * Created by hasee-pc on 2017/1/2.
 */

public class MyInvestDetailActivity extends MyNetworkBaseActivity<InvestRecordCountsEntity> implements View.OnClickListener, XListView.IXListViewListener {
    @BindView(R.id.vPager)
    ViewPager vPager;
    @BindView(R.id.pagerSlidingTabs)
    MyPagerSlidingTabStrip pagerSlidingTabs;
    @BindView(R.id.bottomTextTv)
    TextView bottomTextTv;
    @BindView(R.id.totalProfitTv)
    TextView totalProfitTv;
    @BindView(R.id.investOrgLl)
    LinearLayout investOrgLl;
    @BindView(R.id.investDetialChartPie)
    InvestDetialChartPieLayout investDetialChartPie;

    private int pageIndex = 1;
    private int pageSize = 10;
    private int pageCount;

    int currPageindex; //viewpager 当前的位置
    private InvestRecordCountsData mData;
    private Map<Integer,Integer> mMapPageIndex = new HashMap<Integer,Integer>();
    private Map<Integer,Integer> mMapPageCount = new HashMap<Integer,Integer>();
    private XListView mListView1;
    private XListView mListView2;
    private XListView mListView3;
    private XListView mListView4;

    List<UnRecordInvestDetail> mDataOther; // 由于做了分页数据加载，其他页签的数据需统一存储到该集合下，进行数据刷新
    List<InvestRecordDetail> mdata ;   //由于做了分页数据加载，需统一存储到该集合下，进行数据刷新
    List<InvestRecordDetail> mDatas1;  //募集中
    List<InvestRecordDetail> mDatas2;  //回款中
    List<InvestRecordDetail> mDatas3;  //已完成
    List<UnRecordInvestDetail> mDatas4; //其他
    private List<XListView> mListViews;
    private List<View> mPageViews;

    private String[] mTitles = new String[4]; //tab标题文字
    private SlidingTabsTitlesPageAdapter mAdapter;

    private InvestRecordEntity[] cacheDataCommones = new InvestRecordEntity[3]; //募集中、回款中、已完成页的数据缓存
    private UnRecordInvestEntity cacheDataOther; //其他页的数据缓存
    private int status = 0; //我的投资明细：investRecord 对应参数：status（状态:1=募集中|2=回款中3=回款完成）
    private String mTotalProfitAmount;
    private String title;
    private Boolean isFristOpen = true;   //第一次打开
    private InvestDetialCommonListAdapter mIDCLAdapter; //募集中,回款中,已完成页签adpter
    private InvestDetialOtherListAdapter mIDOLAdapter; //其他页签adpter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        status = StringUtils.toInt(getIntent().getStringExtra("status")) - 1;
        title = getIntent().getStringExtra("title");
        initView();
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_my_invest_detail;
    }

    @Override
    protected InvestRecordCountsEntity onLoadDataInBack() throws Exception {
        getChartPieData();
        refreshListDataAndUI();
        return MyApp.getInstance().getHttpService().getInvestRecordCounts(MyApp.getInstance().getLoginService().token);
    }

    /**
     * 获取饼状图数据
     */
    private void getChartPieData() {
        new MyNetAsyncTask(ctx, false) {
            InvestProfitEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getinvestProfit(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        InvestProfitData data = response.getData();
                        mTotalProfitAmount = Float.parseFloat(data.getInvestAmt()) + Float.parseFloat(data.getPaymentAmt()) + Float.parseFloat(data.getPaymentDoneAmt()) + "";
                        totalProfitTv.setText(TextFormatUtil.formatFloat2zero(mTotalProfitAmount));
                        investDetialChartPie.setInvestProfitData(data);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }


        }.execute();
    }

    private void refreshListDataAndUI() {
        if (currPageindex < 3) {
            getListDatasAndRerefresh(currPageindex + 1);
        } else {
            getOtherListDataAndRerefresh();
        }
    }

    @Override
    protected void onRefreshDataView(InvestRecordCountsEntity data) {
        mListViews.get(currPageindex).stopRefresh();
        refreshHeadTabUI(data.getData());
    }

    /**
     * 加载当前列表（除“其他”）数据
     *
     * @param status
     */
    private void getListDatasAndRerefresh(final int status) {
        new MyNetAsyncTask(ctx, false) {
            InvestRecordEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getInvestRecord(MyApp.getInstance().getLoginService().token, pageIndex + "", pageSize + "", status + "");
                pageCount = response.getData().getPageCount();
                if(pageIndex == 1){
                    cacheDataCommones[status - 1] = response;
                    mdata = response.getData().getDatas();
                }
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    mMapPageCount.put(currPageindex,pageCount);//存储对应页签服务器返回的页数
                    if (response.getCode().equals("0")) {
                        mMapPageIndex.put(currPageindex,pageIndex);//存储对应页签的对应已加载的页数
                        switch (status) {
                            case 1:
                                mDatas1 = response.getData().getDatas();
                                storePageData(mDatas1);
                                break;
                            case 2:
                                mDatas2 = response.getData().getDatas();
                                storePageData(mDatas2);
                                break;
                            case 3:
                                mDatas3 = response.getData().getDatas();
                                storePageData(mDatas3);
                                break;
                        }
                        refreshTabListUI(status);

                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }


        }.execute();

    }

    private void storePageData(List<InvestRecordDetail> pageData) {
        if(pageIndex > 1 && pageData.size() != 0){
            for(int i = 0 ; i < pageData.size() ; i++) {
                mdata.add(pageData.get(i));
            }
            pageData.clear();
            pageData.addAll(mdata);
        }
    }

    private void storePageOtherData(List<UnRecordInvestDetail> pageOtherData) {
        if(pageIndex > 1 && pageOtherData.size() != 0){
            for(int i = 0 ; i < pageOtherData.size() ; i++) {
                mDataOther.add(pageOtherData.get(i));
            }
            pageOtherData.clear();
            pageOtherData.addAll(mDataOther);
        }
    }

    private void refreshHeadTabUI(InvestRecordCountsData data) {
        mTitles[0] = "募集中(" + data.getTzz() + ")";
        mTitles[1] = "回款中(" + data.getHkz() + ")";
        mTitles[2] = "已完成(" + data.getHkwc() + ")";
        mTitles[3] = "其他(" + data.getQt() + ")";
        mAdapter.notifyDataSetChanged();
        int[] counts = {data.getTzz(), data.getHkz(), data.getHkwc(), data.getQt()};
        for (int i = 0; i < counts.length && isFristOpen; i++) {
            if (counts[i] > 0) {
                vPager.setCurrentItem(i);
                isFristOpen = false;
                return;
            }
        }
    }

    /**
     * 刷新当前列表UI
     *
     * @param status
     */
    private void refreshTabListUI(int status) {
        switch (status) {
            case 1:
                if (mDatas1 == null || mDatas1.size() == 0) {
                    ListBlankLayout blankLayout = (ListBlankLayout) mPageViews.get(0);
                    blankLayout.showBlank("暂无募集中项目");
                    bottomTextTv.setVisibility(View.INVISIBLE);
                } else {
                    mIDCLAdapter = new InvestDetialCommonListAdapter(ctx, mDatas1);
                    if(pageIndex > 1 && mIDCLAdapter != null){
                        mIDCLAdapter.notifyDataSetChanged();
                    }else{
                        mListView1.setAdapter(mIDCLAdapter);
                        bottomTextTv.setVisibility(View.VISIBLE);
                    }
                }
                mListView1.stopLoadMore();
                break;
            case 2:
                if (mDatas2 == null || mDatas2.size() == 0) {
                    ListBlankLayout blankLayout = (ListBlankLayout) mPageViews.get(1);
                    blankLayout.showBlank("暂无回款中项目");
                    bottomTextTv.setVisibility(View.INVISIBLE);
                } else {
                    mIDCLAdapter = new InvestDetialCommonListAdapter(ctx, mDatas2);
                    if(pageIndex > 1 && mIDCLAdapter != null){
                        mIDCLAdapter.notifyDataSetChanged();
                    }else{
                        mListView2.setAdapter(mIDCLAdapter);
                        bottomTextTv.setVisibility(View.VISIBLE);
                    }
                }
                mListView2.stopLoadMore();
                break;
            case 3:
                if (mDatas3 == null || mDatas3.size() == 0) {
                    ListBlankLayout blankLayout = (ListBlankLayout) mPageViews.get(2);
                    blankLayout.showBlank("暂无已完成项目");
                    bottomTextTv.setVisibility(View.INVISIBLE);
                } else {
                    mIDCLAdapter = new InvestDetialCommonListAdapter(ctx, mDatas3);
                    if(pageIndex > 1 && mIDCLAdapter != null){
                        mIDCLAdapter.notifyDataSetChanged();
                    }else{
                        mListView3.setAdapter(mIDCLAdapter);
                        bottomTextTv.setVisibility(View.VISIBLE);
                    }
                }
                mListView3.stopLoadMore();
                break;
            case 4:
                if (mDatas4 == null || mDatas4.size() == 0) {
                    ListBlankLayout blankLayout = (ListBlankLayout) mPageViews.get(3);
                    blankLayout.showBlank("暂无其他项目");
                    bottomTextTv.setVisibility(View.INVISIBLE);
                } else {
                    mIDOLAdapter = new InvestDetialOtherListAdapter(ctx, mDatas4);
                    if(pageIndex > 1 && mIDOLAdapter != null){
                        mIDOLAdapter.notifyDataSetChanged();
                    }else{
                        mListView4.setAdapter(mIDOLAdapter);
                        bottomTextTv.setVisibility(View.VISIBLE);
                    }
                }
                mListView4.stopLoadMore();
                break;

        }
    }


    /**
     * 加载“其他”列表的数据并刷新UI
     */
    private void getOtherListDataAndRerefresh() {
        new MyNetAsyncTask(ctx, false) {

            UnRecordInvestEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getUnRecordInvest(MyApp.getInstance().getLoginService().token, pageIndex + "", pageSize + "");
                pageCount = response.getData().getPageCount();
                if(pageIndex == 1){
                    cacheDataOther = response;
                    mDataOther = response.getData().getDatas();
                }
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    mMapPageCount.put(currPageindex,pageCount);//存储对应页签服务器返回的页数
                    if (response.getCode().equals("0")) {
                        mMapPageIndex.put(currPageindex,pageIndex);//存储对应页签的对应已加载的页数
                        mDatas4 = response.getData().getDatas();
                        storePageOtherData(mDatas4);
                        refreshTabListUI(4);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }


        }.execute();
    }


    private void initView() {
        headerLayout.showTitle(title);
        headerLayout.showLeftBackButton();

        initListView();
        initViewPager();
        pagerSlidingTabs.setViewPager(vPager);

        investOrgLl.setOnClickListener(this);
    }

    private void initListView() {
        ListBlankLayout listBlankView1 = (ListBlankLayout) LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null, false);
        ListBlankLayout listBlankView2 = (ListBlankLayout) LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null, false);
        ListBlankLayout listBlankView3 = (ListBlankLayout) LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null, false);
        ListBlankLayout listBlankView4 = (ListBlankLayout) LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null, false);
        mPageViews = new ArrayList<>();
        mPageViews.add(listBlankView1);
        mPageViews.add(listBlankView2);
        mPageViews.add(listBlankView3);
        mPageViews.add(listBlankView4);

        mListViews = new ArrayList<>();
        //四个Tab对应的listView
        mListView1 = (XListView) listBlankView1.initContentView(R.layout.list_blank_xlistview_layout);
        mListView2 = (XListView) listBlankView2.initContentView(R.layout.list_blank_xlistview_layout);
        mListView3 = (XListView) listBlankView3.initContentView(R.layout.list_blank_xlistview_layout);
        mListView4 = (XListView) listBlankView4.initContentView(R.layout.list_blank_xlistview_layout);

        mListViews.add(mListView1);
        mListViews.add(mListView2);
        mListViews.add(mListView3);
        mListViews.add(mListView4);

        for (int i = 0; i < mListViews.size(); i++) {
            XListView lv = mListViews.get(i);
            lv.setXListViewListener(this);
            lv.setAutoLoadMore(false);
            lv.setPullLoadEnable(true);
        }
    }

    private void initViewPager() {

        vPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mAdapter = new SlidingTabsTitlesPageAdapter(mPageViews, mTitles);
        vPager.setAdapter(mAdapter);
        if (status >= 0) {
            vPager.setCurrentItem(status);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.investOrgLl:
                startActivity(new Intent(this, MyInvestOrgActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1; //刷新时只加载第一页
        mListViews.get(currPageindex).setPullLoadEnable(true);//刷新时需要将listview的上拉加载更多置为true
        loadDataFromNet(false);
    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        if(mMapPageCount.get(currPageindex) != null){//从集合中获取对应页签的页数
            pageCount = mMapPageCount.get(currPageindex);
        }
        if(pageIndex > pageCount){//当加载的页数大于服务器返回的总页数时取消加载更多功能
            mListViews.get(currPageindex).setPullLoadEnable(false);
            return ;
        }
        refreshListDataAndUI();
    }


    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            currPageindex = arg0;
            if(mMapPageIndex.get(currPageindex) != null){
                pageIndex = mMapPageIndex.get(currPageindex);
            }
            mListViews.get(currPageindex).setPullLoadEnable(true);//切换页签开启对应页签的上拉加载更多功能
            if (currPageindex < 3) {
                if (cacheDataCommones[currPageindex] == null) {
                    pageIndex = 1;//若刷新就置为1，只刷新第一页
                    getListDatasAndRerefresh(currPageindex + 1);
                } else if (cacheDataCommones[currPageindex].getData().getDatas().size() == 0) {
                    bottomTextTv.setVisibility(View.INVISIBLE);
                } else {
                    bottomTextTv.setVisibility(View.VISIBLE);
                }
            } else {
                if (cacheDataOther == null) {
                    pageIndex = 1;//若刷新就置为1，只刷新第一页
                    getOtherListDataAndRerefresh();
                } else if (cacheDataOther.getData().getDatas().size() == 0) {
                    bottomTextTv.setVisibility(View.INVISIBLE);
                } else {
                    bottomTextTv.setVisibility(View.VISIBLE);
                }
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 跳转机构账户详情
     */
    private void gotoAccountdetail(String orgNo, String platfromName) {
        Intent intent = new Intent(ctx, OrgUserCenterWebActivity.class);
        intent.putExtra("orgNo", orgNo);
        intent.putExtra("orgName", platfromName);
        intent.putExtra("title", platfromName);
        ctx.startActivity(intent);
    }

    class InvestDetialCommonListAdapter extends BaseListAdapter<InvestRecordDetail> {

        public InvestDetialCommonListAdapter(Context ctx) {
            super(ctx);
        }

        public InvestDetialCommonListAdapter(Context ctx, List<InvestRecordDetail> datas) {
            super(ctx, datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ctx).inflate(R.layout.item_myinvest_detail, null, true);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            InvestRecordDetail bean = this.datas.get(position);
            holder.productNameTv.setText(bean.getProductName());
            holder.updataDateTv.setText(bean.getUpdateDate());
            holder.investAmountTv.setText(TextFormatUtil.formatFloat2zero(bean.getInvestAmount()));
            holder.profitTv.setText(TextFormatUtil.formatFloat2zero(bean.getProfit()));
            holder.startDateTv.setText(bean.getStartDate());
            holder.endDateTv.setText(bean.getEndDate());

            String[] dayStr = formateDaytime(bean.getDay());
            holder.dayTv.setText(dayStr[0]);
            holder.dayTypeTv.setText(dayStr[1]);

            final String orgNo = bean.getPlatfrom();
            final String platfromName = bean.getPlatfromName();
            holder.gotoInvestDetailLi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//跳转第三方个人中心
                    gotoAccountdetail(orgNo, platfromName);

                }
            });

            if (position == datas.size() - 1) {
                holder.divideView.setVisibility(View.GONE);
            }

            return convertView;
        }

        private String[] formateDaytime(String str) {
            str = str.replace(" ", "");
            String[] split = str.split(",");
            if (split.length == 2) {
                return split;
            } else if (split.length == 4) {
                String s1 = split[0] + "~" + split[2];
                split[0] = s1;
                split[1] = split[3];
            }

            return split;
        }

        class ViewHolder {
            TextView productNameTv;
            TextView updataDateTv;
            LinearLayout gotoInvestDetailLi;
            TextView investAmountTv;
            TextView profitTv;
            TextView dayTv;
            TextView startDateTv;
            TextView endDateTv;
            View divideView;
            TextView dayTypeTv;

            public ViewHolder(View view) {
                ButterKnife.bind(view);
                productNameTv = (TextView) view.findViewById(R.id.productNameTv);
                profitTv = (TextView) view.findViewById(R.id.profitTv);
                gotoInvestDetailLi = (LinearLayout) view.findViewById(R.id.goto_invest_detail_Li);
                investAmountTv = (TextView) view.findViewById(R.id.investAmountTv);
                updataDateTv = (TextView) view.findViewById(R.id.updataDateTv);
                dayTv = (TextView) view.findViewById(R.id.dayTv);
                dayTypeTv = (TextView) view.findViewById(R.id.dayType);

                startDateTv = (TextView) view.findViewById(R.id.startDateTv);
                endDateTv = (TextView) view.findViewById(R.id.endDateTv);
                divideView = view.findViewById(R.id.divideView);

            }
        }

    }

    class InvestDetialOtherListAdapter extends BaseListAdapter<UnRecordInvestDetail> {


        public InvestDetialOtherListAdapter(Context ctx) {
            super(ctx);
        }

        public InvestDetialOtherListAdapter(Context ctx, List<UnRecordInvestDetail> datas) {
            super(ctx, datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ctx).inflate(R.layout.item_myinvest_detail_other, null, true);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            UnRecordInvestDetail bean = this.datas.get(position);
            holder.productNameTv.setText(bean.getProductName());
            holder.timeTv.setText(bean.getTime());
            holder.investAmountTv.setText(TextFormatUtil.formatFloat2zero(bean.getInvestAmt()));
            holder.deadLineTv.setText(bean.getDeadLine());
            String deadLineType = bean.getDeadLineType();

            switch (Integer.parseInt(deadLineType)) {
                case 0:
                    holder.deadLineType.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.deadLineType.setText("天");
                    break;
                case 2:
                    holder.deadLineType.setText("月");
                    break;
                case 3:
                    holder.deadLineType.setText("年");
                    break;
            }

            if (position == datas.size() - 1) {
                holder.divideView.setVisibility(View.GONE);
            }
            final String url = bean.getUrl();
            holder.gotoInvestDetailLi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转url
                    Intent intent = new Intent(ctx, WebActivityCommon.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "个人中心");
                    startActivity(intent);

                }
            });
            return convertView;
        }


        class ViewHolder {
            TextView productNameTv;
            TextView timeTv;
            LinearLayout gotoInvestDetailLi;
            TextView investAmountTv;
            TextView deadLineTv;
            TextView deadLineType;
            View divideView;

            public ViewHolder(View view) {
                productNameTv = (TextView) view.findViewById(R.id.productNameTv);
                timeTv = (TextView) view.findViewById(R.id.timeTv);
                gotoInvestDetailLi = (LinearLayout) view.findViewById(R.id.goto_invest_detail_Li);
                investAmountTv = (TextView) view.findViewById(R.id.investAmountTv);
                deadLineTv = (TextView) view.findViewById(R.id.deadLineTv);
                deadLineType = (TextView) view.findViewById(R.id.deadLineType);
                divideView = view.findViewById(R.id.divideView);
            }
        }
    }


}
