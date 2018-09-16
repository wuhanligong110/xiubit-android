package com.toobei.tb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.toobei.common.TopApp;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.ProlductListActivity;
import com.toobei.tb.adapter.ProductListAdapter;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 公司: tophlc
 * 类说明: 我的理财师 ->推荐产品
 *
 * @author qingyechen
 * @time 2016/12/30 0030 下午 2:58
 */

public class FragmentMyCfpRecommendProduct extends FragmentBase implements IXListViewListener {

    private static final int PAGE_SIZE = 10; // 默认分页大小
    private int productPagerIndex = 1;

    private XListView productListView;
    public ProductListAdapter productAdapter;
    boolean isCfpRecommend = true; //显示我的理财师推荐 我的理财师没有推荐时显示所有推荐
    public List<ProductDetail> productList = new ArrayList<ProductDetail>();
    private boolean hasAddHeadView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ListBlankLayout rootView = (ListBlankLayout) inflater.inflate(R.layout.my_list_blanklayout, container, false);
        initView(rootView);
        getData(false);
        return rootView;
    }


    @SuppressWarnings("unchecked")
    private void initView(ListBlankLayout rootView) {
        productListView = (XListView) rootView.initContentView(R.layout.list_blank_xlistview_layout);
        productAdapter = new ProductListAdapter(ctx, productList);
        //  orgListAdapter.setOnBtnRecommendClicked(this);// 点击推荐推荐

        productListView.setDividerHeight(0);
        productListView.setXListViewListener(this);
    }

    /**
     * 获取我的理财师推荐产品数据
     *
     * @param openDialog
     */
    private void getData(boolean openDialog) {
        if (!isCfpRecommend && productPagerIndex == 1) { //添加头部禁止自动加载
            productListView.setAutoLoadMore(false);
            productListView.setPullLoadEnable(false);
        }
        new MyNetAsyncTask(ctx, openDialog) {
            ProductDatasDataEntity response;
            long loadSystemTime ;
            @Override
            protected void doInBack() throws Exception {
                loadSystemTime = System.currentTimeMillis();
                if (isCfpRecommend) { //我的理财师推荐

                    response = MyApp.getInstance().getHttpService().myCfpRecommendProduct(MyApp.getInstance().getLoginService().token, productPagerIndex + "", PAGE_SIZE + "");
                } else {  //热推
                    if (!hasAddHeadView) {


                        productListView.post(new Runnable() {
                            @Override
                            public void run() {
                                View headView = LayoutInflater.from(ctx).inflate(R.layout.head_mycfp_recommend_product, null);
                                ImageView moreIv = (ImageView) headView.findViewById(R.id.moreIv);
                                moreIv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ctx, ProlductListActivity.class);
                                        intent.putExtra("cateId", "802");
                                        intent.putExtra("cateName", "getString(R.string.product_list_cfp_recommend_name)");
                                        ctx.startActivity(intent);
                                    }
                                });
                                productListView.addHeaderView(headView);
                                hasAddHeadView = true;
                            }
                        });

                    }
                    String productSort = "2";//1-默认排序 2-年化收益 3-产品期限 4-佣金
                    String productOrder = "0";//	0-升序 1-降序
                    response = TopApp.getInstance().getHttpService().productClassifyPageList201("", "802", "", productOrder, "1", "1", productSort);
                }
            }

            @Override
            protected void onPost(Exception e) {
                stopLoad();
                if (e == null && response != null) {

                    if (response.getCode().equals("0")) {
                        List<ProductDetail> datas = response.getData().getDatas();
                        if (productPagerIndex == 1) {
                            productAdapter.clear();
                            if (isCfpRecommend && (datas == null || datas.size() <= 0)) { //如果我的理财师没有推荐,显示所有热推
                                isCfpRecommend = false;
                                getData(false);
                                return;
                            }
                        }
                        productListView.setAdapter(productAdapter);
                        productAdapter.addAll(datas,loadSystemTime);

                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() < 1) {
                            productListView.setPullLoadEnable(false);
                        } else {
                                productListView.setPullLoadEnable(isCfpRecommend);
                        }

                        productPagerIndex++;
                    } else {
                        if (isCfpRecommend && productPagerIndex == 1) {
                            isCfpRecommend = false; //我的理财师没有推荐时显示所有推荐
                            getData(false);
                            return;
                        }
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());

                    }
                } else {
                    if (isCfpRecommend && productPagerIndex == 1) {
                        isCfpRecommend = false; //我的理财师没有推荐时显示所有推荐
                        getData(false);
                        return;
                    }
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


}
