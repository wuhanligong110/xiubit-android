package com.toobei.tb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ProductClassifyStatisticsDetail;
import com.toobei.common.entity.ProductClassifyStatisticsEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.LoginActivity;
import com.toobei.tb.activity.MainActivity;
import com.toobei.tb.activity.MyCfpActivity;
import com.toobei.tb.activity.ProductViewPagerActivity;
import com.toobei.tb.activity.ProlductListActivity;
import com.toobei.tb.activity.WebActivityCommon;
import com.toobei.tb.adapter.ProductClassifyStatisticsAdapter;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author qingyechen
 * @time 2016/12/23 0023 下午 4:39
 */
public class FragmentProduct extends FragmentBase implements IXListViewListener, ProductClassifyStatisticsAdapter.OnItemClickListener, View.OnClickListener {

    private MainActivity activity;
    private List<ProductClassifyStatisticsDetail> productClassifyList = new ArrayList<ProductClassifyStatisticsDetail>(); //产品分类 短期|长期|高收益
    private ProductClassifyStatisticsAdapter productTypeAdapter; //产品分类 短期 长期 高收益
    private XListView productTypeListView; //产品分类 短期|长期|高收益
    private ProductClassifyStatisticsEntity productClassifyStatisticsEntity;
    private TextView freshRateTV; //新手标年化
    private TextView cfpRecommendRateTV;
    private ProductClassifyStatisticsDetail freshDetail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(ctx).inflate(R.layout.fragment_home, null);
        initView(rootView);
        getProductClassifyDatas(true);
        return rootView;
    }

    private void initView(View rootView) {
        // 首页热门产品列表
        productTypeListView = (XListView) rootView.findViewById(R.id.org_detail_xlistView);
        View headView = LayoutInflater.from(ctx).inflate(R.layout.head_fragment_product, null);
        headView.findViewById(R.id.product_fresh_productLL).setOnClickListener(this); //新手标
        headView.findViewById(R.id.product_cfp_recommendLL).setOnClickListener(this); //理财师推荐
        headView.findViewById(R.id.img_product_lvdun).setOnClickListener(this);
        headView.findViewById(R.id.text_product_fresh_des).setOnClickListener(this); //T呗小白训练营
        freshRateTV = (TextView) headView.findViewById(R.id.text_product_fresh_rate);
        cfpRecommendRateTV = (TextView) headView.findViewById(R.id.text_product_recommend_rate);

        productTypeListView.addHeaderView(headView);
        productTypeListView.setVerticalScrollBarEnabled(false);
        productTypeAdapter = new ProductClassifyStatisticsAdapter(ctx, productClassifyList);
        productTypeAdapter.setOnItemClickListener(this);
        productTypeListView.setAdapter(productTypeAdapter);
        productTypeListView.setDividerHeight(0);
        productTypeListView.setXListViewListener(this);
        productTypeListView.setAutoLoadMore(false);
        productTypeListView.setPullLoadEnable(false);
    }


    /**
     * 接口名称 3.4.7 产品分类统计 v2.0.1
     * 请求类型 get
     * 请求Url /product/productClassifyStatistics/2.0.1
     */
    private void getProductClassifyDatas(boolean isRefresh) {

        new MyNetAsyncTask(ctx, isRefresh) {
            @Override
            protected void doInBack() throws Exception {
                productClassifyStatisticsEntity = MyApp.getInstance().getHttpService().productClassifyStatistics201(MyApp.getInstance().getLoginService().token, "");
            }

            @Override
            protected void onPost(Exception e) {
                productTypeListView.stopRefresh();
                if (e == null && productClassifyStatisticsEntity != null) {
                    if (productClassifyStatisticsEntity.getCode().equals("0")) {
                        List<ProductClassifyStatisticsDetail> datas = productClassifyStatisticsEntity.getData().getDatas();
                        for (int i = 0; i < datas.size(); i++) {
//                            1-热门产品 2-新手产品 3-短期产品 4-高收益产品
//                                    *                   5-稳健收益产品 801-理财师推荐产品 802-热推产品 901-首投标 902-复投标 多个一起查询的时候请使用,分开
//                                    *                   如：1,2,3,4,5,801,901,902 不传时则查询所有的产品分类
                            ProductClassifyStatisticsDetail detail = datas.get(i);
                            String cateId = detail.getCateId();
                            if ("801".contains(cateId)) {
                                cfpRecommendRateTV.setText(detail.getFlowMinRateStatistics() + "~" + detail.getFlowMaxRateStatistics());
                                datas.remove(i);
                                i--;
                            }
                            if ("2".contains(cateId)) {
                                freshRateTV.setText(detail.getFlowMinRateStatistics() + "~" + detail.getFlowMaxRateStatistics());
                                datas.remove(i);
                                freshDetail = detail;
                                i--;
                            }

                        }
                        productTypeAdapter.refresh(datas);
                    } else {
                        if (isAdded()) {
                            ToastUtil.showCustomToast(activity, productClassifyStatisticsEntity.getErrorsMsgStr());
                        }
                    }
                } else {
                    if (isAdded()) {
                        ToastUtil.showCustomToast(activity, getString(R.string.pleaseCheckNetwork));
                    }
                }

            }


        }.execute();
    }

    @Override
    public void onRefresh() {
        if (MyApp.getInstance().getLoginService().isCachPhoneExist()) {
            ((MainActivity) activity).getTopMsgCount();
        }
        getProductClassifyDatas(true);

    }

    @Override
    public void onLoadMore() {

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.img_product_lvdun:

                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, MyApp.getInstance().getDefaultSp().getSafeShield(), "");
                break;
            case R.id.text_product_fresh_des:

                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, MyApp.getInstance().getDefaultSp().getToobeiTrain(), "");
                break;
            case R.id.product_fresh_productLL:

                intent = new Intent(activity, ProlductListActivity.class);
                intent.putExtra("cateId", "2");
                intent.putExtra("cateName", getString(R.string.fresh_productlist_activity_name));
                intent.putExtra("classifyStatisticsDetail", freshDetail);
                activity.startActivity(intent);
                break;

            case R.id.product_cfp_recommendLL:
                if (MyApp.getInstance().getLoginService().isCachPhoneExist()) {
                    activity.showActivity(activity, MyCfpActivity.class);
                } else {
                    activity.showActivity(activity, LoginActivity.class);
                }
                break;
        }

    }


    @Override
    public void onItemclick(int position) {
        //跳转多项可滑动切换的产品列表
        Intent intent = new Intent(ctx, ProductViewPagerActivity.class);
        ProductClassifyStatisticsDetail data = productTypeAdapter.getItem(position);
        intent.putExtra("cateId", data.getCateId());
        intent.putExtra("response", productClassifyStatisticsEntity);
        intent.putExtra("listType", ProductViewPagerActivity.ProductType.PRODUCTTYPE_DATE_LIMIT_TYPE);
        ctx.startActivity(intent);

        //单项不可左右滑动产品列表
//        ProductClassifyStatisticsDetail data = productTypeAdapter.getItem(position);
//        Intent intent = new Intent(activity, ProlductListActivity.class);
//        intent.putExtra("cateId", data.getCateId() + "");
//        intent.putExtra("cateName", data.getCateName());
//        activity.startActivity(intent);
//        ctx.startActivity(intent);
    }
}
