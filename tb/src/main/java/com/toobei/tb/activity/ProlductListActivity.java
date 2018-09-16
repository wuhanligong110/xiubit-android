package com.toobei.tb.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.toobei.common.TopApp;
import com.toobei.common.entity.ProductClassifyStatisticsDetail;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyTitleBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.ProductListAdapter;
import com.toobei.tb.service.ProductService;
import com.toobei.tb.view.ProductListClickHeadLayout;

import org.xsl781.ui.xlist.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明: t呗 -> 产品列表
 *
 * @author qingyechen
 * @time 2016/12/24 0024 下午 3:10
 */
public class ProlductListActivity extends MyTitleBaseActivity implements XListView.IXListViewListener, View.OnClickListener {

    private ProductListClickHeadLayout productListClickHeadLayout;//产品筛选控件
    private int productPagerIndex = 1;
    public List<ProductDetail> financingProductList = new ArrayList<ProductDetail>();
    private XListView productListView;
    public ProductListAdapter productAdapter;
    private String productSort = "4";//1-默认排序 2-年化收益 3-产品期限 4-佣金
    private String productOrder = "0";//	0-升序 1-降序
    private int PAGE_SIZE = 10;
    private String cateId;//非必需 2-新手产品 3-短期产品 4-高收益产品 5-稳健收益产品 801-理财师推荐产品
    // 802-热推产品 多个一起查询的时候请使用,分开 如：2,3,4,5,801,802 不传时则查询所有的产品分类
    private String cateName;
    private ProductClassifyStatisticsDetail classifyStatisticsDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cateId = getIntent().getStringExtra("cateId");
        cateName = getIntent().getStringExtra("cateName");
        try {
            classifyStatisticsDetail = (ProductClassifyStatisticsDetail) getIntent().getSerializableExtra("classifyStatisticsDetail");
        } catch (Exception e) {
        }
        super.onCreate(savedInstanceState);
        initData();
        initTopTitle();
        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProductService.removeList(financingProductList);
    }

    private void initData() {
        refreshProductData();
    }

    private void refreshProductData() {
        productPagerIndex = 1;
        getProductData(true);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_product;

    }

    private void initTopTitle() {

        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(cateName);
        if ("802".equals(cateId)) {
            headerLayout.showTitle(getString(R.string.product_list_cfp_recommend_name));
        }
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    private void initView() {
        //产品头部点击筛选控件
        productListClickHeadLayout = (ProductListClickHeadLayout) findViewById(R.id.proccutlist_head_click_title);
        productListClickHeadLayout.setListener(new ProductListClickHeadLayout.OnHeadTitleClickListener() {
            @Override
            public void headTitleClicked(int index, boolean isDown) {
                productOrder = isDown ? "1" : "0";
                productSort = index + "";
                productPagerIndex = 1;

                if (productSort.equals("1")) {
                    productOrder = "0";

                }
                getProductData(true);
                //   ToastUtil.showCustomToast(" producthead index==" + index + " isDown ==" + isDown);
            }
        });

        final ImageButton backBtn = (ImageButton) findViewById(R.id.product_iv_back_to_top); // 点击返回顶部按钮
        backBtn.setOnClickListener(this);
        productListView = (XListView) findViewById(R.id.pinterHeadLV);
        if ("802".equals(cateId)) { //是理财师热推时显示数字排序  不显示头部筛选控件
            productListClickHeadLayout.setVisibility(View.GONE);
            productAdapter = new ProductListAdapter(this, financingProductList, true);
        } else {
            productListClickHeadLayout.setVisibility(View.VISIBLE);
            productAdapter = new ProductListAdapter(this, financingProductList);
        }
        if ("2".equals(cateId)) {
            productListClickHeadLayout.setVisibility(View.GONE);
            if (classifyStatisticsDetail != null) {
                // 设置产品分类顶部图片
//                headIv = new ImageView(ctx);
//                headIv.setScaleType(ImageView.ScaleType.FIT_XY);
//                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dipToPx(ctx, 100));
//                headIv.setLayoutParams(layoutParams);

                View headView = LayoutInflater.from(ctx).inflate(R.layout.head_fresh_product_list, null);
                ImageView headIv = (ImageView) headView.findViewById(R.id.img_banner);
                String cateLogo = classifyStatisticsDetail.getCateLogoInvestor();

                if (cateLogo != null && !TextUtils.isEmpty(cateLogo.trim())) {
//                    PhotoUtil.loadImageByImageLoader(cateLogo, headIv);
                    headIv.setVisibility(View.VISIBLE);

//                    headIv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            WebActivityCommon.showThisActivity((TopBaseActivity) ctx, MyApp.getInstance().getDefaultSp().getToobeiTrain(), "");
//                        }
//                    });
                    productListView.addHeaderView(headView);
                }
            }
        }
        productListView.setAdapter(productAdapter);
        ProductService.addProductListAndAdapter(financingProductList, productAdapter);
        productListView.setDividerHeight(0);
        productListView.setXListViewListener(this);
        productListView.setAutoLoadMore(false);
        productListView.setPullLoadEnable(false);

        productListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                backBtn.setVisibility(firstVisibleItem <= 6 ? View.GONE : View.VISIBLE);
            }
        });


    }

    /**
     * 理财师热推top10
     */
    private void getProductData(boolean openDialog) {
        new MyNetAsyncTask(ctx, openDialog) {
            ProductDatasDataEntity response;
            long loadSysTime ;
            @Override
            protected void doInBack() throws Exception {
                loadSysTime = System.currentTimeMillis();
                response = TopApp.getInstance().getHttpService().productClassifyPageList201(MyApp.getInstance().getLoginService().token, cateId, "", productOrder, productPagerIndex + "", PAGE_SIZE + "", productSort);
            }

            @Override
            protected void onPost(Exception e) {
                stopLoad();

                if (e == null && response != null) {

                    if (response.getCode().equals("0")) {
                        productListView.setAutoLoadMore(true);
                        productListView.setPullLoadEnable(true);
                        if (productPagerIndex == 1) {
                            financingProductList.clear();
                        }
                        List<ProductDetail> datas = response.getData().getDatas();
                        financingProductList.addAll(datas);
                        productAdapter.notifyDataSetChanged(loadSysTime);

                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                            productListView.setPullLoadEnable(false);
                        } else {
                            productListView.setPullLoadEnable(true);
                        }

                        productPagerIndex++;
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }


            }
        }.execute();
    }

    private void stopLoad() {
        productListView.stopRefresh();
        productListView.stopLoadMore();
    }

    @Override
    public void onRefresh() {
        productPagerIndex = 1;
        refreshProductData();
    }

    @Override
    public void onLoadMore() {
        getProductData(true);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.product_iv_back_to_top:  //listView返回首个item
                productListView.smoothScrollToPosition(0);
                break;
        }

    }
}
