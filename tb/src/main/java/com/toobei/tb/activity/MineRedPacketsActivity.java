package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.MyRedPacketsAdapter;
import com.toobei.tb.entity.RedPacket;
import com.toobei.tb.entity.RedPacketDatasData;
import com.toobei.tb.entity.RedPacketDatasDataEntity;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;
import org.xsl781.utils.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明： 我的红包
 * @date 2016-6-30
 */
public class MineRedPacketsActivity extends MyNetworkBaseActivity<RedPacketDatasDataEntity> implements IXListViewListener, OnItemClickListener, View.OnClickListener {

    private static final int AVALIABLE_VIEWPAGER = 0;
    private static final int USEDPAGE_VIEWPAGER = 1;
    private static final int EXPIREPAGE_VIEWPAGER = 2;

    private static final int AVALIABLE_REDPACKAGE_TYPE = 1;
    private static final int USED_REDPACKAGE_TYPE = 2;
    private static final int EXPIRE_REDPACKAGE_TYPE = 3;
    private static final int PAGESIZE = 20;

    private TextView t1, t2, t3;
    private ViewPager mPager;// 页卡内容
    private List<View> pagerViews; // Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号

    private int availableIndex = 1;
    private int usedIndex = 1;
    private int expireIndex = 1;
    private int bmpW;// 动画图片宽度
    private ImageView cursor;// 动画图片

    // 可派发红包
    private View availableView;
    private XListView availableXListView;
    private ListBlankLayout availableListBlankLayout;
    private MyRedPacketsAdapter availableAdapter;
    private List<RedPacket> availableRedPapers = new ArrayList<RedPacket>();

    //已经派发的红包
    private View usedView;
    private XListView usedXListView;
    private ListBlankLayout usedListBlankLayout;
    private MyRedPacketsAdapter usedAdapter;
    private List<RedPacket> usedRedPapers = new ArrayList<RedPacket>();

    // 过期红包
    private View overdueView;
    private XListView overdueXListView;
    private ListBlankLayout overdueListBlankLayout;
    private MyRedPacketsAdapter overdueAdapter;
    private List<RedPacket> expireRedPapers = new ArrayList<RedPacket>();
//    private Button askForRedPacketBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        /*
		 * 初始化title
		 */
        headerLayout.showTitle(getResources().getString(R.string.mine_redpackage));
        headerLayout.showLeftBackButton();
		/*
		 * 初始化viewpager
		 */

//        askForRedPacketBtn = (Button) findViewById(R.id.askForRedPacketBtn);
//        askForRedPacketBtn.setOnClickListener(this);
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);

        mPager = (ViewPager) findViewById(R.id.vPager);
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = (MyApp.displayWidth) / 3 - PixelUtil.dip2px(ctx, 12);
        cursor.getLayoutParams().width = bmpW;
        offset = PixelUtil.dip2px(ctx, 12);// 偏移量

        pagerViews = new ArrayList<View>();
        pagerViews.add(getAvailablePackets());
        pagerViews.add(getUsedPackets());
        pagerViews.add(getOverduePackets());
        mPager.setAdapter(new MyPagerAdapter(pagerViews));
        mPager.addOnPageChangeListener(new MyOnPageChangeListener());

        t1.setTextColor(ContextCompat.getColor(ctx,R.color.text_black_common));
        t1.getPaint().setFakeBoldText(true);
        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
    }

    @Override
    public void onRefresh() {
        switch (currIndex) {
            case AVALIABLE_VIEWPAGER:
                availableRedPapers.clear();
                loadData(false); //加载可用红包
                break;
            case USEDPAGE_VIEWPAGER:
                usedRedPapers.clear();
                getRedpackageDatasAndRefresh(USED_REDPACKAGE_TYPE, 1);//已用红包
                break;
            case EXPIREPAGE_VIEWPAGER:
                expireRedPapers.clear();
                getRedpackageDatasAndRefresh(EXPIRE_REDPACKAGE_TYPE, 1);//过期红包
                break;

        }
    }

    @Override
    public void onLoadMore() {

        switch (currIndex) {
            case AVALIABLE_VIEWPAGER:
                availableIndex++;
                getRedpackageDatasAndRefresh(AVALIABLE_REDPACKAGE_TYPE, availableIndex);//已用红包
                break;
            case USEDPAGE_VIEWPAGER:
                usedIndex++;
                getRedpackageDatasAndRefresh(USED_REDPACKAGE_TYPE, usedIndex);//已经使用红包
                break;
            case EXPIREPAGE_VIEWPAGER:
                expireIndex++;
                getRedpackageDatasAndRefresh(EXPIRE_REDPACKAGE_TYPE, expireIndex);//过期红包
                break;
        }

    }

    /*
     * 可用红包
     */
    private View getAvailablePackets() {
        availableView = LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null, false);
        availableListBlankLayout = (ListBlankLayout) availableView.findViewById(R.id.list_blank_layout);
        availableXListView = (XListView) availableListBlankLayout.initContentView(R.layout.list_blank_xlistview_layout);
        availableXListView.setXListViewListener(this);
        availableXListView.setAutoLoadMore(false);
        availableXListView.setPullLoadEnable(false);
        availableXListView.setDividerHeight((int) getResources().getDimension(R.dimen.divide_view_height_20));
        availableXListView.setBackgroundColor(ContextCompat.getColor(ctx,R.color.WHITE));
        return availableView;
    }

    /*
     * 已用红包
     */
    private View getUsedPackets() {
        usedView = LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null, false);
        usedListBlankLayout = (ListBlankLayout) usedView.findViewById(R.id.list_blank_layout);
        usedXListView = (XListView) usedListBlankLayout.initContentView(R.layout.list_blank_xlistview_layout);
        usedXListView.setXListViewListener(this);
        usedXListView.setAutoLoadMore(false);
        usedXListView.setPullLoadEnable(false);
        usedXListView.setDividerHeight((int) getResources().getDimension(R.dimen.divide_view_height_20));
        usedXListView.setBackgroundColor(ContextCompat.getColor(ctx,R.color.WHITE));
        return usedView;
    }

    /*
     * 过期红包
     */
    private View getOverduePackets() {
        overdueView = LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null, false);
        overdueListBlankLayout = (ListBlankLayout) overdueView.findViewById(R.id.list_blank_layout);
        overdueXListView = (XListView) overdueListBlankLayout.initContentView(R.layout.list_blank_xlistview_layout);
        overdueXListView.setXListViewListener(this);
        overdueXListView.setAutoLoadMore(false);
        overdueXListView.setPullLoadEnable(false);
        overdueXListView.setDividerHeight((int) getResources().getDimension(R.dimen.divide_view_height_20));
        overdueXListView.setBackgroundColor(ContextCompat.getColor(ctx,R.color.WHITE));
        return overdueView;
    }

    public void onStopLoad() {
        if (availableXListView != null) {
            availableXListView.stopRefresh();
            availableXListView.stopLoadMore();
        }
        if (usedXListView != null) {
            usedXListView.stopRefresh();
            usedXListView.stopLoadMore();
        }
        if (overdueXListView != null) {
            overdueXListView.stopRefresh();
            overdueXListView.stopLoadMore();
        }
    }

    private void refreshView(RedPacketDatasData data, int currentPage) {

        List<RedPacket> availableDatas;
        List<RedPacket> usedDatas;
        List<RedPacket> expireDatas;
        switch (currentPage) {
            case 0:
                availableXListView.setPullLoadEnable(true);
                availableDatas = data.getDatas();
                if (availableIndex == 1) {
                    availableRedPapers.clear();
                }

                availableRedPapers.addAll(availableDatas);
                //可用红包
                if (availableRedPapers != null && availableRedPapers.size() > 0) {
                    availableListBlankLayout.showContentView();
                    if (availableAdapter == null) {
                        availableAdapter = new MyRedPacketsAdapter(ctx, availableRedPapers, AVALIABLE_REDPACKAGE_TYPE);
                        availableXListView.setAdapter(availableAdapter);
                    } else {
                        availableAdapter.notifyDataSetChanged();
                    }
                } else {
                    availableListBlankLayout.showBlank(R.string.blank_list_text_myaccount);
                }
                if (data.getPageCount() <= data.getPageIndex() || data.getPageCount() <= 1) {
                    availableXListView.setPullLoadEnable(false);
                } else {
                    availableXListView.setPullLoadEnable(true);
                }
                break;
            case 1:
                usedXListView.setPullLoadEnable(true);
                usedDatas = data.getDatas();
                if (usedIndex == 1) {
                    usedRedPapers.clear();
                }
                usedRedPapers.addAll(usedDatas);
                //已用红包
                if (usedRedPapers != null && usedRedPapers.size() > 0) {
                    usedListBlankLayout.showContentView();
                    if (usedAdapter == null) {
                        usedAdapter = new MyRedPacketsAdapter(ctx, usedRedPapers, USED_REDPACKAGE_TYPE);
                        usedXListView.setAdapter(usedAdapter);
                    } else {
                        usedAdapter.notifyDataSetChanged();
                    }
                } else {
                    usedListBlankLayout.showBlank(R.string.blank_list_text_myaccount);
                }
                if (data.getPageCount() <= data.getPageIndex() || data.getPageCount() <= 1) {
                    usedXListView.setPullLoadEnable(false);
                } else {
                    usedXListView.setPullLoadEnable(true);
                }
                break;
            case 2:
                overdueXListView.setPullLoadEnable(true);
                if (expireIndex == 1) {
                    expireRedPapers.clear();
                }
                expireDatas = data.getDatas();
                expireRedPapers.addAll(expireDatas);
                //过期红包
                if (expireRedPapers != null && expireRedPapers.size() > 0) {
                    overdueListBlankLayout.showContentView();
                    if (overdueAdapter == null) {
                        overdueAdapter = new MyRedPacketsAdapter(ctx, expireRedPapers, EXPIRE_REDPACKAGE_TYPE);
                        overdueXListView.setAdapter(overdueAdapter);
                    } else {
                        overdueAdapter.notifyDataSetChanged();
                    }
                } else {
                    overdueListBlankLayout.showBlank(R.string.blank_list_text_no_data);
                }
                if (data.getPageCount() <= data.getPageIndex() || data.getPageCount() <= 1) {
                    overdueXListView.setPullLoadEnable(false);
                } else {
                    overdueXListView.setPullLoadEnable(true);
                }
                break;

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.askForRedPacketBtn: // 要红包
//                UserInfo myCfpUserInfo = MyApp.getInstance().getLoginService().getMyCfpUserInfo();
//                Log.e("yl","myCfpUserInfo=="+myCfpUserInfo);
//                String easeID = MyApp.getInstance().getLoginService().getMyCfpUserInfo().getEasemobAcct();
//                Intent intent = new Intent(this, ChatActivity.class);
//                intent.putExtra("askforredpackage", getResources().getString(R.string.askfor_redpackage));
//                intent.putExtra("toChatUsername", easeID);
//                startActivity(intent);
//                break;
        }
    }

    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    /**
     * ViewPager适配器
     */
    class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public int getCount() {
            return mListViews.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            int one = bmpW + offset;// 页卡1 -> 页卡2 偏移量
            Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);

            t1.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
            t1.getPaint().setFakeBoldText(false);
            t2.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
            t2.getPaint().setFakeBoldText(false);
            t3.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
            t3.getPaint().setFakeBoldText(false);
            switch (arg0) {
                case 0:
                    t1.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                    t1.getPaint().setFakeBoldText(true);

                    break;
                case 1:
                    t2.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                    t2.getPaint().setFakeBoldText(true);

                    break;
                case 2:
                    t3.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                    t3.getPaint().setFakeBoldText(true);

                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_my_red_packets;
    }

    @Override
    protected RedPacketDatasDataEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().redpaperRedpaperInfo(MyApp.getInstance().getLoginService().token, AVALIABLE_REDPACKAGE_TYPE + "", availableIndex + "", PAGESIZE + "");
    }

    @Override
    protected void onRefreshDataView(RedPacketDatasDataEntity data) {
        refreshView(data.getData(), AVALIABLE_VIEWPAGER); // 加载第一页可派发时 也加载已经派发红包  过期红包
        getRedpackageDatasAndRefresh(USED_REDPACKAGE_TYPE, 1);//已用红包
        getRedpackageDatasAndRefresh(EXPIRE_REDPACKAGE_TYPE, 1);// 已经过期

    }

    @Override
    protected void onRefreshDataViewCompleted() {
        onStopLoad();
    }

    /**
     * 功能： 获取已经派发和过期红包列表
     *
     * @param redpackageType
     * @param pageIndex
     */
    private void getRedpackageDatasAndRefresh(final int redpackageType, final int pageIndex) {
//        Log.e("yl","getRedpackageDatasAndRefresh");
        new MyNetAsyncTask(ctx, false) {
            RedPacketDatasDataEntity response;
            private RedPacketDatasData redPacketDatasData;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().redpaperRedpaperInfo(MyApp.getInstance().getLoginService().token, redpackageType + "", pageIndex + "", PAGESIZE + "");
            }

            @Override
            protected void onPost(Exception e) {
                onRefreshDataViewStart();
                if (e == null && response != null) {
                    if (response.getCodeNoCheck().equals("0") && response.getData().getDatas() != null) {
                        redPacketDatasData = response.getData();
                        refreshView(redPacketDatasData, redpackageType - 1); // redpackageType - 1 才是当前的viewPager页码

                    } else {
                        onResponseMsgError();
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                }

                onRefreshDataViewCompleted();

            }
        }.execute();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            onRefresh();

        }

    }
}
