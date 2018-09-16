package com.toobei.tb.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.toobei.common.TopApp;
import com.toobei.common.entity.ProductClassifyStatisticsDetail;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.adapter.ProductListAdapter;
import com.toobei.tb.view.ProductListClickHeadLayout;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

import static com.toobei.common.utils.ToastUtil.showCustomToast;

/**
 * 公司:Tophlc 类说明：理财产品-分类产品
 *
 * @date 2016年2月15日
 */
@SuppressLint("ValidFragment")
public class FragmentProductClassifyDetail extends FragmentBase implements IXListViewListener, View.OnClickListener {

    private static final int PAGE_SIZE = 10; // 默认分页大小
    private int productPagerIndex = 1;
    private ProductListClickHeadLayout productListClickHeadLayout;//产品筛选控件
    private XListView productListView;
    public ProductListAdapter productAdapter;
    private View rootView;
    private ImageView headIv;
    private View line;
    public List<ProductDetail> financingProductList = new ArrayList<ProductDetail>();
    private ProductClassifyStatisticsDetail proClassifyDetail;
    private String productSort = "1";//1-默认排序 2-年化收益 3-产品期限 4-佣金排序
    private String productOrder = "1";//	0-升序 1-降序


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);

            }
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_product, container, false);
        initView(rootView);
        onRefresh();
        return rootView;
    }

    @SuppressLint("ValidFragment")
    public FragmentProductClassifyDetail(ProductClassifyStatisticsDetail prductClassifyDetail) {
        this.proClassifyDetail = prductClassifyDetail;
    }


    @SuppressWarnings("unchecked")
    private void initView(View view) {

        //产品头部点击筛选控件
        productListClickHeadLayout = (ProductListClickHeadLayout) view.findViewById(R.id.proccutlist_head_click_title);

        line = view.findViewById(R.id.line);
        line.setVisibility(View.VISIBLE);

        if (!"2".equals(proClassifyDetail.getCateId())) { //不是新手标时显示筛选控件
            productListClickHeadLayout.setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.product_list_hot10).setVisibility(View.GONE); //隐藏顶部热门top10标签



        final ImageButton backBtn = (ImageButton) view.findViewById(R.id.product_iv_back_to_top); // 点击返回顶部按钮
        backBtn.setOnClickListener(this);
        productListView = (XListView) view.findViewById(R.id.pinterHeadLV);
        productAdapter = new ProductListAdapter(ctx, financingProductList);
        //  orgListAdapter.setOnBtnRecommendClicked(this);// 点击推荐推荐
        productListView.setAdapter(productAdapter);
        productListView.setDividerHeight(0);
        productListView.setXListViewListener(this);
        productListView.setAutoLoadMore(false);
        productListView.setPullLoadEnable(false);

//        // 设置产品分类顶部图片
//        headIv = new ImageView(ctx);
//        headIv.setScaleType(ImageView.ScaleType.FIT_XY);
//        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dipToPx(ctx, 100));
//        headIv.setLayoutParams(layoutParams);
//        String cateLogo = proClassifyDetail.getCateLogoInvestor();
//        if (cateLogo != null && !TextUtils.isEmpty(cateLogo.trim())) {
//            PhotoUtil.loadImageByImageLoader(cateLogo, headIv);
//            headIv.setVisibility(View.VISIBLE);
//
//            headIv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    WebActivityCommon.showThisActivity((TopBaseActivity) ctx, proClassifyDetail.getUrlLink(), "");
//                }
//            });
//            productListView.addHeaderView(headIv);
//        }
        productListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                backBtn.setVisibility(firstVisibleItem <= 7 ? View.GONE : View.VISIBLE);
            }
        });
        productListClickHeadLayout.setListener(new ProductListClickHeadLayout.OnHeadTitleClickListener() {
            @Override
            public void headTitleClicked(int index, boolean isDown) {
                productOrder = isDown ? "1" : "0";
                productSort = index == 1 ? 4 + "" : index + "";// 当选择第一个角标即按佣金排序时 :对应的productSort=4
                productPagerIndex = 1;
                if (productSort.equals("1")) {
                    productOrder = "0";

                }
                getData(false);
            }
        });
    }


    private void getData(boolean openDialog) {
        new MyNetAsyncTask(ctx, openDialog) {
            ProductDatasDataEntity response;
            long loadSysTime;
            @Override
            protected void doInBack() throws Exception {
                loadSysTime = System.currentTimeMillis();
                response = TopApp.getInstance().getHttpService().productClassifyPageList201(MyApp.getInstance().getLoginService().token, proClassifyDetail.getCateId(), "", productOrder, productPagerIndex + "", PAGE_SIZE + "", productSort);
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
                        if (isAdded())
                            ToastUtil.showCustomToast(getActivity(), response.getErrorsMsgStr());
                    }
                } else {
                    if (isAdded())
                        showCustomToast(getActivity(), getString(R.string.pleaseCheckNetwork));
                }

            }
        }.execute();
    }


    @Override
    public void onRefresh() {
        productPagerIndex = 1;
        getData(true);
    }


    @Override
    public void onLoadMore() {
        getData(true);
    }

    public void stopLoad() {
        if (productListView != null) {
            productListView.stopRefresh();
            productListView.stopLoadMore();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.product_iv_back_to_top:  //listView返回首个item
                productListView.smoothScrollToPosition(0);
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
